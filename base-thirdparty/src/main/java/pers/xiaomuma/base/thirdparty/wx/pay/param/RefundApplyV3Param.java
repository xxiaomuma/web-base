package pers.xiaomuma.base.thirdparty.wx.pay.param;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import pers.xiaomuma.base.common.utils.JsonUtils;
import java.util.Map;
import java.util.Objects;



public class RefundApplyV3Param {

    /**
     * 二级商户号(必填）
     */
    private String subMchId;

    /**
     * 电商平台APPID(必填)
     */
    private String spAppId;

    /**
     * 二级商户APPID(选填)
     */
    private String subAppId;

    /**
     * 微信订单号(transactionId &outTradeNo 二选一)
     */
    private String transactionId;

    /**
     * 商户订单号(transactionId &outTradeNo 二选一)
     */
    private String outTradeNo;

    /**
     * 商户退款单号(必填)
     */
    private String outRefundNo;

    /**
     * 退款原因(选填)
     */
    private String reason;

    /**
     * 退款结果回调url(选填)
     */
    private String notifyUrl;

    /**
     * 订单金额(必填)
     */
    private Amount amount;

    public void setRefundAmount(int refundAmount) {
        if (this.amount == null) {
            this.amount = new Amount();
        }
        this.amount.setRefund(refundAmount);
    }

    public void setTotalAmount(int totalAmount) {
        if (this.amount == null) {
            this.amount = new Amount();
        }
        this.amount.setTotal(totalAmount);
    }

    @Data
    private static class Amount {

        /**
         * 退款金额(必填)
         */
        private Integer refund;

        /**
         * 原订单金额(必填)
         */
        private Integer total;

        /**
         * 退款币种(选填)
         */
        private String currency;

    }

    public String parse2RequestBody() {
        checkParam();
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("sub_mchid", this.subMchId);
        requestMap.put("sp_appid", this.spAppId);
        requestMap.put("out_refund_no", this.outRefundNo);
        Map<String, Object> innerAmountMap = Maps.newHashMap();
        innerAmountMap.put("refund", this.amount.refund);
        innerAmountMap.put("total", this.amount.total);
        if (this.amount.currency != null) {
            innerAmountMap.put("currency", this.amount.currency);
        }
        requestMap.put("amount", innerAmountMap);
        if (Objects.nonNull(this.reason)) {
            requestMap.put("reason", this.reason);
        }
        if (Objects.nonNull(this.notifyUrl)) {
            requestMap.put("notify_url", this.notifyUrl);
        }
        if (Objects.nonNull(this.subAppId)) {
            requestMap.put("sub_appid", this.subAppId);
        }
        if (Objects.nonNull(this.transactionId)) {
            requestMap.put("transaction_id", this.transactionId);
        }
        if (Objects.nonNull(this.outTradeNo)) {
            requestMap.put("out_trade_no", this.outTradeNo);
        }
        return JsonUtils.object2Json(requestMap);
    }

    private void checkParam() {
        if (StrUtil.isBlank(this.subMchId)) {
            throw new IllegalArgumentException("subMchId must not be null");
        }
        if (StrUtil.isBlank(this.spAppId)) {
            throw new IllegalArgumentException("spAppId must not be null");
        }
        if (StrUtil.isBlank(this.outRefundNo)) {
            throw new IllegalArgumentException("outTradeNo must not be null");
        }
        if (StrUtil.isBlank(this.transactionId) && StrUtil.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("transactionId and outTradeNo must not both be null");
        }
        if (Objects.isNull(this.amount.refund)) {
            throw new IllegalArgumentException("amount.refund must not be null");
        }
        if (Objects.isNull(this.amount.total)) {
            throw new IllegalArgumentException("amount.total must not be null");
        }
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getSpAppId() {
        return spAppId;
    }

    public void setSpAppId(String spAppId) {
        this.spAppId = spAppId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
