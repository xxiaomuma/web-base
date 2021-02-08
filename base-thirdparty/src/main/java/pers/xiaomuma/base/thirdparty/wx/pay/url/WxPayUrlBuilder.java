package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class WxPayUrlBuilder {

    public static JsApiPayUrlBuilder jsApiPayUrlBuilder() {
        return new JsApiPayUrlBuilder();
    }

    public static RefundApplyV3UrlBuilder wxRefundApplyV3UrlBuilder() {
        return new RefundApplyV3UrlBuilder();
    }

    public static RefundApplyV2UrlBuilder refundApplyV2UrlBuilder() {
        return new RefundApplyV2UrlBuilder();
    }

    public static OutTradeNoPayQueryUrlBuilder outTradeNoPayQueryUrlBuilder() {
        return new OutTradeNoPayQueryUrlBuilder();
    }

    public static CompanyTransfersUrlBuilder companyTransfersUrlBuilder() {
        return new CompanyTransfersUrlBuilder();
    }
}
