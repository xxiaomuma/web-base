package pers.xiaomuma.framework.security.authentication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.xiaomuma.framework.response.BaseResponse;
import pers.xiaomuma.framework.response.ResponseCode;
import pers.xiaomuma.framework.serialize.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);

    public DefaultAuthenticationFailureHandler() {
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        this.logger.debug("AuthenticationFailure");
        if (response.isCommitted()) {
            this.logger.debug("Response has already been committed");
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.object2Json(BaseResponse.failed(e.getMessage(), ResponseCode.EXPIRED_AUTHORIZE)));
            /*if (e instanceof BadCredentialsException) {
                response.getWriter().write(JsonUtils.object2Json(ViewResponse.failed(ResponseCode.EXPIRED_AUTHORIZE, e.getMessage())));
            } else {
                response.getWriter().write(JsonUtils.object2Json(ViewResponse.failed(ResponseCode.EXPIRED_AUTHORIZE, "")));
            }*/
        }

    }
}
