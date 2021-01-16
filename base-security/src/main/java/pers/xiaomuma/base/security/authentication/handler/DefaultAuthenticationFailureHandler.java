package pers.xiaomuma.base.security.authentication.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pers.xiaomuma.base.common.api.CommonCode;
import pers.xiaomuma.base.common.api.ResultDTO;
import pers.xiaomuma.base.common.utils.JsonUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);

    public DefaultAuthenticationFailureHandler() {}

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.debug("AuthenticationFailure", e);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed");
        } else {
            HashMap<String, String> data = new HashMap<>(2);
            data.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            data.put("error_msg", e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.object2Json(ResultDTO.create(CommonCode.UNAUTHORIZED, data)));
        }

    }
}
