package pers.xiaomuma.framework.web;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.xiaomuma.framework.web.config.SwaggerConfiguration;
import pers.xiaomuma.framework.web.config.WebMvcConfiguration;
import pers.xiaomuma.framework.web.web.GlobalExceptionTranslator;

@Configuration
@Import(SwaggerConfiguration.class)
public class WebApplicationConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionTranslator globalExceptionTranslator() {
        return new GlobalExceptionTranslator();
    }
}
