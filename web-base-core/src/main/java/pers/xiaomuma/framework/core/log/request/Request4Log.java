package pers.xiaomuma.framework.core.log.request;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.core.log.Log;
import pers.xiaomuma.framework.core.log.LogLevel;
import pers.xiaomuma.framework.core.log.LogType;

public class Request4Log extends Log {

    protected static final Logger logger = LoggerFactory.getLogger(Request4Log.class);

    public static final String CLIENT_IP_HEADER = "X-Forwarded-For";

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求http消息类型
     */
    private String httpMethod;

    /**
     * 请求头
     */
    private String header;

    /**
     * 请求参数(httpServletRequest.getParameterMap获得)
     */
    private String param;

    /**
     * 请求消息体
     */
    private String body;

    /**
     * client IP address, only in spring mvc request.
     */
    private String clientIp;


    public Request4Log() {}

    public Request4Log(LogType type, LogLevel logLevel) {
        super(type, logLevel);
    }

    public void simplifyLogIfNecessary(LogLevel logLevel) {
        if(LogLevel.SIMPLE.equals(logLevel)) {
            if(this.body != null && this.body.length() > SIMPLE_MAX_SIZE) {
                this.body = this.body.substring(0, SIMPLE_MAX_SIZE);
            }
            if(this.header != null && this.header.length() > SIMPLE_MAX_SIZE) {
                this.header = this.header.substring(0, SIMPLE_MAX_SIZE);
            }
            if(this.param != null && this.param.length() > SIMPLE_MAX_SIZE) {
                this.param = this.param.substring(0, SIMPLE_MAX_SIZE);
            }
        }
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
