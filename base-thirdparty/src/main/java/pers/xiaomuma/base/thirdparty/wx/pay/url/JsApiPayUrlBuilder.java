package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class JsApiPayUrlBuilder {

    private static final String JS_API_PAY = "https://api.mch.weixin.qq.com/v%s/pay/transactions/jsapi";
    private int version = 3;

    public JsApiPayUrlBuilder() {
    }

    public JsApiPayUrlBuilder version(int version) {
        this.version = version;
        return this;
    }

    public String build() {
        return String.format("https://api.mch.weixin.qq.com/v%s/pay/transactions/jsapi", this.version);
    }
}
