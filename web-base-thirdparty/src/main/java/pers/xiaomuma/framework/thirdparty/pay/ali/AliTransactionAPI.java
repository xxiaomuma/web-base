package pers.xiaomuma.framework.thirdparty.pay.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.thirdparty.pay.TransactionResult;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionRefundParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.request.AliTransactionRequest;
import pers.xiaomuma.framework.thirdparty.pay.ali.request.AliTransactionRequestManager;
import java.util.Map;


public class AliTransactionAPI {

    private final Logger logger = LoggerFactory.getLogger(AliTransactionAPI.class);
    private final AliTransactionRequest transactionRequest;

    public AliTransactionAPI(AliTransactionProperties properties) {
        this.transactionRequest = new AliTransactionRequestManager(properties);
    }

    public TransactionResult<String> transactionPayPage(AliTransactionPayParam param) {
        AlipayTradePagePayResponse response = null;
        try {
            response = transactionRequest.transactionPayPage(param);
            if (response.isSuccess()) {
                return TransactionResult.success(response.getBody());
            }
        } catch (AlipayApiException e) {
            return TransactionResult.error("支付宝电脑网站支付Api异常, error:" + e.getMessage());
        }
        return TransactionResult.error(response.getMsg());
    }

    public TransactionResult<String> transactionPayNative(AliTransactionPayParam param) {
        AlipayTradePrecreateResponse response = null;
        try {
            response = transactionRequest.transactionPayNative(param);
            if (response.isSuccess()) {
                return TransactionResult.success(response.getQrCode());
            }
        } catch (AlipayApiException e) {
            return TransactionResult.error("支付宝当面付Api异常, error:" + e.getMessage());
        }
        return TransactionResult.error(response.getMsg());
    }

    public TransactionResult<Map<String, String>> transactionRefund(AliTransactionRefundParam param) {
        AlipayTradeRefundResponse response = null;
        try {
            response = transactionRequest.transactionRefund(param);
            if (response.isSuccess()) {
                return TransactionResult.success(response.getParams());
            }
        } catch (AlipayApiException e) {
            return TransactionResult.error("支付宝退款申请Api异常, error:" + e.getMessage());
        }
        return TransactionResult.error(response.getMsg());
    }

    public TransactionResult<Map<String, String>> transactionQueryTransactionId(String transactionId) {
        AlipayTradeQueryResponse response = null;
        try {
            response = transactionRequest.transactionQueryTransactionId(transactionId);
            if (response.isSuccess()) {
                return TransactionResult.success(response.getParams());
            }
        } catch (AlipayApiException e) {
            return TransactionResult.error("支付宝transactionId查询Api异常, error:" + e.getMessage());
        }
        return TransactionResult.error(response.getMsg());
    }

    public TransactionResult<Map<String, String>> transactionQueryOutTradeNo(String outTradeNo) {
        AlipayTradeQueryResponse response = null;
        try {
            response = transactionRequest.transactionQueryOutTradeNo(outTradeNo);
            if (response.isSuccess()) {
                return TransactionResult.success(response.getParams());
            }
        } catch (AlipayApiException e) {
            return TransactionResult.error("支付宝outTradeNo查询Api异常, error:" + e.getMessage());
        }
        return TransactionResult.error(response.getMsg());
    }
}
