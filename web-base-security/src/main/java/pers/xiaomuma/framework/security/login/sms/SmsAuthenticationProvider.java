package pers.xiaomuma.framework.security.login.sms;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import pers.xiaomuma.framework.security.login.DefaultAuthenticationChecker;
import pers.xiaomuma.framework.security.login.DefaultAuthenticationPostProcessor;
import pers.xiaomuma.framework.security.login.DefaultUserDetailsService;
import pers.xiaomuma.framework.security.login.DefaultValidateCodeService;
import java.util.Objects;


public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(SmsAuthenticationProvider.class);
    private DefaultUserDetailsService userDetailsService;
    private DefaultValidateCodeService validateCodeService;
    private DefaultAuthenticationPostProcessor defaultAuthenticationPostProcessor;
    private UserDetailsChecker userDetailsChecker = new DefaultAuthenticationChecker();

    public SmsAuthenticationProvider(DefaultUserDetailsService userDetailsService, DefaultValidateCodeService validateCodeService) {
        this.userDetailsService = userDetailsService;
        this.validateCodeService = validateCodeService;
    }

    public SmsAuthenticationProvider(DefaultUserDetailsService userDetailsService, DefaultValidateCodeService validateCodeService, DefaultAuthenticationPostProcessor defaultAuthenticationPostProcessor) {
        this.userDetailsService = userDetailsService;
        this.validateCodeService = validateCodeService;
        this.defaultAuthenticationPostProcessor = defaultAuthenticationPostProcessor;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = authenticationToken.getMobile();
        if (StrUtil.isBlank(mobile)) {
            throw new BadCredentialsException("手机号码为空");
        }
        String code = authenticationToken.getCode();
        if (StrUtil.isBlank(code)) {
            throw new BadCredentialsException("验证码为空");
        }
        if (Objects.nonNull(defaultAuthenticationPostProcessor)) {
            defaultAuthenticationPostProcessor.postProcessBeforeAuthentication(authenticationToken);
        }

        if (!validateCodeService.validateSmsCode(mobile, code)) {
            logger.debug("手机验证码不匹配: mobile:[{}], code:[{}]", mobile, code);
            throw new BadCredentialsException("手机验证码不匹配");
        }
        UserDetails userDetails = userDetailsService.loadUserByMobile(mobile);
        userDetailsChecker.check(userDetails);

        if (Objects.nonNull(defaultAuthenticationPostProcessor)) {
            defaultAuthenticationPostProcessor.postProcessAfterAuthentication(userDetails);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return (SmsAuthenticationToken.class.isAssignableFrom(clazz));
    }
}
