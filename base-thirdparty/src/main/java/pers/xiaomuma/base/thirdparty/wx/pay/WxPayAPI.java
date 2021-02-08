package pers.xiaomuma.base.thirdparty.wx.pay;


import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.thirdparty.wx.WxResult;
import pers.xiaomuma.base.thirdparty.wx.config.WxPayProperties;
import pers.xiaomuma.base.thirdparty.wx.pay.param.CompanyTransfersV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.JsapiPayV3Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.PayRefundV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.RefundApplyV3Param;
import pers.xiaomuma.base.thirdparty.wx.pay.request.WxPayRequestManager;
import pers.xiaomuma.base.thirdparty.wx.utils.SignUtils;
import pers.xiaomuma.base.thirdparty.wx.utils.WxPayUtils;
import pers.xiaomuma.base.thirdparty.wx.utils.WxXMLUtils;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WxPayAPI {

    private PrivateKey privateKey;
    private WxPayProperties properties;
    private WxPayRequestManager requestManager;
    private Logger logger = LoggerFactory.getLogger(WxPayAPI.class);

    public WxPayAPI(WxPayProperties properties) {
        this.properties = properties;
        this.requestManager = new WxPayRequestManager(properties);
        this.privateKey = WxPayUtils.getPrivateKey(properties.getKeyPemPath());
    }

    public WxResult<Map<String, String>> wxJspaiPayV3(JsapiPayV3Param param) {
        ResponseEntity<String> response = this.requestManager.jsapiPayV3(param);
        logger.debug(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return WxResult.error("微信支付Api异常, response:" + response.getBody());
        } else {
            Map<String, String> resp = JsonUtils.json2Map(response.getBody(), String.class, String.class);
            String prepayId = resp.get("prepay_id");
            return WxResult.success(this.jsapiStartPay(prepayId));
        }
    }

    public WxResult<Map<String, Object>> outTradeNoPayQueryV3(String outTradeNo) {
        ResponseEntity<String> response = this.requestManager.outTradeNoPayQueryV3(outTradeNo);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return WxResult.error("微信支付Api异常, response:" + response.getBody());
        } else {
            Map<String, Object> resp = JsonUtils.json2Map(response.getBody(), String.class, Object.class);
            return WxResult.success(resp);
        }
    }

    public WxResult<Map<String, Object>> applyRefundV3(RefundApplyV3Param param) {
        ResponseEntity<String> response = this.requestManager.refundApplyV3(param);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return WxResult.error("微信支付Api异常, response:" + response.getBody());
        } else {
            Map<String, Object> resp = JsonUtils.json2Map(response.getBody(), String.class, Object.class);
            return WxResult.success(resp);
        }
    }

    public WxResult<Map<String, Object>> payRefundV2(PayRefundV2Param param) {
        ResponseEntity<String> response = this.requestManager.payRefundV2(param);
        return !response.getStatusCode().is2xxSuccessful() ? WxResult.error("微信支付Api异常, response:" + response.getBody()) : this.handleXMLResponse(response);
    }

    public WxResult<Map<String, Object>> companyTransferV2(CompanyTransfersV2Param param) {
        ResponseEntity<String> response = this.requestManager.companyTransfersV2(param);
        return !response.getStatusCode().is2xxSuccessful() ? WxResult.error("微信支付Api异常, response:" + response.getBody()) : this.handleXMLResponse(response);
    }

    private WxResult<Map<String, Object>> handleXMLResponse(ResponseEntity<String> response) {
        String body = Optional.ofNullable(response.getBody()).orElse("");
        Map<String, Object> respMap = WxXMLUtils.transferXmlToMap(body);
        return respMap != null && StrUtil.equals((String)respMap.get("return_code"), "SUCCESS") ? WxResult.success(respMap) : WxResult.error("");
    }

    private Map<String, String> jsapiStartPay(String prepayId) {
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
