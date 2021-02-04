package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class UniformTemplateMsgUrlBuilder {

    private static final String UNIFORM_SEND = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=%s";

    private String accessToken;

    public UniformTemplateMsgUrlBuilder() {

    }

    public UniformTemplateMsgUrlBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String build() {
        return String.format(UNIFORM_SEND, accessToken);
    }

}
