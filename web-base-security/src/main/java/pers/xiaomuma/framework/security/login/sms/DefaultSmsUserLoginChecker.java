package pers.xiaomuma.framework.security.login.sms;

import org.springframework.security.authentication.BadCredentialsException;


public interface DefaultSmsUserLoginChecker {

    default boolean check(String mobile, String code) throws BadCredentialsException {
        throw new BadCredentialsException("缺失效验验证码服务");
    }
}
