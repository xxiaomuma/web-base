package pers.xiaomuma.base.web.http.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request4Log extends Log {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Request4Log.class);
    public static final String CLIENT_IP_HEADER = "X-Forwarded-For";
    private String uri;
    private String httpMethod;
    private String header;
    private String param;
    private String body;
    private String clientIp;

    public Request4Log() {
    }

    public Request4Log(LogType type, LogLevel logLevel) {
        super(type, logLevel);
    }

    public void simplifyLogIfNecessary(LogLevel logLevel) {
        if (LogLevel.SIMPLE.equals(logLevel)) {
            if (this.body != null && this.body.length() > 1000) {
                this.body = this.body.substring(0, 1000);
            }

            if (this.header != null && this.header.length() > 1000) {
                this.header = this.header.substring(0, 1000);
            }

            if (this.param != null && this.param.length() > 1000) {
                this.param = this.param.substring(0, 1000);
            }
        }
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
