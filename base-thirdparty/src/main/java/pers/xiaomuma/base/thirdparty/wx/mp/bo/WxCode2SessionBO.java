package pers.xiaomuma.base.thirdparty.wx.mp.bo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class WxCode2SessionBO {

    @JsonAlias("session_key")
    private String sessionKey;
    private String openid;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
