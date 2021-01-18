package pers.xiaomuma.base.dome.service.biz;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pers.xiaomuma.base.security.login.sms.DefaultSmsUserLoginChecker;


@Component
public class SmsUserLoginChecker implements DefaultSmsUserLoginChecker {

    @Override
    public boolean check(String mobile, String code) throws BadCredentialsException {
        return code.equals("00000");
    }
}
