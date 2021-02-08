package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class CompanyTransfersUrlBuilder {

    private static final String URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public CompanyTransfersUrlBuilder() {
    }

    public String build() {
        return URL;
    }
}
