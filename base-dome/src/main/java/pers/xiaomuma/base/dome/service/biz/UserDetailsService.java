package pers.xiaomuma.base.dome.service.biz;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pers.xiaomuma.base.security.login.DefaultUserDetailsService;
import pers.xiaomuma.base.security.user.CustomUser;
import java.util.Collections;

@Component
public class UserDetailsService implements DefaultUserDetailsService {

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        UserDetails userDetails = User.builder()
                .username("xiaomuma")
                .password("[PROTECTED]")
                .authorities(Collections.emptySet())
                .accountLocked(false)
                .build();
        return new CustomUser(2, userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = User.builder()
                .username("xiaomuma")
                .password("$2a$10$Hn.iWsoJ9D2zne0.HTbTNulsgkCE9HHGwv9b8Jc2alVEKqMQQLC8i")
                .authorities(Collections.emptySet())
                .accountLocked(false)
                .build();
        return new CustomUser(2, userDetails);
    }
}
