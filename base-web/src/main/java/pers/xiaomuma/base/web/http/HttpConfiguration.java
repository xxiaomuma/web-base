package pers.xiaomuma.base.web.http;


import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.base.web.ApplicationEnv;
import pers.xiaomuma.base.web.http.okhttp.OkHttpClientBuilder;

public class HttpConfiguration {

    @Bean
    public OkHttpClientBuilder okHttpClientBuilder(ApplicationEnv applicationEnv) {
        return new OkHttpClientBuilder(applicationEnv);
    }

    @Bean(
            name = {"restTemplate"}
    )
    public RestTemplate restTemplate(OkHttpClientBuilder okHttpClientBuilder) {
        OkHttpClient okHttpClient = okHttpClientBuilder.multiplexedHttpClient();
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
    }
}
