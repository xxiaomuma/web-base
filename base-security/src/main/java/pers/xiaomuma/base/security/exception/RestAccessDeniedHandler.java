package pers.xiaomuma.base.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pers.xiaomuma.base.common.api.CommonCode;
import pers.xiaomuma.base.common.api.ResultDTO;
import pers.xiaomuma.base.common.utils.JsonUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);
    private static final String DEFAULT_ERR_MSG;

    static {
        DEFAULT_ERR_MSG = JsonUtils.object2Json(ResultDTO.create(CommonCode.UNAUTHORIZED));
    }

    public RestAccessDeniedHandler () {}

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException var) throws IOException, ServletException {
        this.logger.error("Handle Access Denied", var);
        if (!response.isCommitted()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(DEFAULT_ERR_MSG);
        }
    }
}
