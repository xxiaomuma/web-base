package pers.xiaomuma.framework.thirdparty.validatecode.aliyun;

import pers.xiaomuma.framework.thirdparty.validatecode.ValidateCodeParam;

public class AliYunValidateCodeParam implements ValidateCodeParam {

    private String mobile;

    private String codeTemplate;

    @Override
    public String getTarget() {
        return mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }
}
