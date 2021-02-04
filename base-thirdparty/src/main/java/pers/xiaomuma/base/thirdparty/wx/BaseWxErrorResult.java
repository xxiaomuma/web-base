package pers.xiaomuma.base.thirdparty.wx;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BaseWxErrorResult {

    @JsonAlias("errcode")
    private Integer errCode;
    @JsonAlias("errmsg")
    private String errMsg;

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
