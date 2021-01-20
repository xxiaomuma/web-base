package pers.xiaomuma.base.dome.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.xiaomuma.base.dome.service.biz.UserDetailsService;
import pers.xiaomuma.base.dome.service.biz.SmsUserLoginChecker;
import pers.xiaomuma.base.security.authentication.handler.DefaultAuthenticationFailureHandler;
import pers.xiaomuma.base.security.authentication.handler.JwtAuthenticationSuccessHandler;
import pers.xiaomuma.base.security.authentication.jwt.JwtAuthenticationConfiguration;
import pers.xiaomuma.base.security.authentication.jwt.JwtAuthenticationConfigurer;
import pers.xiaomuma.base.security.authentication.jwt.JwtTokenGenerator;
import pers.xiaomuma.base.security.exception.RestAccessDeniedHandler;
import pers.xiaomuma.base.security.exception.RestForbiddenEntryPoint;
import pers.xiaomuma.base.security.login.sms.SmsAuthenticationFilter;
import pers.xiaomuma.base.security.login.sms.SmsAuthenticationProvider;
import pers.xiaomuma.base.security.login.username.UsernameAuthenticationFilter;
import pers.xiaomuma.base.security.login.username.UsernameAuthenticationProvider;

@Configuration
@EnableWebSecurity
@Import({
        JwtAuthenticationConfiguration.class
})
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationConfigurer jwtAuthenticationConfigurer;
    private final UserDetailsService userDetailsService;
    private final SmsUserLoginChecker smsUserLoginChecker;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated();
        http.apply(jwtAuthenticationConfigurer);
        http.exceptionHandling()
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .authenticationEntryPoint(new RestForbiddenEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SmsAuthenticationFilter smsAuthenticationFilter() throws Exception {
        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(jwtTokenGenerator));
        return filter;
    }

    @Bean
    public UsernameAuthenticationFilter usernameAuthenticationFilter() throws Exception {
        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setAuthenticationFailureHandler(new DefaultAuthenticationFailureHandler());
        filter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(jwtTokenGenerator));
        return filter;
    }

    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        return new SmsAuthenticationProvider(userDetailsService, smsUserLoginChecker);
    }

    @Bean
    public UsernameAuthenticationProvider usernameAuthenticationProvider() {
        return new UsernameAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(smsAuthenticationProvider())
                .authenticationProvider(usernameAuthenticationProvider());
    }

}
