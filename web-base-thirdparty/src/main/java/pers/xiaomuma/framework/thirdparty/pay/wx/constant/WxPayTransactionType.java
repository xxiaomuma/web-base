package pers.xiaomuma.framework.thirdparty.pay.wx.constant;

public enum WxPayTransactionType {

    // https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml
    JSAPI(1, "JSAPI支付"),
    // https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_3_1.shtml
    H5(2, "H5支付"),
    // https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_1.shtml
    APP(3, "APP支付"),
    // https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml
    NATIVE(4, "NATIVE支付");

    private final int code;
    private final String desc;

    WxPayTransactionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
