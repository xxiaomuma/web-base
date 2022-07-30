package pers.xiaomuma.framework.security.login;

import org.springframework.security.authentication.BadCredentialsException;


public interface DefaultValidateCodeService {

    default boolean validateSmsCode(String mobile, String code) throws BadCredentialsException {
        throw new BadCredentialsException("缺失验证码效验");
    }
}
