package pers.xiaomuma.framework.security.authentication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.xiaomuma.framework.response.ResponseCode;
import pers.xiaomuma.framework.response.ViewResponse;
import pers.xiaomuma.framework.serialize.JsonUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);

    public DefaultAuthenticationFailureHandler() {}

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.debug("AuthenticationFailure", e);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.object2Json(ViewResponse.failed(ResponseCode.UN_AUTHORIZED, "")));
        }

    }
}
