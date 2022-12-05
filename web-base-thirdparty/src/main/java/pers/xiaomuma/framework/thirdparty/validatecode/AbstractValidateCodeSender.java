package pers.xiaomuma.framework.thirdparty.validatecode;

import cn.hutool.core.util.RandomUtil;

public abstract class AbstractValidateCodeSender implements ValidateCodeAdapter {

    private int codeLength;
    private boolean simulate;

    protected AbstractValidateCodeSender() {
        this(false, 6);
    }

    protected AbstractValidateCodeSender(boolean simulate, int codeLength) {
        this.simulate = simulate;
        this.codeLength = codeLength;
    }

    @Override
    public String send(ValidateCodeParam param) {
        String code = "888888";
        if (!simulate) {
            code = RandomUtil.randomNumbers(codeLength);
            doSend(param, code);
        }
        return code;
    }

    public abstract void doSend(ValidateCodeParam param, String code);

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public boolean isSimulate() {
        return simulate;
    }

    public void setSimulate(boolean simulate) {
        this.simulate = simulate;
    }
}
