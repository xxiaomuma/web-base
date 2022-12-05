package pers.xiaomuma.framework.thirdparty.pay.ali.url;

public class AliTransactionUrlBuilder {

    private static final String ALI_TRANSACTION_URL = "https://openapi.alipay.com/gateway.do";

    public static String build() {
        return ALI_TRANSACTION_URL;
    }
}
