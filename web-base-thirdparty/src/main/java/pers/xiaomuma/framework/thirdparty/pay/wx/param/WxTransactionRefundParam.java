package pers.xiaomuma.framework.thirdparty.pay.wx.param;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WxTransactionRefundParam {

    /**
     * 微信支付订单号
     * 二选一
     * 商户订单号
     */
    private String transactionId;

    /**
     * 微信支付订单号
     * 二选一
     * 商户订单号
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
     * 退款资金来源(选填)
     * AVAILABLE：可用余额账户
     */
    private String fundsAccount;

    /**
     * 金额信息(必填)
     */
    private RefundAmount amount;

    /**
     * 退款商品(选填)
     */
    private List<GoodsDetail> goodsDetail;

    private final static BigDecimal ONE_HUNDRED_DECIMAL = new BigDecimal("100");

    public static class RefundAmount {
        /**
         * 退款金额,单位为分。(必填)
         */
        private Integer refund;

        /**
         * 原订单金额(必填)
         */
        private Integer total;

        /**
         * CNY：人民币，境内商户号仅支持人民币。(选填)
         */
        private String currency;

        /**
         * 退款出资账户及金额 (选填)
         */
        private List<RefundFrom> from;

        public static class RefundFrom {

            /**
             * 出资账户类型
             * AVAILABLE : 可用余额
             */
            private String account;

            /**
             * 出资金额(必填)
             */
            private Integer amount;

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = NumberUtil.mul(amount, ONE_HUNDRED_DECIMAL).intValue();;
            }
        }

        public Integer getRefund() {
            return refund;
        }

        public void setRefund(Integer refund) {
            this.refund = NumberUtil.mul(refund, ONE_HUNDRED_DECIMAL).intValue();
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = NumberUtil.mul(total, ONE_HUNDRED_DECIMAL).intValue();
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public List<RefundFrom> getFrom() {
            return from;
        }

        public void setFrom(List<RefundFrom> from) {
            this.from = from;
        }
    }

    public static class GoodsDetail {

        /**
         * 商户侧商品编码(必填)
         */
        private String merchantGoodsId;

        /**
         * 微信支付商品编码(选填)
         */
        private String wechatpayGoodsId;

        /**
         * 商品名称(选填)
         */
        private String goodsName;

        /**
         * 商品单价(必填)
         */
        private Integer unitPrice;

        /**
         * 商品退款金额,单位分
         */
        private Integer refundAmount;

        /**
         * 商品退货数量
         */
        private Integer refundQuantity;

        public String getMerchantGoodsId() {
            return merchantGoodsId;
        }

        public void setMerchantGoodsId(String merchantGoodsId) {
            this.merchantGoodsId = merchantGoodsId;
        }

        public String getWechatpayGoodsId() {
            return wechatpayGoodsId;
        }

        public void setWechatpayGoodsId(String wechatpayGoodsId) {
            this.wechatpayGoodsId = wechatpayGoodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public Integer getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Integer unitPrice) {
            this.unitPrice =  NumberUtil.mul(unitPrice, ONE_HUNDRED_DECIMAL).intValue();
        }

        public Integer getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(Integer refundAmount) {
            this.refundAmount =  NumberUtil.mul(refundAmount, ONE_HUNDRED_DECIMAL).intValue();
        }

        public Integer getRefundQuantity() {
            return refundQuantity;
        }

        public void setRefundQuantity(Integer refundQuantity) {
            this.refundQuantity = refundQuantity;
        }
    }

    private Map<String, Object> buildAmountMap() {
        if (this.amount == null) {
            throw new IllegalArgumentException("amount must not be null");
        }
        if (this.amount.getRefund() == null) {
            throw new IllegalArgumentException("amount.refund must not be null");
        }
        if (this.amount.getTotal() == null) {
            throw new IllegalArgumentException("amount.total must not be null");
        }
        if (StrUtil.isEmpty(this.amount.getCurrency())) {
            this.amount.setCurrency("CNY");
        }
        Map<String, Object> innerAmount = Maps.newHashMap();
        innerAmount.put("refund", this.amount.getRefund());
        innerAmount.put("total", this.amount.getTotal());
        innerAmount.put("currency", this.amount.getCurrency());
        if (this.amount.getFrom() == null) {
            return innerAmount;
        }
        List<Map<String, Object>> innerFromList = Lists.newArrayList();
        for (WxTransactionRefundParam.RefundAmount.RefundFrom refundFrom : this.amount.getFrom()) {
            if (StrUtil.isEmpty(refundFrom.getAccount())) {
                throw new IllegalArgumentException("amount.from.account must not be empty");
            }
            if (refundFrom.getAmount() == null) {
                throw new IllegalArgumentException("amount.from.amount must not be null");
            }
            Map<String, Object> innerFrom = Maps.newHashMap();
            innerFrom.put("account", refundFrom.getAccount());
            innerFrom.put("amount", refundFrom.getAmount());
            innerFromList.add(innerFrom);
        }
        innerAmount.put("from", innerFromList);
        return innerAmount;
    }

    private List<Map<String, Object>> buildGoodsDetailMap() {
        if (this.goodsDetail == null) {
            return null;
        }
        List<Map<String, Object>> innerGoodsDetailList = Lists.newArrayList();
        for (WxTransactionRefundParam.GoodsDetail goodsDetail : this.goodsDetail) {
            if (StrUtil.isEmpty(goodsDetail.getMerchantGoodsId())) {
                throw new IllegalArgumentException("goodsDetail.merchantGoodsId must not be empty");
            }
            if (goodsDetail.getUnitPrice() == null) {
                throw new IllegalArgumentException("dgoodsDetail.unitPrice must not be null");
            }
            if (goodsDetail.getRefundQuantity() == null) {
                throw new IllegalArgumentException("goodsDetail.refundQuantity must not be null");
            }
            if (goodsDetail.getRefundAmount() == null) {
                throw new IllegalArgumentException("goodsDetail.refundAmount must not be null");
            }
            Map<String, Object> innerGoodsDetail = Maps.newHashMap();
            innerGoodsDetail.put("merchant_goods_id", goodsDetail.getMerchantGoodsId());
            innerGoodsDetail.put("wechatpay_goods_id", goodsDetail.getWechatpayGoodsId());
            innerGoodsDetail.put("goods_name", goodsDetail.getGoodsName());
            innerGoodsDetail.put("unit_price", goodsDetail.getUnitPrice());
            innerGoodsDetail.put("refund_amount", goodsDetail.getRefundAmount());
            innerGoodsDetail.put("refund_quantity", goodsDetail.getRefundQuantity());
            innerGoodsDetailList.add(innerGoodsDetail);
        }
        return innerGoodsDetailList;
    }

    public String parse2RequestBody() {
        if (StrUtil.isBlank(this.transactionId) && StrUtil.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("transactionId or outTradeNo must not be empty");
        }
        if (StrUtil.isBlank(this.outRefundNo)) {
            throw new IllegalArgumentException("outRefundNo must not be empty");
        }
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("transaction_id", this.transactionId);
        requestMap.put("out_trade_no", this.outTradeNo);
        requestMap.put("out_refund_no", this.outRefundNo);
        requestMap.put("reason", this.reason);
        requestMap.put("notify_url", this.notifyUrl);
        requestMap.put("funds_account", this.fundsAccount);

        Map<String, Object> amount = this.buildAmountMap();
        requestMap.put("amount", amount);

        List<Map<String, Object>> goodsDetail = this.buildGoodsDetailMap();
        requestMap.put("goods_detail", amount);
        return JsonUtils.object2Json(requestMap);
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

    public String getFundsAccount() {
        return fundsAccount;
    }

    public void setFundsAccount(String fundsAccount) {
        this.fundsAccount = fundsAccount;
    }

    public RefundAmount getAmount() {
        return amount;
    }

    public void setAmount(RefundAmount amount) {
        this.amount = amount;
    }

    public List<GoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List<GoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}
