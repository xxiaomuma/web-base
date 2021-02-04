package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class UnLimitUrlBuilder {

    private final static String UN_LIMIT_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    private String accessToken;

    public UnLimitUrlBuilder() {}

    public UnLimitUrlBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String build() {
        return String.format(UN_LIMIT_URL, accessToken);
    }

}
