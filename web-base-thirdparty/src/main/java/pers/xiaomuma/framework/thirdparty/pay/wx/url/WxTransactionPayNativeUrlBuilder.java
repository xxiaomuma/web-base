package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionPayNativeUrlBuilder {

    private static final String WX_NATIVE_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";

    public String build() {
        return WX_NATIVE_PAY_URL;
    }
}
