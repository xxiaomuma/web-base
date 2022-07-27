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
	public BaseResponse handleError(AppBizException e) {
		logger.warn("业务异常", e);
		return BaseResponse
				.builder()
				.code(e.getResultCode())
				.message(e.getViewMessage())
				.success(false)
				.build();
	}

	@ExceptionHandler(BadRequestParameterException.class)
	public BaseResponse handleError(BadRequestParameterException e) {
		logger.warn("入参错误", e);
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_TYPE_BIND_ERROR)
				.message( e.getMessage())
				.success(false)
				.build();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public BaseResponse handleError(MissingServletRequestParameterException e) {
		logger.warn("Missing Request Parameter", e);
		String message = String.format("Missing Request Parameter: %s", e.getParameterName());
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_MISS)
				.message(message)
				.success(false)
				.build();
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public BaseResponse handleError(MethodArgumentTypeMismatchException e) {
		logger.warn("Method Argument Type Mismatch", e);
		String message = String.format("Method Argument Type Mismatch: %s", e.getName());
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_TYPE_BIND_ERROR)
				.message(message)
				.success(false)
				.build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse handleError(MethodArgumentNotValidException e) {
		logger.warn("Method Argument Not Valid", e);
		BindingResult result = e.getBindingResult();
		FieldError error = result.getFieldError();
		String message = String.format("%s:%s", Objects.isNull(error) ? "" : error.getField(), Objects.isNull(error) ? "" : error.getDefaultMessage());
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_IS_VALID)
				.message(message)
				.success(false)
				.build();
	}

	@ExceptionHandler(BindException.class)
	public BaseResponse handleError(BindException e) {
		logger.warn("Bind Exception", e);
		FieldError error = e.getFieldError();
		String message = String.format("%s:%s", Objects.isNull(error) ? "" : error.getField(), Objects.isNull(error) ? "" : error.getDefaultMessage());
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_TYPE_BIND_ERROR)
				.message(message)
				.success(false)
				.build();
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public BaseResponse handleError(HttpRequestMethodNotSupportedException e) {
		logger.warn("Http Request Method Not Supported Exception", e);
		return BaseResponse
				.builder()
				.code(ResponseCode.METHOD_NOT_ALLOWED)
				.message("请求方法不支持")
				.success(false)
				.build();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public BaseResponse handleError(ConstraintViolationException e) {
		logger.warn("Constraint Violation", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
		String message = String.format("%s:%s", path, violation.getMessage());
		return BaseResponse
				.builder()
				.code(ResponseCode.PARAM_IS_VALID)
				.message(message)
				.success(false)
				.build();
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public BaseResponse handleError(NoHandlerFoundException e) {
		logger.error("404 Not Found", e);
		return BaseResponse
				.builder()
				.code(ResponseCode.NOT_FOUND)
				.message(e.getMessage())
				.success(false)
				.build();
	}

	@ExceptionHandler(InternalServerErrorException.class)
	public BaseResponse handleError(InternalServerErrorException e) {
		logger.error("Internal Server Error Exception", e);
		return BaseResponse
				.builder()
				.code(e.getResultCode())
				.message(e.getMessage())
				.success(false)
				.build();
	}

	@ExceptionHandler(Throwable.class)
	public BaseResponse handleError(Throwable e) {
		logger.error("Internal Server Error", e);
		return BaseResponse
				.builder()
				.code(ResponseCode.INTERNAL_SERVER_ERROR)
				.message(e.getMessage())
				.success(false)
				.build();
	}

}
