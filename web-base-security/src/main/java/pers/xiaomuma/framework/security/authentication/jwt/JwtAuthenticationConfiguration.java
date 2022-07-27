package pers.xiaomuma.framework.security.authentication.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pers.xiaomuma.framework.security.authentication.handler.JwtAuthenticationSuccessHandler;

@Configuration
@Import(JwtProperties.class)
public class JwtAuthenticationConfiguration {

    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtProperties jwtProperties) {
        return new JwtTokenGenerator(jwtProperties);
    }

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(JwtTokenGenerator jwtTokenGenerator) {
        return new JwtAuthenticationConfigurer(jwtTokenGenerator);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(JwtTokenGenerator jwtTokenGenerator) {
        return new JwtAuthenticationSuccessHandler(jwtTokenGenerator);
    }


}
