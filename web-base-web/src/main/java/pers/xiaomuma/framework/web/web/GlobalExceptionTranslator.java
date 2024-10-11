package pers.xiaomuma.framework.web.web;


import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import pers.xiaomuma.framework.exception.AppBizException;
import pers.xiaomuma.framework.exception.BadRequestParameterException;
import pers.xiaomuma.framework.exception.InternalServerErrorException;
import pers.xiaomuma.framework.exception.RpcException;
import pers.xiaomuma.framework.response.BaseResponse;
import pers.xiaomuma.framework.response.ResponseCode;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionTranslator {

	static final Logger logger = LoggerFactory.getLogger(GlobalExceptionTranslator.class);

	@ExceptionHandler(AppBizException.class)
	public <T> BaseResponse<T> handleError(AppBizException e) {
		logger.warn("业务异常", e);
		return BaseResponse.failed(e.getMessage(), e.getResultCode());
	}

	@ExceptionHandler(BadRequestParameterException.class)
	public <T> BaseResponse<T> handleError(BadRequestParameterException e) {
		logger.warn("入参错误", e);
		return BaseResponse.failed(e.getMessage(), ResponseCode.PARAM_TYPE_BIND_ERROR);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public <T> BaseResponse<T> handleError(MissingServletRequestParameterException e) {
		logger.warn("Missing Request Parameter", e);
		String message = String.format("Missing Request Parameter: %s", e.getParameterName());
		return BaseResponse.failed(message, ResponseCode.PARAM_MISS);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public <T> BaseResponse<T> handleError(MethodArgumentTypeMismatchException e) {
		logger.warn("Method Argument Type Mismatch", e);
		String message = String.format("Method Argument Type Mismatch: %s", e.getName());
		return BaseResponse.failed(message, ResponseCode.PARAM_TYPE_BIND_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public <T> BaseResponse<T> handleError(MethodArgumentNotValidException e) {
		logger.warn("Method Argument Not Valid", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String message = String.format("%s:%s", Objects.isNull(error) ? "" : error.getField(), Objects.isNull(error) ? "" : error.getDefaultMessage());
		return BaseResponse.failed(message, ResponseCode.PARAM_IS_VALID);
	}

	@ExceptionHandler(BindException.class)
	public <T> BaseResponse<T> handleError(BindException e) {
		logger.warn("Bind Exception", e);
		FieldError error = e.getFieldError();
		String message = String.format("%s:%s", Objects.isNull(error) ? "" : error.getField(), Objects.isNull(error) ? "" : error.getDefaultMessage());
		return BaseResponse.failed(message, ResponseCode.PARAM_TYPE_BIND_ERROR);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public <T> BaseResponse<T> handleError(HttpRequestMethodNotSupportedException e) {
		logger.warn("Http Request Method Not Supported Exception", e);
		return BaseResponse.failed("请求方法不支持", ResponseCode.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public <T> BaseResponse<T> handleError(ConstraintViolationException e) {
		logger.warn("Constraint Violation", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
		String message = String.format("%s:%s", path, violation.getMessage());
		return BaseResponse.failed(message, ResponseCode.PARAM_IS_VALID);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public <T> BaseResponse<T> handleError(NoHandlerFoundException e) {
		logger.error("404 Not Found", e);
		return BaseResponse.failed(e.getMessage(), ResponseCode.NOT_FOUND);
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public <T> BaseResponse<T> handleError(InternalServerErrorException e) {
		logger.error("Internal Server Error Exception", e);
		return BaseResponse.failed(e.getMessage(), e.getResultCode());
	}

	@ExceptionHandler(Throwable.class)
	public <T> BaseResponse<T> handleError(Throwable e) {
		logger.error("Internal Server Error", e);
		return BaseResponse.failed(e.getMessage(), ResponseCode.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RpcException.class)
	public <T> BaseResponse<T> handleError(RpcException e) {
		logger.error("RPC Error Exception", e);
		return BaseResponse.failed(String.format("服务异常，%s", e.getMessage()), ResponseCode.SERVER_RPC_ERROR);
	}

}
