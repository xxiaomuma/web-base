package pers.xiaomuma.framework.thirdparty.pay.ali;


import cn.hutool.core.util.StrUtil;
import pers.xiaomuma.framework.exception.AppBizException;
import pers.xiaomuma.framework.thirdparty.utils.FileUtils;

public class AliTransactionProperties {

    private String appId;

    private String privateKey;

    private String publicKey;

    private String notifyUrl;

    private String privatePemPath;

    private String publicPemPath;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        if (StrUtil.isBlank(this.privateKey)) {
            if (StrUtil.isBlank(this.privatePemPath)) {
                throw new AppBizException("请配置支付宝私钥");
            }
            String content = FileUtils.getFileContent(this.privatePemPath);
            this.privateKey = content
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
        }
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        if (StrUtil.isBlank(this.publicKey)) {
            if (StrUtil.isBlank(this.publicPemPath)) {
                throw new AppBizException("请配置支付宝公钥");
            }
            String content = FileUtils.getFileContent(this.publicPemPath);
            this.publicKey = content
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");
        }
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getPrivatePemPath() {
        return privatePemPath;
    }

    public void setPrivatePemPath(String privatePemPath) {
        this.privatePemPath = privatePemPath;
    }

    public String getPublicPemPath() {
        return publicPemPath;
    }

    public void setPublicPemPath(String publicPemPath) {
        this.publicPemPath = publicPemPath;
    }
}
