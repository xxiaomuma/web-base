package pers.xiaomuma.framework.thirdparty.pay.ali.param;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AliTransactionPayParam {

    /**
     * 商户订单号(必填)
     */
    private String outTradeNo;

    /**
     * 订单总金额(必填)
     */
    private BigDecimal totalAmount;

    /**
     * 订单标题(必填)
     */
    private String subject;

    /**
     * 销售产品码(选填)
     */
    private String productCode;

    /**
     * 卖家支付宝用户ID(选填)
     */
    private String sellerId;

    /**
     * 订单附加信息(选填)
     */
    private String body;

    /**
     * 订单包含的商品列表信息，为 JSON 格式(选填)
     */
    private List<GoodsDetail> goodsDetail;

    /**
     * 业务扩展参数(选填)
     */
    private ExtendParams extendParams;

    /**
     * 打折金额(选填)
     */
    private BigDecimal discountableAmount;

    /**
     * 商户门店编号(选填)
     */
    private String storeId;

    /**
     * 商户操作员编号(选填)
     */
    private String operatorId;

    /**
     * 商户机具终端编号(选填)
     */
    private String terminalId;

    /**
     * 商户原始订单号(选填)
     */
    private String merchantOrderNo;

    public static class GoodsDetail {

        /**
         * 商品的编号(必填)
         */
        private String goodsId;

        /**
         * 商品名称(必填)
         */
        private String goodsName;

        /**
         * 商品数量(必填)
         */
        private Integer quantity;

        /**
         * 商品单价，单位为元(必填)
         */
        private BigDecimal price;

        /**
         * 商品类目(选填)
         */
        private String goodsCategory;

        /**
         * 从商品类目根节点到叶子节点的类目id组成，类目id值使用|分割(选填)
         */
        private String categoriesTree;

        /**
         * 商品的展示地址(选填)
         */
        private String showUrl;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getGoodsCategory() {
            return goodsCategory;
        }

        public void setGoodsCategory(String goodsCategory) {
            this.goodsCategory = goodsCategory;
        }

        public String getCategoriesTree() {
            return categoriesTree;
        }

        public void setCategoriesTree(String categoriesTree) {
            this.categoriesTree = categoriesTree;
        }

        public String getShowUrl() {
            return showUrl;
        }

        public void setShowUrl(String showUrl) {
            this.showUrl = showUrl;
        }
    }

    public static class ExtendParams {

        /**
         * 系统商编号(选填)
         */
        private String sysServiceProviderId;

        /**
         * 卡类型(选填)
         */
        private String cardType;

        /**
         * 特殊场景下,允许商户指定交易展示的卖家名称(选填)
         */
        private String specifiedSellerName;

        public String getSysServiceProviderId() {
            return sysServiceProviderId;
        }

        public void setSysServiceProviderId(String sysServiceProviderId) {
            this.sysServiceProviderId = sysServiceProviderId;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getSpecifiedSellerName() {
            return specifiedSellerName;
        }

        public void setSpecifiedSellerName(String specifiedSellerName) {
            this.specifiedSellerName = specifiedSellerName;
        }
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<GoodsDetail> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List<GoodsDetail> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public ExtendParams getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(ExtendParams extendParams) {
        this.extendParams = extendParams;
    }

    public BigDecimal getDiscountableAmount() {
        return discountableAmount;
    }

    public void setDiscountableAmount(BigDecimal discountableAmount) {
        this.discountableAmount = discountableAmount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    private Map<String, Object> buildExtendParamsMap() {
        if (this.extendParams == null) {
            return null;
        }
        Map<String, Object> innerExtendParams = Maps.newHashMap();
        innerExtendParams.put("sys_service_provider_id", this.extendParams.getSysServiceProviderId());
        innerExtendParams.put("card_type", this.extendParams.getCardType());
        innerExtendParams.put("specified_seller_name", this.extendParams.getSpecifiedSellerName());
        return innerExtendParams;
    }

    private List<Map<String, Object>> buildGoodsDetailMap() {
        if (this.goodsDetail == null) {
            return null;
        }
        List<Map<String, Object>> innerGoodsDetailList = Lists.newArrayList();
        for (AliTransactionPayParam.GoodsDetail goodsDetail : this.goodsDetail) {
            if (StrUtil.isEmpty(goodsDetail.getGoodsId())) {
                throw new IllegalArgumentException("bizContent.goodsDetail.goodsId must not be empty");
            }
            if (StrUtil.isEmpty(goodsDetail.getGoodsName())) {
                throw new IllegalArgumentException("bizContent.goodsDetail.goodsName must not be empty");
            }
            if (goodsDetail.getQuantity() == null) {
                throw new IllegalArgumentException("bizContent.goodsDetail.quantity must not be null");
            }
            if (goodsDetail.getPrice() == null) {
                throw new IllegalArgumentException("bizContent.goodsDetail.price must not be null");
            }
            Map<String, Object> innerGoodsDetail = Maps.newHashMap();
            innerGoodsDetail.put("goods_id", goodsDetail.getGoodsId());
            innerGoodsDetail.put("goods_name", goodsDetail.getGoodsName());
            innerGoodsDetail.put("quantity", goodsDetail.getQuantity());
            innerGoodsDetail.put("price", goodsDetail.getPrice());
            innerGoodsDetail.put("goods_category", goodsDetail.getGoodsCategory());
            innerGoodsDetail.put("categories_tree", goodsDetail.getCategoriesTree());
            innerGoodsDetail.put("show_url", goodsDetail.getShowUrl());
            innerGoodsDetailList.add(innerGoodsDetail);
        }
        return innerGoodsDetailList;
    }

    public String parse2BizContentRequestBody() {
        if (StrUtil.isEmpty(this.outTradeNo)) {
            throw new IllegalArgumentException("bizContent.outTradeNo must not be empty");
        }
        if (this.totalAmount == null) {
            throw new IllegalArgumentException("bizContent.totalAmount must not be null");
        }
        if (StrUtil.isEmpty(this.subject)) {
            throw new IllegalArgumentException("bizContent.subject must not be empty");
        }
        Map<String, Object> requestBizContentMap = Maps.newHashMap();
        requestBizContentMap.put("out_trade_no", this.outTradeNo);
        requestBizContentMap.put("total_amount", this.totalAmount);
        requestBizContentMap.put("subject", this.subject);
        requestBizContentMap.put("product_code", this.productCode);
        requestBizContentMap.put("seller_id", this.sellerId);
        requestBizContentMap.put("body", this.body);
        List<Map<String, Object>> goodsDetail = this.buildGoodsDetailMap();
        requestBizContentMap.put("goods_detail", goodsDetail);
        Map<String, Object> extendParams = this.buildExtendParamsMap();
        requestBizContentMap.put("extend_params", extendParams);
        requestBizContentMap.put("discountable_amount", this.discountableAmount);
        requestBizContentMap.put("store_id", this.storeId);
        requestBizContentMap.put("operator_id", this.operatorId);
        requestBizContentMap.put("terminal_id", this.terminalId);
        requestBizContentMap.put("merchant_order_no", this.merchantOrderNo);
        return JsonUtils.object2Json(requestBizContentMap);
    }

}
