package pers.xiaomuma.base.thirdparty.wx.pay.url;


public class OutTradeNoPayQueryUrlBuilder {

    private static final String URL = "https://api.mch.weixin.qq.com/v%s/pay/transactions/out-trade-no/%s?mchid=%s";
    private int version = 3;
    private String outTradeNo;
    private String mchId;

    public OutTradeNoPayQueryUrlBuilder() {
    }

    public OutTradeNoPayQueryUrlBuilder version(int version) {
        this.version = version;
        return this;
    }

    public OutTradeNoPayQueryUrlBuilder outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public OutTradeNoPayQueryUrlBuilder mchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public String build() {
        return String.format(URL, this.version, this.outTradeNo, this.mchId);
    }
}
