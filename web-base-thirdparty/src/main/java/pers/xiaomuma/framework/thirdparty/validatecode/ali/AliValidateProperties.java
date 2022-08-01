package pers.xiaomuma.framework.thirdparty.validatecode.ali;


public class AliValidateProperties {

    private String appKey;

    private String secret;

    private String signName;

    private String defaultCodeTemplate;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getDefaultCodeTemplate() {
        return defaultCodeTemplate;
    }

    public void setDefaultCodeTemplate(String defaultCodeTemplate) {
        this.defaultCodeTemplate = defaultCodeTemplate;
    }
}
