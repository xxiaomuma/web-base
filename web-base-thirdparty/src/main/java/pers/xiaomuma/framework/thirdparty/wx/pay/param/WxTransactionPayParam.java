package pers.xiaomuma.framework.thirdparty.wx.pay.param;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class WxTransactionPayParam {

    /**
     * 应用ID(必填)
     */
    private String appId;

    /**
     * 直连商户号(必填)
     */
    private String mchId;

    /**
     * 商品描述 (必填)
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
     * 订单金额信息(必填)
     */
    private PayAmount amount;

    /**
     * 支付者 (JSAPI必填)
     */
    private Payer payer;

    /**
     * 优惠功能(选填)
     */
    private Detail detail;

    /**
     * 场景信息(选填)
     */
    private SceneInfo sceneInfo;

    /**
     * 结算信息 (选填)
     */
    private SettleInfo settleInfo;

    private final static BigDecimal ONE_HUNDRED_DECIMAL = new BigDecimal("100");

    private static class PayAmount {

        /**
         * 订单总金额，单位为分。(必填)
         */
        private Integer total;

        /**
         * CNY：人民币，境内商户号仅支持人民币。(选填)
         */
        private String currency;

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
    }

    private static class Payer {

        /**
         * 用户标识,openid(必填)
         */
        private String openid;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }

    public static class SettleInfo {

        /**
         * 是否指定分账
         */
        private Boolean profitSharing;

        public Boolean getProfitSharing() {
            return profitSharing;
        }

        public void setProfitSharing(Boolean profitSharing) {
            this.profitSharing = profitSharing;
        }
    }

    public static class SceneInfo {

        /**
         * 用户终端IP(必填)
         */
        private String payerClientIp;

        /**
         * 商户端设备号(选填)
         */
        private String deviceId;

        /**
         * 商户门店信息 (选填)
         */
        private StoreInfo storeInfo;

        public static class StoreInfo {

            /**
             * 门店编号(必填)
             */
            private String id;

            /**
             * 门店名称(选填)
             */
            private String name;

            /**
             * 地区编码(选填)
             */
            private String areaCode;

            /**
             * 详细地址(选填)
             */
            private String address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public String getPayerClientIp() {
            return payerClientIp;
        }

        public void setPayerClientIp(String payerClientIp) {
            this.payerClientIp = payerClientIp;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public StoreInfo getStoreInfo() {
            return storeInfo;
        }

        public void setStoreInfo(StoreInfo storeInfo) {
            this.storeInfo = storeInfo;
        }
    }

    public static class Detail {

        /**
         * 订单原价(选填)
         */
        private Integer costPrice;

        /**
         * 商品小票ID(选填)
         */
        private String invoiceId;

        /**
         * 单品列表(选填)
         */
        private List<GoodsDetail> goodsDetail;

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
             * 商品数量(必填)
             */
            private Integer quantity;

            /**
             * 商品单价(必填)
             */
            private Integer unitPrice;

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

            public Integer getQuantity() {
                return quantity;
            }

            public void setQuantity(Integer quantity) {
                this.quantity = quantity;
            }

            public Integer getUnitPrice() {
                return unitPrice;
            }

            public void setUnitPrice(Integer unitPrice) {
                this.unitPrice = NumberUtil.mul(unitPrice, ONE_HUNDRED_DECIMAL).intValue();
            }
        }

        public Integer getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Integer costPrice) {
            this.costPrice = NumberUtil.mul(costPrice, ONE_HUNDRED_DECIMAL).intValue();
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public List<GoodsDetail> getGoodsDetail() {
            return goodsDetail;
        }

        public void setGoodsDetail(List<GoodsDetail> goodsDetail) {
            this.goodsDetail = goodsDetail;
        }
    }

    private Map<String, Object> buildMainMap() {
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
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("appid", this.appId);
        requestMap.put("mchid", this.mchId);
        requestMap.put("description", this.description);
        requestMap.put("out_trade_no", this.outTradeNo);
        requestMap.put("time_expire", this.timeExpire);
        requestMap.put("attach", this.attach);
        requestMap.put("notify_url", this.notifyUrl);
        requestMap.put("goods_tag", this.goodsTag);
        return requestMap;
    }

    private Map<String, Object> buildAmountMap() {
        if (amount == null) {
            throw new IllegalArgumentException("amount must not be empty");
        }
        if (amount.getTotal() == null) {
            throw new IllegalArgumentException("amount.total must not be null");
        }
        Map<String, Object> innerAmount = Maps.newHashMap();
        innerAmount.put("total", this.amount.getTotal());
        innerAmount.put("currency", this.amount.getCurrency());
        return innerAmount;
    }

    private Map<String, Object> buildPayerMap() {
        if (payer == null) {
            throw new IllegalArgumentException("payer must not be null");
        }
        if (StrUtil.isEmpty(payer.getOpenid())) {
            throw new IllegalArgumentException("payer.openId must not be empty");
        }
        Map<String, Object> innerPayer = Maps.newHashMap();
        innerPayer.put("openid", this.payer.getOpenid());
        return innerPayer;
    }

    private Map<String, Object> buildSettleInfoMap() {
        if (settleInfo == null) {
            return null;
        }
        Map<String, Object> innerSettleInfo = Maps.newHashMap();
        innerSettleInfo.put("profit_sharing", settleInfo.getProfitSharing());
        return innerSettleInfo;
    }

    private Map<String, Object> buildSceneInfoMap() {
        if (sceneInfo == null) {
            return null;
        }
        if (StrUtil.isEmpty(sceneInfo.getPayerClientIp())) {
            throw new IllegalArgumentException("sceneInfo.payerClientIp must not be empty");
        }
        Map<String, Object> innerSceneInfo = Maps.newHashMap();
        innerSceneInfo.put("payer_client_ip", this.sceneInfo.getPayerClientIp());
        innerSceneInfo.put("device_id", this.sceneInfo.getDeviceId());
        if (sceneInfo.getStoreInfo() == null) {
            return innerSceneInfo;
        }
        if (StrUtil.isEmpty(sceneInfo.getStoreInfo().getId())) {
            throw new IllegalArgumentException("sceneInfo.storeInfo.id must not be empty");
        }
        Map<String, Object> innerStoreInfo = Maps.newHashMap();
        innerStoreInfo.put("id", sceneInfo.getStoreInfo().getId());
        innerStoreInfo.put("name", sceneInfo.getStoreInfo().getName());
        innerStoreInfo.put("area_code", sceneInfo.getStoreInfo().getAreaCode());
        innerStoreInfo.put("address", sceneInfo.getStoreInfo().getAddress());

        innerSceneInfo.put("store_info", innerStoreInfo);
        return innerSceneInfo;
    }

    private Map<String, Object> buildDetailMap() {
        if (detail == null) {
            return null;
        }
        Map<String, Object> innerDetail = Maps.newHashMap();
        innerDetail.put("cost_price", this.detail.getCostPrice());
        innerDetail.put("invoice_id", this.detail.getInvoiceId());
        if (detail.getGoodsDetail() == null) {
            return innerDetail;
        }
        List<Map<String, Object>> innerGoodsDetailList = Lists.newArrayList();
        for (WxTransactionPayParam.Detail.GoodsDetail goodsDetail : detail.getGoodsDetail()) {
            if (StrUtil.isEmpty(goodsDetail.getMerchantGoodsId())) {
                throw new IllegalArgumentException("detail.goodsDetail.merchantGoodsId must not be empty");
            }
            if (goodsDetail.getQuantity() == null) {
                throw new IllegalArgumentException("detail.goodsDetail.quantity must not be null");
            }
            if (goodsDetail.getUnitPrice() == null) {
                throw new IllegalArgumentException("detail.goodsDetail.unitPrice must not be null");
            }
            Map<String, Object> innerGoodsDetail = Maps.newHashMap();
            innerGoodsDetail.put("merchant_goods_id", goodsDetail.getMerchantGoodsId());
            innerGoodsDetail.put("wechatpay_goods_id", goodsDetail.getWechatpayGoodsId());
            innerGoodsDetail.put("goods_name", goodsDetail.getGoodsName());
            innerGoodsDetail.put("quantity", goodsDetail.getQuantity());
            innerGoodsDetail.put("unit_price", goodsDetail.getUnitPrice());
            innerGoodsDetailList.add(innerGoodsDetail);
        }
        innerDetail.put("goods_detail", innerGoodsDetailList);
        return innerDetail;
    }

    public String parse2JsapiRequestBody() {
        Map<String, Object> requestMap = this.buildMainMap();

        Map<String, Object> amount = this.buildAmountMap();
        requestMap.put("amount", amount);

        Map<String, Object> payer = this.buildPayerMap();
        requestMap.put("payer", payer);

        Map<String, Object> sceneInfo = this.buildSceneInfoMap();
        requestMap.put("scene_info", settleInfo);

        Map<String, Object> settleInfo = this.buildSettleInfoMap();
        requestMap.put("settle_info", settleInfo);

        Map<String, Object> detail = this.buildDetailMap();
        requestMap.put("detail", detail);
        return JsonUtils.object2Json(requestMap);
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = this.buildMainMap();

        Map<String, Object> amount = this.buildAmountMap();
        requestMap.put("amount", amount);

        Map<String, Object> settleInfo = this.buildSettleInfoMap();
        requestMap.put("settle_info", settleInfo);

        Map<String, Object> sceneInfo = this.buildSceneInfoMap();
        requestMap.put("scene_info", settleInfo);

        Map<String, Object> detail = this.buildDetailMap();
        requestMap.put("detail", detail);
        return JsonUtils.object2Json(requestMap);
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

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public SceneInfo getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(SceneInfo sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public SettleInfo getSettleInfo() {
        return settleInfo;
    }

    public void setSettleInfo(SettleInfo settleInfo) {
        this.settleInfo = settleInfo;
    }

    public static void main(String[] args) {
        WxTransactionPayParam param = new WxTransactionPayParam();
        param.setAppId("appid");
        param.setMchId("mchid");
        param.setNotifyUrl("notifyUrl");
        param.setDescription("description");
        param.setOutTradeNo("outTradeNo");
        Payer payer = new Payer();
        payer.setOpenid("openid");
        param.setPayer(payer);
        PayAmount payAmount = new PayAmount();
        payAmount.setTotal(2);
        param.setAmount(payAmount);
        System.out.println(param.parse2RequestBody());
    }
}
