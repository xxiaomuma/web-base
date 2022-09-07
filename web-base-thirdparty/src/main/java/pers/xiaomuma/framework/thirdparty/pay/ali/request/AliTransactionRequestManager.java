package pers.xiaomuma.framework.thirdparty.pay.ali.request;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import pers.xiaomuma.framework.thirdparty.pay.ali.AliTransactionProperties;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionRefundParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.url.AliTransactionUrlBuilder;


public class AliTransactionRequestManager implements AliTransactionRequest {

    private AliTransactionProperties properties;

    public AliTransactionRequestManager(AliTransactionProperties properties) {
        this.properties = properties;
    }

    @Override
    public AlipayTradePagePayResponse transactionPayPage(AliTransactionPayParam param) throws AlipayApiException {
        AlipayClient alipayClient = this.buildDefaultAlipayClient();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl("");
        request.setNotifyUrl(properties.getNotifyUrl());
        request.setBizContent(param.parse2BizContentRequestBody());
        return alipayClient.execute(request);
    }

    @Override
    public AlipayTradePrecreateResponse transactionPayNative(AliTransactionPayParam param) throws AlipayApiException {
        AlipayClient alipayClient = this.buildDefaultAlipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(properties.getNotifyUrl());
        request.setBizContent(param.parse2BizContentRequestBody());
        return alipayClient.execute(request);
    }

    @Override
    public AlipayTradeRefundResponse transactionRefund(AliTransactionRefundParam param) throws AlipayApiException {
        AlipayClient alipayClient = this.buildDefaultAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(param.parse2BizContentRequestBody());
        return alipayClient.execute(request);
    }

    @Override
    public AlipayTradeQueryResponse transactionQueryTransactionId(String transactionId) throws AlipayApiException {
        AlipayClient alipayClient = this.buildDefaultAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "  \"trade_no\":\""+transactionId+"\"," +
                "}");
        return alipayClient.execute(request);
    }

    @Override
    public AlipayTradeQueryResponse transactionQueryOutTradeNo(String outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = this.buildDefaultAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "  \"out_trade_no\":\""+outTradeNo+"\"," +
                "}");
        return alipayClient.execute(request);
    }

    private AlipayClient buildDefaultAlipayClient() {
        String requestAddress = AliTransactionUrlBuilder.build();
        return new DefaultAlipayClient(requestAddress, properties.getAppId(), properties.getPrivateKey(), "json", "utf-8", properties.getPublicKey(), "RSA2");
    }
}
