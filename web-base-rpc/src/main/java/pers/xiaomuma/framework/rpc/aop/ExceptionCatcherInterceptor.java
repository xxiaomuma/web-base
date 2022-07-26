package pers.xiaomuma.framework.rpc.aop;


import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import pers.xiaomuma.framework.exception.AppBizException;

@Aspect
@Order(10)
@Configuration
public class ExceptionCatcherInterceptor {

	private Logger logger = LoggerFactory.getLogger(ExceptionCatcherInterceptor.class);

	private final static String DEFAULT_ERROR_MESSAGE = "服务器错误";

	@Around(value = "@annotation(catcher)")
	public Object proceed(ProceedingJoinPoint pjp, ExceptionCatcher catcher) throws Throwable {
		Object result = null;
		try {
			result =  pjp.proceed();
		} catch (Throwable throwable) {
			handleCatchesException(catcher, throwable);
		}
		return result;
	}

	private void handleCatchesException(ExceptionCatcher catcher, Throwable throwable) throws Throwable {
		String message = StrUtil.isBlank(catcher.message()) ? DEFAULT_ERROR_MESSAGE : catcher.message();
		boolean targetException = isTargetException(throwable, catcher.catchFor());
		if (targetException) {
			switch (catcher.catchType()) {
				case SWALLOW:
					break;
                case CATCH_AND_LOG :
					logger.error("", throwable);
					break;
				case RETHROW:
					throw new RuntimeException(message);
				case RETHROW_BIZ:
					throw new AppBizException(message);
				case RETHROW_CUSTOM:
					throw catcher.throwing().newInstance();
			}
		} else  {
			throw throwable;
		}
	}

	private boolean isTargetException(Throwable throwable, Class<? extends Throwable>[] throwableArray) {
		boolean targetException = false;
		for (Class<? extends Throwable> clazz : throwableArray) {
			if (clazz.isAssignableFrom(throwable.getClass())) {
				targetException = true;
				break;
			}
		}
		return targetException;
	}

}
