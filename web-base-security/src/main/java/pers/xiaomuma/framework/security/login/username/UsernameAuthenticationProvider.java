package pers.xiaomuma.framework.security.login.username;


import cn.hutool.core.util.StrUtil;
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
import pers.xiaomuma.framework.security.login.DefaultAuthenticationChecker;
import pers.xiaomuma.framework.security.login.DefaultAuthenticationPostProcessor;
import pers.xiaomuma.framework.security.login.DefaultUserDetailsService;
import java.util.Objects;


public class UsernameAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(UsernameAuthenticationProvider.class);
    private PasswordEncoder passwordEncoder;
    private DefaultUserDetailsService userDetailsService;
    private DefaultAuthenticationPostProcessor defaultAuthenticationPostProcessor;
    private UserDetailsChecker userDetailsChecker = new DefaultAuthenticationChecker();

    public UsernameAuthenticationProvider(PasswordEncoder passwordEncoder, DefaultUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public UsernameAuthenticationProvider(PasswordEncoder passwordEncoder, DefaultUserDetailsService userDetailsService, DefaultAuthenticationPostProcessor defaultAuthenticationPostProcessor) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.defaultAuthenticationPostProcessor = defaultAuthenticationPostProcessor;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernameAuthenticationToken authenticationToken = (UsernameAuthenticationToken) authentication;
        String username = authenticationToken.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new BadCredentialsException("账号为空");
        }
        String password = authenticationToken.getPassword();
        if (StrUtil.isBlank(password)) {
            throw new BadCredentialsException("密码为空");
        }
        if (Objects.nonNull(defaultAuthenticationPostProcessor)) {
            defaultAuthenticationPostProcessor.postProcessBeforeAuthentication(authenticationToken);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.debug("用户名密码不匹配: username:[{}]", username);
            throw new BadCredentialsException("用户名密码不匹配");
        }
        userDetailsChecker.check(userDetails);

        if (Objects.nonNull(defaultAuthenticationPostProcessor)) {
            defaultAuthenticationPostProcessor.postProcessAfterAuthentication(userDetails);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (UsernameAuthenticationToken.class.isAssignableFrom(clazz));
    }


}
