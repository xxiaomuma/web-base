package pers.xiaomuma.base.thirdparty.wx.pay.param;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import pers.xiaomuma.base.thirdparty.wx.utils.WxXMLUtils;

import java.util.Objects;
import java.util.SortedMap;

public class CompanyTransfersV2Param {

    /**
     * 商户账号appid(必填)
     */
    private String mchAppId;

    /**
     * 商户号(必填)
     */
    private String mchId;

    /**
     * 设备号(选填)
     */
    private String deviceInfo;

    /**
     * 随机字符串(必填)
     */
    private String nonceStr;
    /**
     * 签名(必填)
     */
    private String sign;

    /**
     * 商户订单号(必填)
     */
    private String partnerTradeNo;

    /**
     * 用户openid(必填)
     */
    private String openId;

    /**
     * 校验用户姓名选项(选填)
     * NO_CHECK：不校验真实姓名
     * FORCE_CHECK：强校验真实姓名
     */
    private String checkName;

    /**
     * 收款用户姓名(选填)
     */
    private String reUsername;

    /**
     * 金额(必填)
     */
    private Integer amount;

    /**
     * 企业付款备注(必填)
     */
    private String desc;

    /**
     * Ip地址(选填)
     */
    private String spbillCreateIp;


    public String parse2RequestBody() {
        checkParam();
        return WxXMLUtils.transferMapToXml(getSortedParamMap());
    }

    public SortedMap<String, Object> getSortedParamMap() {
        SortedMap<String, Object> sortedMap = Maps.newTreeMap();
        sortedMap.put("amount", this.amount);
        sortedMap.put("check_name", this.checkName);
        sortedMap.put("desc", this.desc);
        if (StrUtil.isNotBlank(this.deviceInfo)) {
            sortedMap.put("device_info", this.deviceInfo);
        }
        sortedMap.put("mch_appid", this.mchAppId);
        sortedMap.put("mchid", this.mchId);
        sortedMap.put("nonce_str", this.nonceStr);
        sortedMap.put("openid", this.openId);
        sortedMap.put("partner_trade_no", this.partnerTradeNo);
        if (StrUtil.isNotBlank(this.reUsername)) {
            sortedMap.put("re_user_name", this.reUsername);
        }
        sortedMap.put("sign", this.sign);
        if (StrUtil.isNotBlank(this.spbillCreateIp)) {
            sortedMap.put("spbill_create_ip", this.spbillCreateIp);
        }
        return sortedMap;
    }

    private void checkParam() {
        if (StrUtil.isBlank(this.mchAppId)) {
            throw new IllegalArgumentException("mchAppId must not be null");
        }
        if (StrUtil.isBlank(this.mchId)) {
            throw new IllegalArgumentException("mchId must not be null");
        }
        if (StrUtil.isBlank(this.nonceStr)) {
            throw new IllegalArgumentException("nonceStr must not be null");
        }
        if (StrUtil.isBlank(this.partnerTradeNo)) {
            throw new IllegalArgumentException("partnerTradeNo and outTradeNo must not both be null");
        }
        if (StrUtil.isBlank(this.openId)) {
            throw new IllegalArgumentException("openId must not be null");
        }
        if (StrUtil.isBlank(this.checkName)) {
            throw new IllegalArgumentException("checkName must not be null");
        }
        if (Objects.isNull(this.amount)) {
            throw new IllegalArgumentException("amount must not be null");
        }
        if (StrUtil.isBlank(this.desc)) {
            throw new IllegalArgumentException("desc must not be null");
        }
    }

    public String getMchAppId() {
        return mchAppId;
    }

    public void setMchAppId(String mchAppId) {
        this.mchAppId = mchAppId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getReUsername() {
        return reUsername;
    }

    public void setReUsername(String reUsername) {
        this.reUsername = reUsername;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public static void main(String[] args) {
        CompanyTransfersV2Param param = new CompanyTransfersV2Param();
        param.setMchAppId("wx8888888888888888");
        param.setMchId("1900000109");
        param.setNonceStr("5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
        param.setSign("C380BEC2BFD727A4B6845133519F3AD6");
        param.setPartnerTradeNo("10000098201411111234567890");
        param.setOpenId("oxTWIuGaIt6gTKsQRLau2M0yL16E");
        param.setCheckName("FORCE_CHECK");
        param.setAmount(100);
        param.setDesc("测试描述");
        System.out.println(param.parse2RequestBody());
    }

}
