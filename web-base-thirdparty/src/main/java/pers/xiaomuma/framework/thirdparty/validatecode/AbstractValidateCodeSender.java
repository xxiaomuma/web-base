package pers.xiaomuma.framework.thirdparty.validatecode;

import cn.hutool.core.util.RandomUtil;

public abstract class AbstractValidateCodeSender implements ValidateCodeAdapter {

    private int codeLength;
    private boolean simulate;

    public AbstractValidateCodeSender() {
        this(false, 6);
    }

    public AbstractValidateCodeSender(boolean simulate, int codeLength) {
        this.simulate = simulate;
        this.codeLength = codeLength;
    }

    @Override
    public String send(String mobile) {
        String code = "888888";
        if (!simulate) {
            code = RandomUtil.randomNumbers(codeLength);
            doSend(mobile, code);
        }
        return code;
    }

    abstract public void doSend(String mobile, String code);

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
