package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class RefundApplyV3UrlBuilder {

    private static final String URL = "https://api.mch.weixin.qq.com/v3/ecommerce/refunds/apply";

    public RefundApplyV3UrlBuilder() {
    }

    public String build() {
        return URL;
    }
}
