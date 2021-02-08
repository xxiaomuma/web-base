package pers.xiaomuma.base.thirdparty.wx.pay.request;


import org.springframework.http.ResponseEntity;
import pers.xiaomuma.base.thirdparty.wx.pay.param.CompanyTransfersV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.JsapiPayV3Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.PayRefundV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.RefundApplyV3Param;


public interface WxPayRequest {

    ResponseEntity<String> jsapiPayV3(JsapiPayV3Param param);

    ResponseEntity<String> outTradeNoPayQueryV3(String outTradeNo);

    ResponseEntity<String> refundApplyV3(RefundApplyV3Param param);

    ResponseEntity<String> payRefundV2(PayRefundV2Param param);

    ResponseEntity<String> companyTransfersV2(CompanyTransfersV2Param param);
}
