package pers.xiaomuma.framework.rpc.error;

public class ErrorInfo {

    private int code;

    private String message;

    private String requestUri;

    private int status;

    public ErrorInfo() {

    }

    public ErrorInfo(int code, String requestUri, String message) {
        this(code, 500, requestUri, message);
    }

    public ErrorInfo(int code, int status, String requestUri, String message) {
        this.code = code;
        this.requestUri = requestUri;
        this.message = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", status=" + status +
                '}';
    }

}
