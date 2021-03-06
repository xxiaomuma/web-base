package pers.xiaomuma.base.security.login.sms;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import pers.xiaomuma.base.security.login.DefaultAuthenticationChecker;
import pers.xiaomuma.base.security.login.DefaultUserDetailsService;


public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(SmsAuthenticationProvider.class);
    private DefaultUserDetailsService userDetailsService;
    private DefaultSmsUserLoginChecker userLoginChecker;
    private UserDetailsChecker userDetailsChecker = new DefaultAuthenticationChecker();

    public SmsAuthenticationProvider(DefaultUserDetailsService userDetailsService, DefaultSmsUserLoginChecker userLoginChecker) {
        this.userDetailsService = userDetailsService;
        this.userLoginChecker = userLoginChecker;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = authenticationToken.getMobile();
        if (StrUtil.isBlank(mobile)) {
            throw new BadCredentialsException("手机号码为空");
        }
        String code = authenticationToken.getCode();
        if (StrUtil.isBlank(code)) {
            throw new BadCredentialsException("验证码为空");
        }
        if (userLoginChecker.check(mobile, code)) {
            UserDetails userDetails = userDetailsService.loadUserByMobile(mobile);
            userDetailsChecker.check(userDetails);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        logger.debug("手机验证码不匹配: mobile:[{}], code:[{}]", mobile, code);
        throw new BadCredentialsException("手机验证码不匹配");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (SmsAuthenticationToken.class.isAssignableFrom(clazz));
    }
}
