package pers.xiaomuma.framework.thirdparty.pay.ali.param;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.math.BigDecimal;
import java.util.Map;

public class AliTransactionRefundParam {

    /**
     * 商户订单号
     * 二选一
     * 支付宝交易号
     */
    private String outTradeNo;

    /**
     * 商户订单号
     * 二选一
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 退款金额(必填)
     */
    private BigDecimal refundAmount;

    /**
     * 原因(选填)
     */
    private String refundReason;

    /**
     * 退款请求号(可选)
     */
    private String outRequestNo;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
    }

    public String parse2BizContentRequestBody() {
        if (StrUtil.isEmpty(this.outTradeNo) && StrUtil.isEmpty(this.tradeNo)) {
            throw new IllegalArgumentException("bizContent.outTradeNo or bizContent.tradeNo must not be empty");
        }
        if (this.refundAmount == null) {
            throw new IllegalArgumentException("bizContent.refundAmount must not be null");
        }
        Map<String, Object> requestBizContentMap = Maps.newHashMap();
        requestBizContentMap.put("out_trade_no", this.outTradeNo);
        requestBizContentMap.put("trade_no", this.tradeNo);
        requestBizContentMap.put("refund_amount", this.refundAmount);
        requestBizContentMap.put("refund_reason", this.refundReason);
        requestBizContentMap.put("out_request_no", this.outRequestNo);
        return JsonUtils.object2Json(requestBizContentMap);
    }
}
