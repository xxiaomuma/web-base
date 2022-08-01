package pers.xiaomuma.framework.security.login;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface DefaultUserDetailsService {

    default UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        throw new BadCredentialsException("缺失mobile登录服务");
    }

    default UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        throw new BadCredentialsException("缺失email登录服务");
    }

    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new BadCredentialsException("缺失username登录服务");
    }

}
