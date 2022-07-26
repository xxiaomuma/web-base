package pers.xiaomuma.framework.rpc.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.xiaomuma.framework.exception.AppBizException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class MsExceptionTranslator {

	@ExceptionHandler(AppBizException.class)
	public ErrorInfo handleError(HttpServletRequest request, HttpServletResponse response, AppBizException e) {
		ErrorCode errorCode = CommonErrorCode.REQUEST_SERVICE_ERROR;
		response.setStatus(errorCode.getStatus());
		return new ErrorInfo(601, errorCode.getStatus(), request.getRequestURI(), e.getViewMessage());
	}

	@ExceptionHandler(Exception.class)
	public ErrorInfo handleError(HttpServletRequest request, HttpServletResponse response, Exception e) {
		ErrorCode errorCode = CommonErrorCode.REQUEST_SERVICE_ERROR;
		response.setStatus(errorCode.getStatus());
		return new ErrorInfo(500, errorCode.getStatus(), request.getRequestURI(), e.getMessage());
	}


}
