package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionPayH5UrlBuilder {

    private static final String WX_H5_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";

    public String build() {
        return WX_H5_PAY_URL;
    }

}
