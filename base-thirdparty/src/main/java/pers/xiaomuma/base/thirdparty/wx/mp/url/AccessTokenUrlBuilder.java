package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class AccessTokenUrlBuilder {

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private String appId;
    private String secret;

    public AccessTokenUrlBuilder() {}

    public AccessTokenUrlBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public AccessTokenUrlBuilder secret(String secret) {
        this.secret = secret;
        return this;
    }

    public String build() {
        return String.format(ACCESS_TOKEN_URL, appId, secret);
    }

}
