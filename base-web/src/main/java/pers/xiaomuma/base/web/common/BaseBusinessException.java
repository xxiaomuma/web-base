package pers.xiaomuma.base.web.common;


import pers.xiaomuma.base.web.api.BusinessCode;
import pers.xiaomuma.base.web.api.IResultCode;

public class BaseBusinessException extends RuntimeException {

    private IResultCode code;
    private boolean shouldPrintCustomizedMsg;

    public BaseBusinessException(String msg) {
        super(msg);
        this.shouldPrintCustomizedMsg = true;
        this.code = BusinessCode.COMMON_BUSINESS_ERROR;
    }

    public BaseBusinessException(IResultCode code, String msg) {
        super(msg);
        this.shouldPrintCustomizedMsg = true;
        this.code = code;
    }

    public BaseBusinessException(IResultCode code) {
        super(code.getMsg());
        this.shouldPrintCustomizedMsg = false;
        this.code = code;
    }

    public IResultCode getCode() {
        return code;
    }

    public boolean isShouldPrintCustomizedMsg() {
        return shouldPrintCustomizedMsg;
    }
}
