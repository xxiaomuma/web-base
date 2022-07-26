package pers.xiaomuma.framework.rpc.resttemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.*;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.exception.ServiceUnavailableException;
import pers.xiaomuma.framework.exception.StartupFailureException;
import pers.xiaomuma.framework.rpc.feign.okhttp.OkHttpBuilder;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class EnhancedRestTemplate extends RestTemplate {

    private final static Logger logger = LoggerFactory.getLogger(EnhancedRestTemplate.class);

    public EnhancedRestTemplate() {
        super();
    }

    public EnhancedRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public EnhancedRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException(e.getMessage());
        } catch (IllegalStateException e) {
            if(e.getMessage().contains("No instances")) {
                //目标服务没启动的时候RibbonLoadBalancerClient.execute会抛出IllegalStateException
                //转化成ServiceUnavailableException
                throw new ServiceUnavailableException(e.getMessage());
            } else {
                throw e;
            }
        }
    }

    public static EnhancedRestTemplate assembleRestTemplate(
            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter, OkHttpClient okHttpClient) {

        EnhancedRestTemplate restTemplate = createRestTemplate(okHttpClient);
        //自定义异常处理
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.removeIf(converter ->
                converter instanceof MappingJackson2HttpMessageConverter);
        messageConverters.add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

    public static EnhancedRestTemplate createRestTemplate(OkHttpClient okHttpClient) {
        return new EnhancedRestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
    }

    private static OkHttpClient rawOkHttpClient = null;

    private static MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = null;

    private static ApplicationConstant applicationConstant = null;

    private static AtomicBoolean init = new AtomicBoolean(false);

    /**
     * builder可以复用, 能够重用socket连接, 不能大量使用assembleRestTemplate创建过多连接.
     * @return
     */
    public static Builder newBuilder() {
        if(!init.get()) {
            throw new StartupFailureException("EnhancedRestTemplate.newBuilder初始化失败, 需要等项目启动完成再调用该方法");
        }
        return new Builder();
    }

    public static synchronized void initBuilderInfo(ApplicationContext context) {
        if(init.compareAndSet(false, true)) {
            applicationConstant = context.getBean(ApplicationConstant.class);
            mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(createJacksonInnerObjectMapper());
            rawOkHttpClient = OkHttpBuilder.build(EnhancedRestTemplate.applicationConstant).build();
        }
    }

    private static ObjectMapper createJacksonInnerObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static final class Builder {

        private OkHttpClient.Builder builder = null;

        /** 读取超时时间, 单位毫秒*/
        private long okHttpReadTimeout;

        /** 连接超时时间, 单位毫秒*/
        private long okHttpConnectTimeout;

        /** 写入超时时间, 单位毫秒*/
        private long okHttpWriteTimeout;


        public Builder() {
            init();
        }

        private void init() {
            try {
                this.builder = rawOkHttpClient.newBuilder();
                this.okHttpReadTimeout = applicationConstant.okHttpReadTimeout;
                this.okHttpConnectTimeout = applicationConstant.okHttpConnectTimeout;
                this.okHttpWriteTimeout = applicationConstant.okHttpWriteTimeout;
            } catch (Exception e) {
                logger.error("EnhancedRestTemplate.newBuilder初始化失败, 需要等项目启动完成再调用该方法");
                throw e;
            }
        }

        public EnhancedRestTemplate build() {
            return assembleRestTemplate(mappingJackson2HttpMessageConverter,
                    builder.connectTimeout(this.okHttpConnectTimeout, TimeUnit.MILLISECONDS)
                            .readTimeout(this.okHttpReadTimeout, TimeUnit.MILLISECONDS)
                            .writeTimeout(this.okHttpWriteTimeout, TimeUnit.MILLISECONDS).build());
        }

    }
}
