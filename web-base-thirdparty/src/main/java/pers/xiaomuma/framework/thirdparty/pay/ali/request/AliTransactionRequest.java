package pers.xiaomuma.framework.thirdparty.pay.ali.request;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionRefundParam;

public interface AliTransactionRequest {

    AlipayTradePrecreateResponse transactionPayNative(AliTransactionPayParam param) throws AlipayApiException;

    AlipayTradeRefundResponse transactionRefund(AliTransactionRefundParam param) throws AlipayApiException;

    AlipayTradeQueryResponse transactionQueryTransactionId(String transactionId) throws AlipayApiException;

    AlipayTradeQueryResponse transactionQueryOutTradeNo(String outTradeNo) throws AlipayApiException;
}
