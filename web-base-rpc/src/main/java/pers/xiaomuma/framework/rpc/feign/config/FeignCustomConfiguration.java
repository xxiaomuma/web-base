package pers.xiaomuma.framework.rpc.feign.config;

import feign.Feign;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.rpc.feign.interceptor.RpcFeignInterceptor;


@Configuration
public class FeignCustomConfiguration {

    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignHystrixBuilder(Retryer retryer) {
        return HystrixFeign.builder()
                .retryer(retryer)
                .errorDecoder(new FeignErrorHandler());
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringEncoder(messageConverters);
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
    }

    @Bean
    public RpcFeignInterceptor rpcFeignInterceptor(ApplicationConstant applicationConstant) {
        return new RpcFeignInterceptor(applicationConstant);
    }

}
