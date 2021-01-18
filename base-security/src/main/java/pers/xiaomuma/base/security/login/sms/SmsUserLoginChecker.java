package pers.xiaomuma.base.security.login.sms;

import org.springframework.security.authentication.BadCredentialsException;


public interface SmsUserLoginChecker {

    boolean check(String mobile, String code) throws BadCredentialsException;
}
