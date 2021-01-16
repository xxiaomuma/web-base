package pers.xiaomuma.base.security.authentication.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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

}
