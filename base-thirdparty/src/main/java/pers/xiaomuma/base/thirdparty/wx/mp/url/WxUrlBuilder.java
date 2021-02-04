package pers.xiaomuma.base.thirdparty.wx.mp.url;


public class WxUrlBuilder {

    public static AccessTokenUrlBuilder accessTokenUrlBuilder() {
        return new AccessTokenUrlBuilder();
    }

    public static Code2SessionUrlBuilder code2SessionUrlBuilder() {
        return new Code2SessionUrlBuilder();
    }

    public static UserInfoUrlBuilder userInfoUrlBuilder () {
        return userInfoUrlBuilder();
    }

    public static CustomMsgUrlBuilder customMsgUrlBuilder() {
        return new CustomMsgUrlBuilder();
    }

    public static UniformTemplateMsgUrlBuilder uniformTemplateMsgUrlBuilder () {
        return new UniformTemplateMsgUrlBuilder();
    }

    public static UnLimitUrlBuilder unLimitUrlBuilder () {
        return new UnLimitUrlBuilder();
    }
}
