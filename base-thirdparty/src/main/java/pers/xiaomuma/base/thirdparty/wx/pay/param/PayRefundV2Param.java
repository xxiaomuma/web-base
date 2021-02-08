package pers.xiaomuma.base.thirdparty.wx.pay.param;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Maps;
import pers.xiaomuma.base.thirdparty.wx.utils.WxXMLUtils;
import java.util.Objects;
import java.util.SortedMap;

public class PayRefundV2Param {

    /**
     * 公众账号ID(必传)
     */
    private String appId;

    /**
     * 商户号(必传)
     */
    private String mchId;

    /**
     * 随机字符串(必传)
     */
    private String nonceStr;

    /**
     * 签名(必传)
     */
    private String sign;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 微信订单号(二选一)
     */
    private String transactionId;

    /**
     * 商户订单号(二选一)
     */
    private String outTradeNo;

    /**
     * 商户退款单号(必传)
     */
    private String outRefundNo;

    /**
     * 订单金额(必传)
     */
    private Integer totalFee;

    /**
     * 退款金额(必传)
     */
    private Integer refundFee;

    /**
     * 退款货币种类
     */
    private String refundFeeType;

    /**
     * 退款原因
     */
    private String refundDesc;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    /**
     * 退款结果通知url
     */
    private String notifyUrl;

    public String parse2RequestBody() {
        checkParam();
        return WxXMLUtils.transferMapToXml(getSortedParamMap());
    }

    public SortedMap<String, Object> getSortedParamMap() {
        SortedMap<String, Object> requestMap = Maps.newTreeMap();
        requestMap.put("appid", this.appId);
        requestMap.put("mch_id", this.mchId);
        requestMap.put("nonce_str", this.nonceStr);
        if (StringUtils.isNotEmpty(this.notifyUrl)) {
            requestMap.put("notify_url", this.notifyUrl);
        }
        if (StringUtils.isNotEmpty(this.outTradeNo)) {
            requestMap.put("out_trade_no", this.outTradeNo);
        }
        requestMap.put("out_refund_no", this.outRefundNo);
        requestMap.put("refund_fee", this.refundFee);
        if (StringUtils.isNotEmpty(this.refundFeeType)) {
            requestMap.put("refund_fee_type", this.refundFeeType);
        }
        if (StringUtils.isNotEmpty(this.refundDesc)) {
            requestMap.put("refund_desc", this.refundDesc);
        }
        if (StringUtils.isNotEmpty(this.refundAccount)) {
            requestMap.put("refund_account", this.refundAccount);
        }
        requestMap.put("sign", this.sign);
        if (StringUtils.isNotEmpty(this.signType)) {
            requestMap.put("sign_type", this.signType);
        }
        if (StringUtils.isNotEmpty(this.transactionId)) {
            requestMap.put("transaction_id", this.transactionId);
        }
        requestMap.put("total_fee", this.totalFee);
        return requestMap;
    }

    private void checkParam() {
        if (StrUtil.isBlank(this.appId)) {
            throw new IllegalArgumentException("appId must not be null");
        }
        if (StrUtil.isBlank(this.mchId)) {
            throw new IllegalArgumentException("mchId must not be null");
        }
        if (StrUtil.isBlank(this.nonceStr)) {
            throw new IllegalArgumentException("nonceStr must not be null");
        }
        if (StrUtil.isBlank(this.sign)) {
            throw new IllegalArgumentException("sign must not be null");
        }
        if (StrUtil.isBlank(this.transactionId) && StrUtil.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("transactionId and outTradeNo must not both be null");
        }
        if (StrUtil.isBlank(this.outRefundNo)) {
            throw new IllegalArgumentException("outRefundNo must not be null");
        }
        if (Objects.isNull(this.totalFee)) {
            throw new IllegalArgumentException("totalFee must not be null");
        }
        if (Objects.isNull(this.refundFee)) {
            throw new IllegalArgumentException("refundFee must not be null");
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
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

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundFeeType() {
        return refundFeeType;
    }

    public void setRefundFeeType(String refundFeeType) {
        this.refundFeeType = refundFeeType;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
