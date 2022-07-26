package pers.xiaomuma.framework.rpc.feign.config;

import feign.Feign;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.hystrix.HystrixFeign;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.rpc.feign.interceptor.AppInfoFeignInterceptor;

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
	public AppInfoFeignInterceptor appInfoFeignInterceptor(ApplicationConstant applicationConstant) {
		return new AppInfoFeignInterceptor(applicationConstant.applicationName);
	}

	@Bean
	@Primary
	@Scope("prototype")
	public Encoder multipartFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
		return new SpringFormEncoder(new SpringEncoder(messageConverters));
	}

}
