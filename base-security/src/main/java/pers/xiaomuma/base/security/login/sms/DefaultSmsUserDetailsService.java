package pers.xiaomuma.base.security.login.sms;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class DefaultSmsUserDetailsService implements SmsUserDetailsService {

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        return null;
    }
}
