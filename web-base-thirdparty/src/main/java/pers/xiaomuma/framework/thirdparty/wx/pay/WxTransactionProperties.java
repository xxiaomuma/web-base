package pers.xiaomuma.framework.thirdparty.wx.pay;

public class WxTransactionProperties {

    private String appId;

    private String merchantId;

    private String v3APIKey;

    private String APIKey;

    private String certificateSerialNo;

    private String certPath;

    private String keyPemPath;

    private String certPemPath;

    private String notifyUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getV3APIKey() {
        return v3APIKey;
    }

    public void setV3APIKey(String v3APIKey) {
        this.v3APIKey = v3APIKey;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
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
