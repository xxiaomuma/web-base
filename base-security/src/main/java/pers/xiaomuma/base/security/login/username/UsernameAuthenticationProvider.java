package pers.xiaomuma.base.security.login.username;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.xiaomuma.base.security.login.DefaultAuthenticationChecker;
import pers.xiaomuma.base.security.login.DefaultUserDetailsService;


public class UsernameAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(UsernameAuthenticationProvider.class);
    private PasswordEncoder passwordEncoder;
    private DefaultUserDetailsService userDetailsService;
    private UserDetailsChecker userDetailsChecker = new DefaultAuthenticationChecker();

    public UsernameAuthenticationProvider(PasswordEncoder passwordEncoder, DefaultUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernameAuthenticationToken authenticationToken = (UsernameAuthenticationToken) authentication;
        String username = authenticationToken.getUsername();
        String password = authenticationToken.getPassword();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.debug("用户名密码不匹配: username:[{}]", username);
            throw new BadCredentialsException("用户名密码不匹配");
        }
        userDetailsChecker.check(userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (UsernameAuthenticationToken.class.isAssignableFrom(clazz));
    }


}
