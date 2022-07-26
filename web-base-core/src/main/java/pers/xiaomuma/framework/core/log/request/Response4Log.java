package pers.xiaomuma.framework.core.log.request;


import pers.xiaomuma.framework.core.log.Log;
import pers.xiaomuma.framework.core.log.LogLevel;
import pers.xiaomuma.framework.core.log.LogType;

public class Response4Log extends Log {

    /**
     * 响应时间(ms)
     */
    private long time;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 返回的http状态码
     */
    private int status;

    /**
     * 请求http消息类型
     */
    private String httpMethod;

    /**
     * 返回消息头
     */
    private String header;


    /**
     * 异常信息
     */
    private String error;

    /**
     * 请求消息体, 只有在type是spring mvc, 并且请求是json消息的情况下才有值.
     * 由于servlet request input stream的局限性, 消息体只能在业务请求读取后才能被拦截器获得, 所以只能在response里打印
     */
    private String requestBody;

    public Response4Log() {}

    public Response4Log(LogType type, LogLevel logLevel) {
        super(type, logLevel);
    }


    public void simplifyLogIfNecessary(LogLevel logLevel) {
        if(LogLevel.SIMPLE.equals(logLevel)) {
            if(this.header != null && this.header.length() > SIMPLE_MAX_SIZE) {
                this.header = this.header.substring(0, SIMPLE_MAX_SIZE);
            }

            if(this.requestBody != null && this.requestBody.length() > SIMPLE_MAX_SIZE) {
                this.requestBody = this.requestBody.substring(0, SIMPLE_MAX_SIZE);
            }

        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
