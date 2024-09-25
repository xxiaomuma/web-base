package pers.xiaomuma.framework.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pers.xiaomuma.framework.response.BaseResponse;
import pers.xiaomuma.framework.response.ResponseCode;
import pers.xiaomuma.framework.serialize.JsonUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RestForbiddenEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(RestForbiddenEntryPoint.class);
    private static final String DEFAULT_ERR_MSG;

    static {
        DEFAULT_ERR_MSG = JsonUtils.object2Json(BaseResponse.failed(ResponseCode.UN_AUTHORIZED));
    }

    public RestForbiddenEntryPoint() {}

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException var) throws IOException, ServletException {
        this.logger.error("Handle Forbidden");
        if (!response.isCommitted()) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(DEFAULT_ERR_MSG);
        }
    }
}
