package pers.xiaomuma.framework.thirdparty.wx.pay.request;

import org.springframework.http.ResponseEntity;
import pers.xiaomuma.framework.thirdparty.wx.pay.param.WxTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.wx.pay.param.WxTransactionRefundParam;


public interface WxTransactionRequest {

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_3_1.shtml H5支付
     */
    ResponseEntity<String> transactionPayH5(WxTransactionPayParam param);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_1.shtml APP支付
     */
    ResponseEntity<String> transactionPayApp(WxTransactionPayParam param);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml JSAPI支付
     */
    ResponseEntity<String> transactionPayJsapi(WxTransactionPayParam param);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml Native支付
     */
    ResponseEntity<String> transactionPayNative(WxTransactionPayParam param);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_9.shtml 国内申请退款
     */
    ResponseEntity<String> transactionRefundDomestic(WxTransactionRefundParam param);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_2.shtml 微信订单号查询
     */
    ResponseEntity<String> transactionQueryTransactionId(String transactionId);

    /**
     * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_2.shtml 商户订单号查询
     */
    ResponseEntity<String> transactionQueryOutTradeNo(String outTradeNo);
}
