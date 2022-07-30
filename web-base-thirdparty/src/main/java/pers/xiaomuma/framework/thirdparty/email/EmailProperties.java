package pers.xiaomuma.framework.thirdparty.email;

public class EmailProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private Integer timeout = 5000;

    private Integer asyncThreadNum = 3;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getAsyncThreadNum() {
        return asyncThreadNum;
    }

    public void setAsyncThreadNum(Integer asyncThreadNum) {
        this.asyncThreadNum = asyncThreadNum;
    }
}
