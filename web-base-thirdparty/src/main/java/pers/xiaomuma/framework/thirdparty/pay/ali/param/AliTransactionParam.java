package pers.xiaomuma.framework.thirdparty.pay.ali.param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AliTransactionParam {

    /**
     * 应用ID(必填)
     */
    private String appId;

    /**
     * 接口名称(必填)
     */
    private String method;

    /**
     * 仅支持JSON(选填)
     */
    private String format;

    /**
     * 请求使用的编码格式:utf-8(必填)
     */
    private String charset;

    /**
     * 签名算法类型:支持RSA2和RSA (必填)
     */
    private String signType;

    /**
     * 发送请求的时间,格式"yyyy-MM-dd HH:mm:ss"(必填)
     */
    private String timestamp;

    /**
     * 调用的接口版本，固定为：1.0(必填)
     */
    private String version;

    /**
     * 通知URL(选填)
     */
    private String notifyUrl;

    /**
     * 应用授权TOKEN(选填)
     */
    private String appAuthToken;

    /**
     * 支付参数(必填)
     */
    private String bizContent;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFormat() {
        return "JSON";
    }

    public String getCharset() {
        return "utf-8";
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getVersion() {
        return "1.0";
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public void setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }
}
