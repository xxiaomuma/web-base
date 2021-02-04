package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class UserInfoUrlBuilder {

    private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    private String accessToken;
    private String openId;

    public UserInfoUrlBuilder() {}

    public UserInfoUrlBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public UserInfoUrlBuilder openId(String openId) {
        this.openId = openId;
        return this;
    }

    public String build() {
        return String.format(USER_INFO_URL, accessToken, openId);
    }

}
