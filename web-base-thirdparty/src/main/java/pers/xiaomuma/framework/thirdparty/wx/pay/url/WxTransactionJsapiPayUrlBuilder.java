package pers.xiaomuma.framework.thirdparty.wx.pay.url;

public class WxTransactionJsapiPayUrlBuilder {

    private final static String WX_JSAPI_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    public String build() {
        return WX_JSAPI_PAY_URL;
    }

}
