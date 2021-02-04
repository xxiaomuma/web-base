package pers.xiaomuma.base.thirdparty.wx.mp.bo;

import com.fasterxml.jackson.annotation.JsonAlias;


public class WxAccessTokenBO {

    @JsonAlias("access_token")
    private String accessToken;
    @JsonAlias("expires_in")
    private String expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
