package pers.xiaomuma.framework.thirdparty.im.tencent.param;

import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.util.Map;

public class TencentIMRegisterAccountParam {

    private String userId;

    private String nick;

    private String faceUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("UserID", this.getUserId());
        requestMap.put("Nick", this.getNick());
        requestMap.put("FaceUrl", this.getFaceUrl());
        return JsonUtils.object2Json(requestMap);
    }
}
