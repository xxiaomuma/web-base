package pers.xiaomuma.base.web;

import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import pers.xiaomuma.base.web.api.CommonCode;
import pers.xiaomuma.base.web.api.IResultCode;
import pers.xiaomuma.base.web.api.ResultDTO;
import pers.xiaomuma.base.web.common.BaseBusinessException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;



@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionTranslator.class);

    private final ApplicationEnv applicationEnv;

    @ExceptionHandler(BaseBusinessException.class)
    public ResultDTO<?> handleBusinessError(BaseBusinessException var) {
        if (!applicationEnv.isProdProfile()) {
            LOGGER.info("业务异常", var);
        }
        IResultCode resultCode = var.getCode();
        if (var.isShouldPrintCustomizedMsg()) {
            return ResultDTO.create(resultCode.getCode(), var.getMessage());
        }
        return ResultDTO.create(resultCode);
    }

    @ExceptionHandler(BindException.class)
    public ResultDTO<?> handleDataBindError(BindException var) {
        if (!applicationEnv.isProdProfile()) {
            LOGGER.info("参数验证错误", var);
        }
        IResultCode badRequestCode = CommonCode.BAD_REQUEST;
        String errorMsg = extractErrorMsg(var.getAllErrors(), badRequestCode.getMsg());
        return ResultDTO.create(badRequestCode.getCode(), errorMsg);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResultDTO<?> handleBadRequestError(Exception var) {
        if (!applicationEnv.isProdProfile()) {
            LOGGER.info("请求错误", var);
        }
        return ResultDTO.create(CommonCode.BAD_REQUEST, var.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultDTO<?> handleMethodNotSupportedError(HttpRequestMethodNotSupportedException var) {
        if (!applicationEnv.isProdProfile()) {
            LOGGER.info("不支持该请求方法", var);
        }
        return ResultDTO.create(CommonCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultDTO<?> handleNoHandlerFoundError(NoHandlerFoundException var) {
        if (!applicationEnv.isProdProfile()) {
            LOGGER.info("未找到请求处理器", var);
        }
        return ResultDTO.create(CommonCode.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResultDTO<?> handleUncatchableError(Throwable var) {
        LOGGER.error("服务器错误", var);
        return ResultDTO.create(CommonCode.INTERNAL_SERVER_ERROR);
    }

    private String extractErrorMsg(List<ObjectError> allErrors, String defaultMessage) {
        if (allErrors == null || allErrors.isEmpty()) {
            return defaultMessage;
        } else {
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(errorMessages);
        }
    }
}

