package pers.xiaomuma.base.security.login.username;


import cn.hutool.core.util.StrUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pers.xiaomuma.base.security.login.sms.SmsAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    public UsernameAuthenticationFilter(String pattern) {
        super(new AntPathRequestMatcher(pattern, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String mobile = getUsername(request);
        if (StrUtil.isBlank(mobile)) {
            throw new AuthenticationServiceException("账号不能为空");
        }
        String code = getPassword(request);
        if (StrUtil.isBlank(mobile)) {
            throw new AuthenticationServiceException("密码不能为空");
        }
        return this.getAuthenticationManager().authenticate(new SmsAuthenticationToken(mobile, code));
    }

    protected String getUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected String getPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }
}
