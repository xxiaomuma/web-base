package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class Code2SessionUrlBuilder {

    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    private String appId;
    private String secret;
    private String jsCode;

    public Code2SessionUrlBuilder() {}

    public Code2SessionUrlBuilder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public Code2SessionUrlBuilder secret(String secret) {
        this.secret = secret;
        return this;
    }

    public Code2SessionUrlBuilder jsCode(String jsCode) {
        this.jsCode = jsCode;
        return this;
    }

    public String build() {
        return String.format(CODE2SESSION_URL, appId, secret, jsCode);
    }

}
