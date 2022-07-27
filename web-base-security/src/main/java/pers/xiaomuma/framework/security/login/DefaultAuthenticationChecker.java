package pers.xiaomuma.framework.security.login;


import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;



public class DefaultAuthenticationChecker implements UserDetailsChecker  {

    @Override
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
