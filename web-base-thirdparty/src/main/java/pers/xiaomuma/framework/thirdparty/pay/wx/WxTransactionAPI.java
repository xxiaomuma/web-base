package pers.xiaomuma.framework.thirdparty.pay.wx;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import pers.xiaomuma.framework.serialize.JsonUtils;
import pers.xiaomuma.framework.thirdparty.pay.TransactionResult;
import pers.xiaomuma.framework.thirdparty.pay.wx.param.WxTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.wx.param.WxTransactionRefundParam;
import pers.xiaomuma.framework.thirdparty.pay.wx.request.WxTransactionRequest;
import pers.xiaomuma.framework.thirdparty.pay.wx.request.WxTransactionRequestManager;
import pers.xiaomuma.framework.thirdparty.utils.SignUtils;
import pers.xiaomuma.framework.thirdparty.utils.WxPayUtils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;


public class WxTransactionAPI {

    private final Logger logger = LoggerFactory.getLogger(WxTransactionAPI.class);
    private PrivateKey privateKey;
    private WxTransactionProperties properties;
    private WxTransactionRequest transactionRequest;

    public WxTransactionAPI(WxTransactionProperties properties) {
        this.properties = properties;
        this.privateKey = WxPayUtils.getPrivateKey(properties.getKeyPemPath());
        this.transactionRequest = new WxTransactionRequestManager(this.privateKey, properties);
    }

    public TransactionResult<String> transactionPayH5(WxTransactionPayParam param) {
        ResponseEntity<String> response = transactionRequest.transactionPayH5(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信H5支付Api异常, response:" + response.getBody());
        }
        Map<String, String> resp = JsonUtils.json2Map(response.getBody(), String.class, String.class);
        String h5Url = CollUtil.isEmpty(resp) ? null : resp.get("h5_url");
        if (StrUtil.isBlank(h5Url)) {
            return TransactionResult.error("微信H5支付请求失败");
        }
        return TransactionResult.success(h5Url);
    }

    public TransactionResult<Map<String, String>> transactionPayApp(WxTransactionPayParam param) {
        ResponseEntity<String> response = transactionRequest.transactionPayApp(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信APP支付Api异常, response:" + response.getBody());
        }
        Map<String, String> resp = JsonUtils.json2Map(response.getBody(), String.class, String.class);
        String prepayId = CollUtil.isEmpty(resp) ? null : resp.get("prepay_id");
        if (StrUtil.isBlank(prepayId)) {
            return TransactionResult.error("微信APP支付请求失败");
        }
        return TransactionResult.success(this.startPay(prepayId));
    }

    public TransactionResult<Map<String, String>> transactionPayJsapi(WxTransactionPayParam param) {
        ResponseEntity<String> response = transactionRequest.transactionPayJsapi(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信JSAPI支付Api异常, response:" + response.getBody());
        }
        Map<String, String> resp = JsonUtils.json2Map(response.getBody(), String.class, String.class);
        String prepayId = CollUtil.isEmpty(resp) ? null : resp.get("prepay_id");
        if (StrUtil.isBlank(prepayId)) {
            return TransactionResult.error("微信JSAPI支付请求失败");
        }
        return TransactionResult.success(this.startPay(prepayId));
    }

    public TransactionResult<String> transactionPayNative(WxTransactionPayParam param) {
        ResponseEntity<String> response = transactionRequest.transactionPayNative(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信Native支付Api异常, response:" + response.getBody());
        }
        Map<String, String> resp = JsonUtils.json2Map(response.getBody(), String.class, String.class);
        String codeUrl = CollUtil.isEmpty(resp) ? null : resp.get("code_url");
        if (StrUtil.isBlank(codeUrl)) {
            return TransactionResult.error("微信Native支付请求失败");
        }
        return TransactionResult.success(codeUrl);
    }

    public TransactionResult<Map<String, Object>> transactionRefundDomestic(WxTransactionRefundParam param) {
        ResponseEntity<String> response = transactionRequest.transactionRefundDomestic(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信RefundDomestic支付Api异常, response:" + response.getBody());
        }
        Map<String, Object> resp = JsonUtils.json2Map(response.getBody(), String.class, Object.class);
        return TransactionResult.success(resp);
    }

    public TransactionResult<Map<String, Object>> transactionQueryTransactionId(String transactionId) {
        ResponseEntity<String> response = transactionRequest.transactionQueryTransactionId(transactionId);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信QueryTransactionId支付Api异常, response:" + response.getBody());
        }
        Map<String, Object> resp = JsonUtils.json2Map(response.getBody(), String.class, Object.class);
        return TransactionResult.success(resp);
    }

    public TransactionResult<Map<String, Object>> transactionQueryOutTradeNo(String outTradeNo) {
        ResponseEntity<String> response = transactionRequest.transactionQueryOutTradeNo(outTradeNo);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return TransactionResult.error("微信QueryOutTradeNo支付Api异常, response:" + response.getBody());
        }
        Map<String, Object> resp = JsonUtils.json2Map(response.getBody(), String.class, Object.class);
        return TransactionResult.success(resp);
    }

    private Map<String, String> startPay(String prepayId) {
        String appId = this.properties.getAppId();
        String nonceStr = WxPayUtils.genNonceStr();
        long timestamp = System.currentTimeMillis() / 1000L;
        String wrap = "prepay_id=" + prepayId;
        String message = appId + "\n" + timestamp + "\n" + nonceStr + "\n" + wrap + "\n";
        String signature = SignUtils.rsaSHA256Sign(message.getBytes(StandardCharsets.UTF_8), this.privateKey);
        Map<String, String> result = new HashMap<>();
        result.put("appId", appId);
        result.put("timeStamp", timestamp + "");
        result.put("nonceStr", nonceStr);
        result.put("package", wrap);
        result.put("signType", "RSA");
        result.put("paySign", signature);
        return result;
    }

}
