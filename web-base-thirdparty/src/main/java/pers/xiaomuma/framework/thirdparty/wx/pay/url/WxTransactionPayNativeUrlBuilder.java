package pers.xiaomuma.framework.thirdparty.wx.pay.url;

public class WxTransactionPayNativeUrlBuilder {

    private final static String WX_NATIVE_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";

    public String build() {
        return WX_NATIVE_PAY_URL;
    }
}
