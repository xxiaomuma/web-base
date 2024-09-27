package pers.xiaomuma.framework.standard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.exception.ServiceUnavailableException;
import pers.xiaomuma.framework.response.BaseResponse;
import java.util.function.Function;


public class RpcServiceHelper {

    private static final Logger log = LoggerFactory.getLogger(RpcServiceHelper.class);

    public static <T, R extends BaseResponse<?>> R call(String methodName, T params, Function<T, R> remoteMethod) {
        try {
            return remoteMethod.apply(params);
        } catch (Exception e) {
            log.error("{}服务异常, error", methodName, e);
        }
        throw new ServiceUnavailableException(String.format("%s服务异常，请稍后再试", methodName));
    }
}
