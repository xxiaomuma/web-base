package pers.xiaomuma.framework.thirdparty.validatecode.smsbao;

import cn.hutool.core.util.StrUtil;
import pers.xiaomuma.framework.thirdparty.validatecode.ValidateCodeParam;

public class SmsBaoValidateCodeParam implements ValidateCodeParam {

    private String mobile;

    private String content;

    @Override
    public String getTarget() {
        return this.mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        if (StrUtil.isBlank(this.content)) {
            return "验证码 %s，用于手机注册，5分钟内有效。验证码提供他人可能导致账户被盗，请勿泄露，谨防被骗。";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
