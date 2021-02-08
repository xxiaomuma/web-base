package pers.xiaomuma.base.thirdparty.wx.pay.param;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import pers.xiaomuma.base.common.utils.JsonUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


public class JsapiPayV3Param {
    /**
     * 公众号ID(必填)
     */
    private String appId;
    /**
     * 直连商户号(必填)
     */
    private String mchId;
    /**
     * 商品描述(必填)
     */
    private String description;
    /**
     * 商户订单号(必填)
     */
    private String outTradeNo;
    /**
     * 交易结束时间(选填)
     */
    private LocalDateTime timeExpire;
    /**
     * 附加数据(选填)
     */
    private String attach;
    /**
     * 通知地址(必填)
     */
    private String notifyUrl;
    /**
     * 订单优惠标记(选填)
     */
    private String goodsTag;
    /**
     * 订单金额(必填)
     */
    private PayAmount amount;
    /**
     * 支付者(必填)
     */
    private Payer payer;


    public void setPayerOpenId(String openId) {
        Payer payer = new Payer();
        payer.setOpenid(openId);
        this.setPayer(payer);
    }

    private final static BigDecimal ONE_HUNDRED_DECIMAL = new BigDecimal("100");

    public void setCNYPayAmount(BigDecimal cnyPayAmount) {
        int payAmountInt = NumberUtil.mul(cnyPayAmount, ONE_HUNDRED_DECIMAL).intValue();
        if (this.amount == null) {
            this.amount = new PayAmount();
        }
        this.amount.setTotal(payAmountInt);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public LocalDateTime getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(LocalDateTime timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public PayAmount getAmount() {
        return amount;
    }

    public void setAmount(PayAmount amount) {
        this.amount = amount;
    }

    public Payer getPayer() {
        return payer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    @Data
    public static class PayAmount {
        /**
         * 订单总金额，单位为分。(必填)
         * 示例值：100
         */
        private Integer total;
        /**
         * CNY：人民币，境内商户号仅支持人民币。（选填）
         * 示例值：CNY
         */
        private String currency;
    }

    @Data
    public static class Payer {
        /**
         * 用户标识,openId(必填)
         */
        private String openid;

    }

    public String parse2RequestBody() {
        checkParam();
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("appid", this.appId);
        requestMap.put("mchid", this.mchId);
        requestMap.put("description", this.description);
        requestMap.put("out_trade_no", this.outTradeNo);
        requestMap.put("time_expire", this.timeExpire);
        requestMap.put("attach", this.attach);
        requestMap.put("notify_url", this.notifyUrl);
        requestMap.put("goods_tag", this.goodsTag);
        Map<String, Object> innerAmount = Maps.newHashMap();
        innerAmount.put("total", this.amount.getTotal());
        innerAmount.put("currency", this.amount.getCurrency());
        requestMap.put("amount", innerAmount);
        Map<String, String> innerPayer = Maps.newHashMap();
        innerPayer.put("openid", this.payer.getOpenid());
        requestMap.put("payer", innerPayer);
        return JsonUtils.object2Json(requestMap);
    }

    private void checkParam() {
        if (StrUtil.isBlank(this.appId)) {
            throw new IllegalArgumentException("appId must not be empty");
        }
        if (StrUtil.isBlank(this.mchId)) {
            throw new IllegalArgumentException("mchId must not be empty");
        }
        if (StrUtil.isBlank(this.description)) {
            throw new IllegalArgumentException("description must not be empty");
        }
        if (StrUtil.isBlank(this.outTradeNo)) {
            throw new IllegalArgumentException("outTradeNo must not be empty");
        }
        if (StrUtil.isBlank(this.notifyUrl)) {
            throw new IllegalArgumentException("notifyUrl must not be empty");
        }
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("amount must not be empty");
        }
        if (Objects.isNull(amount.getTotal())) {
            throw new IllegalArgumentException("amount.total must not be null");
        }
        if (Objects.isNull(payer)) {
            throw new IllegalArgumentException("payer must not be null");
        }
        if (StrUtil.isEmpty(payer.getOpenid())) {
            throw new IllegalArgumentException("payer.openId must not be null");
        }
    }

    public static void main(String[] args) {
        JsapiPayV3Param wxJsapiPayV3Param = new JsapiPayV3Param();
        wxJsapiPayV3Param.setAppId("dasda");
        wxJsapiPayV3Param.setMchId("mchid");
        wxJsapiPayV3Param.setNotifyUrl("notify url");
        wxJsapiPayV3Param.setDescription("description");
        wxJsapiPayV3Param.setOutTradeNo("out trade no");
        Payer payer = new Payer();
        payer.setOpenid("openid");
        wxJsapiPayV3Param.setPayer(payer);
        PayAmount payAmount = new PayAmount();
        payAmount.setCurrency("dada");
        payAmount.setTotal(2);
        wxJsapiPayV3Param.setAmount(payAmount);
        System.out.println(wxJsapiPayV3Param.parse2RequestBody());
    }

}