package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionRefundUrlBuilder {


    private static final String WX_REFUND_URL = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

    public String build() {
        return WX_REFUND_URL;
    }
}
