package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class CustomMsgUrlBuilder {

    private static final String CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    private String accessToken;

    public CustomMsgUrlBuilder() {

    }

    public CustomMsgUrlBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String build() {
        return String.format(CUSTOM_SEND_URL, accessToken);
    }

}
