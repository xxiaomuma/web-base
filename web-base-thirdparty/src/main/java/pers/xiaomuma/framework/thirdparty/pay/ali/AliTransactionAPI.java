package pers.xiaomuma.framework.thirdparty.pay.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.thirdparty.pay.ali.param.AliTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.ali.request.AliTransactionRequest;
import pers.xiaomuma.framework.thirdparty.pay.ali.request.AliTransactionRequestManager;


public class AliTransactionAPI {

    private final Logger logger = LoggerFactory.getLogger(AliTransactionAPI.class);
    private AliTransactionRequest transactionRequest;

    public AliTransactionAPI(AliTransactionProperties properties) {
        this.transactionRequest = new AliTransactionRequestManager(properties);
    }

    public void transactionPayNative(AliTransactionPayParam param) {
        try {
            AlipayTradePrecreateResponse response = transactionRequest.transactionPayNative(param);
        } catch (AlipayApiException e) {}
    }
}
