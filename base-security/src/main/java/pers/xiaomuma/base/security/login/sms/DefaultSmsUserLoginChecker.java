package pers.xiaomuma.base.security.login.sms;


import org.springframework.security.authentication.BadCredentialsException;

public class DefaultSmsUserLoginChecker implements SmsUserLoginChecker {

    @Override
    public boolean check(String mobile, String code) throws BadCredentialsException {
        throw new BadCredentialsException("缺失效验验证码组件");
    }
}
