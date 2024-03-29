package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionUrlBuilder {

    public static WxTransactionPayAppUrlBuilder transactionPayAppUrl() {
        return new WxTransactionPayAppUrlBuilder();
    }

    public static WxTransactionPayH5UrlBuilder transactionPayH5Url() {
        return new WxTransactionPayH5UrlBuilder();
    }

    public static WxTransactionJsapiPayUrlBuilder transactionJsapiPayUrl() {
        return new WxTransactionJsapiPayUrlBuilder();
    }

    public static WxTransactionPayNativeUrlBuilder transactionPayNativeUrl() {
        return new WxTransactionPayNativeUrlBuilder();
    }

    public static WxTransactionRefundUrlBuilder transactionRefundUrl() {
        return new WxTransactionRefundUrlBuilder();
    }

    public static WxTransactionQueryUrlBuilder transactionQueryUrl() {
        return new WxTransactionQueryUrlBuilder();
    }

}
