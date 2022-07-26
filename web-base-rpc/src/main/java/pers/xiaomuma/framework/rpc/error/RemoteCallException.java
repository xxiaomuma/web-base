package pers.xiaomuma.framework.rpc.error;


import com.netflix.hystrix.exception.HystrixBadRequestException;

public class RemoteCallException extends HystrixBadRequestException {

    private ErrorInfo errorInfo;

    public RemoteCallException(ErrorInfo error) {
        super(String.format("调用远程服务异常, errorInfo[%s]", error.toString()));
        this.errorInfo = error;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
