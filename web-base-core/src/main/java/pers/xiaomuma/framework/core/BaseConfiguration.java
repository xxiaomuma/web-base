package pers.xiaomuma.framework.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.serialize.JsonUtils;
import pers.xiaomuma.framework.serialize.ObjectMapperFactory;

@Configuration
@EnableAspectJAutoProxy(
        exposeProxy = true,
        proxyTargetClass = true
)
public class BaseConfiguration {

    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.OBJECT_MAPPER;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = ObjectMapperFactory.createDefaultObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

}
