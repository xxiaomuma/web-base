package pers.xiaomuma.framework.security.login.email;

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
import pers.xiaomuma.framework.security.login.DefaultAuthenticationChecker;
import pers.xiaomuma.framework.security.login.DefaultUserDetailsService;
import pers.xiaomuma.framework.security.login.DefaultValidateCodeService;


public class EmailAuthenticationProvider implements AuthenticationProvider {


    private final Logger logger = LoggerFactory.getLogger(EmailAuthenticationProvider.class);
    private DefaultUserDetailsService userDetailsService;
    private DefaultValidateCodeService validateCodeService;
    private UserDetailsChecker userDetailsChecker = new DefaultAuthenticationChecker();

    public EmailAuthenticationProvider(DefaultUserDetailsService userDetailsService, DefaultValidateCodeService validateCodeService) {
        this.userDetailsService = userDetailsService;
        this.validateCodeService = validateCodeService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken authenticationToken = (EmailAuthenticationToken) authentication;
        String email = authenticationToken.getEmail();
        if (StrUtil.isBlank(email)) {
            throw new BadCredentialsException("邮箱号为空");
        }
        String code = authenticationToken.getCode();
        if (StrUtil.isBlank(code)) {
            throw new BadCredentialsException("验证码为空");
        }
        if (validateCodeService.validateEmailCode(email, code)) {
            UserDetails userDetails = userDetailsService.loadUserByEmail(email);
            userDetailsChecker.check(userDetails);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        logger.debug("邮箱验证码不匹配: mobile:[{}], code:[{}]", email, code);
        throw new BadCredentialsException("邮箱验证码不匹配");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (EmailAuthenticationToken.class.isAssignableFrom(clazz));
    }
}
