package pers.xiaomuma.base.web.http.log;


public class Response4Log extends Log{

    private long time;
    private String uri;
    private int status;
    private String httpMethod;
    private String header;
    private String error;
    private String requestBody;

    public Response4Log() {
    }

    public Response4Log(LogType type, LogLevel logLevel) {
        super(type, logLevel);
    }

    public void simplifyLogIfNecessary(LogLevel logLevel) {
        if (LogLevel.SIMPLE.equals(logLevel)) {
            if (this.header != null && this.header.length() > 1000) {
                this.header = this.header.substring(0, 1000);
            }

            if (this.requestBody != null && this.requestBody.length() > 1000) {
                this.requestBody = this.requestBody.substring(0, 1000);
            }
        }
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}
