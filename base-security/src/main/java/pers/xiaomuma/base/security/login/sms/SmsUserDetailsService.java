package pers.xiaomuma.base.security.login.sms;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SmsUserDetailsService {

    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;

}
