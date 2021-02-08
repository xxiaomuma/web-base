package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class RefundApplyV2UrlBuilder {

    private static final String URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public RefundApplyV2UrlBuilder() {
    }

    public String build() {
        return URL;
    }
}
