package pers.xiaomuma.framework.security.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import pers.xiaomuma.framework.exception.AppBizException;

public interface DefaultAuthenticationPostProcessor {

    default void postProcessBeforeAuthentication(Authentication authentication) throws AppBizException {

    }

    default void postProcessAfterAuthentication(UserDetails userDetails) throws AppBizException {

    }
}
