package pers.xiaomuma.framework.rpc.feign.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import pers.xiaomuma.framework.rpc.error.ErrorInfo;
import pers.xiaomuma.framework.rpc.error.RemoteCallException;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.io.IOException;

public class FeignErrorHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        ErrorInfo error = new ErrorInfo(500, "", "响应失败!");
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader());
                if(StringUtils.isNotBlank(body)) {
                    try {
                        body = body.trim();
                        error = JsonUtils.json2Object(body, ErrorInfo.class);
                    } catch (Exception ignore) {
                    }
                }
            }
        } catch (IOException ignored) { // NOPMD
        }
        return new RemoteCallException(error);
    }


}
