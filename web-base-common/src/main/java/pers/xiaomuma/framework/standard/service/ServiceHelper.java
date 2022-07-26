package pers.xiaomuma.framework.standard.service;

import lombok.SneakyThrows;
import pers.xiaomuma.framework.exception.InternalBizException;
import java.util.concurrent.Callable;


public class ServiceHelper {

	@SneakyThrows
	public static <T> ServiceResult<T> processing(Callable<T> callable) {
		T returnValue;
		try {
			returnValue = callable.call();
		} catch (InternalBizException e) {
			return ServiceResult.failed(e.getMessage());
		}
		return ServiceResult.success(returnValue);
	}

	@SneakyThrows
	public static ServiceResult<Void> processing(Runnable runnable) {
		try {
			runnable.run();
		} catch (InternalBizException e) {
			return ServiceResult.failed(e.getMessage());
		}
		return ServiceResult.success();
	}

}
