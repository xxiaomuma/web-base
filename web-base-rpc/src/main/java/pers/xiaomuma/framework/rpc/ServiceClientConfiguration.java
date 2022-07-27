package pers.xiaomuma.framework.rpc;


import feign.Client;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.core.startup.BaseApplicationInitializer;
import pers.xiaomuma.framework.rpc.aop.ExceptionCatcherInterceptor;
import pers.xiaomuma.framework.rpc.config.DefaultContextRefresher;
import pers.xiaomuma.framework.rpc.error.MsExceptionTranslator;
import pers.xiaomuma.framework.rpc.feign.annotation.EnableCustomFeignClients;
import pers.xiaomuma.framework.rpc.feign.config.FeignCustomConfiguration;
import pers.xiaomuma.framework.rpc.feign.okhttp.CustomOkHttpFeignClient;
import pers.xiaomuma.framework.rpc.feign.okhttp.OkHttpLoggingInterceptor;
import pers.xiaomuma.framework.rpc.resttemplate.EnhancedRestTemplate;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableDiscoveryClient
@EnableCustomFeignClients(basePackages = "pers.xiaomuma")
@Import({FeignCustomConfiguration.class, ExceptionCatcherInterceptor.class})
public class ServiceClientConfiguration {

	@Bean
	public MsExceptionTranslator msExceptionTranslator() {
		return new MsExceptionTranslator();
	}

	@Primary
	@Bean
	public OkHttpClient okHttpClient(ApplicationConstant applicationConstant) {
		return buildOkHttp3Client(applicationConstant);
	}

	private OkHttpClient buildOkHttp3Client(ApplicationConstant applicationConstant) {
		return new OkHttpClient.Builder()
				.readTimeout(applicationConstant.okHttpReadTimeout, TimeUnit.MILLISECONDS)
				.connectTimeout(applicationConstant.okHttpConnectTimeout, TimeUnit.MILLISECONDS)
				.writeTimeout(applicationConstant.okHttpWriteTimeout, TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(true)
				.connectionPool(new ConnectionPool(applicationConstant.okHttpMaxIdle,
						applicationConstant.okHttpAliveDuration, TimeUnit.SECONDS))
				.addInterceptor(new OkHttpLoggingInterceptor(applicationConstant)).build();
	}

	@Bean
	public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
							  SpringClientFactory clientFactory,
							  OkHttpClient okHttpClient) {
		// ribbon 路由规则拓展
		Client feignOkHttpClient = new CustomOkHttpFeignClient(okHttpClient);
		return new LoadBalancerFeignClient(feignOkHttpClient, cachingFactory, clientFactory);
	}

	@Bean
	public BaseApplicationInitializer enhancedRestTemplateInitializer(ApplicationContext ctx) {
		return () -> EnhancedRestTemplate.initBuilderInfo(ctx);
	}

	@LoadBalanced
	@Bean
	@Primary
	public RestTemplate restTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
									 OkHttpClient okHttpClient,
									 @Qualifier("enhancedRestTemplateInitializer") BaseApplicationInitializer initializer) {
		return EnhancedRestTemplate.assembleRestTemplate(mappingJackson2HttpMessageConverter, okHttpClient);
	}

	@Bean
	@Primary
	public ContextRefresher contextRefresher(ConfigurableApplicationContext context,
											 RefreshScope scope) {
		return new DefaultContextRefresher(context, scope);
	}

}
