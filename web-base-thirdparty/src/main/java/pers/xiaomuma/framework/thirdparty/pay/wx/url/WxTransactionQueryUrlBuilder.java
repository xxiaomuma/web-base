package pers.xiaomuma.framework.thirdparty.pay.wx.url;

public class WxTransactionQueryUrlBuilder {

    private static final String ID_QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/id/%s?mchid=%s";
    private static final String OUT_TRADE_NO_QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s?mchid=%s";

    private String mchId;
    private String outTradeNo;
    private String transactionId;

    public WxTransactionQueryUrlBuilder mchId(String mchId) {
        this.mchId = mchId;
        return this;
    }

    public WxTransactionQueryUrlBuilder outTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public WxTransactionQueryUrlBuilder transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String buildIdQuery() {
        return String.format(ID_QUERY_URL, transactionId, mchId);
    }

    public String buildOutTradeNoQuery() {
        return String.format(OUT_TRADE_NO_QUERY_URL, outTradeNo, mchId);
    }

}
