package pers.xiaomuma.framework.security.authentication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pers.xiaomuma.framework.response.ViewResponse;
import pers.xiaomuma.framework.security.authentication.jwt.JwtTokenGenerator;
import pers.xiaomuma.framework.security.user.CustomUser;
import pers.xiaomuma.framework.serialize.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationSuccessHandler.class);
    private JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthenticationSuccessHandler(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
        } else {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            Integer userId = customUser.getUserId();
            Map<String, String> passport = this.jwtTokenGenerator
                    .generatePassport(Integer.toString(userId), null, customUser.getAuthorities());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.object2Json(ViewResponse.success(passport)));
        }
    }
}
