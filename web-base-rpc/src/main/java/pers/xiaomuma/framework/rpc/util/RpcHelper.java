package pers.xiaomuma.framework.rpc.util;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.response.BaseResponse;
import pers.xiaomuma.framework.rpc.error.RemoteCallException;

import java.util.concurrent.Callable;

public class RpcHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(RpcHelper.class);

	@SneakyThrows
	public static <T extends BaseResponse> T handleRpc(Callable<T> callable) {
		try {
			return callable.call();
		} catch (RemoteCallException e) {
			LOGGER.error("远程调用异常", e);
			return null;
		}
	}

}
