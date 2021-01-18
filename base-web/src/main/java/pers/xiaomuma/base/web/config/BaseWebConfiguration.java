package pers.xiaomuma.base.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import pers.xiaomuma.base.web.ApplicationEnv;
import pers.xiaomuma.base.web.GlobalExceptionTranslator;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.web.http.HttpConfiguration;


@Configuration
@Import({ HttpConfiguration.class })
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class BaseWebConfiguration {

    @Bean
    public ApplicationEnv applicationConstant() {
        return new ApplicationEnv();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.OBJECT_MAPPER;
    }

    @Bean
    public GlobalExceptionTranslator globalExceptionTranslator(ApplicationEnv applicationEnv) {
        return new GlobalExceptionTranslator(applicationEnv);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = JsonUtils.ObjectMapperFactory.createDefaultObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
