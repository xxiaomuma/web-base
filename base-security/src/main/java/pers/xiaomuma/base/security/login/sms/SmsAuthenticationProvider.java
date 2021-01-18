package pers.xiaomuma.base.security.login.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;


public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(SmsAuthenticationProvider.class);
    private SmsUserDetailsService smsUserDetailsService = new DefaultSmsUserDetailsService();
    private SmsUserLoginChecker smsUserLoginChecker = new DefaultSmsUserLoginChecker();
    private UserDetailsChecker userDetailsChecker = new SmsAuthenticationProvider.DefaultSmsAuthenticationChecker();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = authenticationToken.getMobile();
        String code = authenticationToken.getCode();
        if (smsUserLoginChecker.check(mobile, code)) {
            UserDetails userDetails = smsUserDetailsService.loadUserByMobile(mobile);
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

    private static class DefaultSmsAuthenticationChecker implements UserDetailsChecker {
        private DefaultSmsAuthenticationChecker() {
        }

        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                throw new LockedException("账户已锁定");
            } else if (!user.isEnabled()) {
                throw new DisabledException("用户已禁用");
            } else if (!user.isAccountNonExpired()) {
                throw new AccountExpiredException("账户已过期");
            } else if (!user.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("凭证已失效");
            }
        }
    }
}
