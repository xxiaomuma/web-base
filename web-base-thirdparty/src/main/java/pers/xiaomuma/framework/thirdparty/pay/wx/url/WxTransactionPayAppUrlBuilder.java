package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionPayAppUrlBuilder {

    private static final String WX_APP_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    public String build() {
        return WX_APP_PAY_URL;
    }
}
