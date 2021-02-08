package pers.xiaomuma.base.thirdparty.wx.config;


public class WxPayProperties extends WxProperties{

    private String merchantId;
    private String apiKey;
    private String v3ApiKey;
    private String certificateSerialNo;
    private String certPath;
    private String keyPemPath;
    private String certPemPath;
    private String notifyUrl;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getV3ApiKey() {
        return v3ApiKey;
    }

    public void setV3ApiKey(String v3ApiKey) {
        this.v3ApiKey = v3ApiKey;
    }

    public String getCertificateSerialNo() {
        return certificateSerialNo;
    }

    public void setCertificateSerialNo(String certificateSerialNo) {
        this.certificateSerialNo = certificateSerialNo;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getKeyPemPath() {
        return keyPemPath;
    }

    public void setKeyPemPath(String keyPemPath) {
        this.keyPemPath = keyPemPath;
    }

    public String getCertPemPath() {
        return certPemPath;
    }

    public void setCertPemPath(String certPemPath) {
        this.certPemPath = certPemPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
