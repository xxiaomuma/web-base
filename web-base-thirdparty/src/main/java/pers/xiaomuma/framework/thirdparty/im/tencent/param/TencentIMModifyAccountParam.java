package pers.xiaomuma.framework.thirdparty.im.tencent.param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.util.List;
import java.util.Map;

public class TencentIMModifyAccountParam {

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
        requestMap.put("From_Account", this.getUserId());

        Map<String, Object> profileNickMap = Maps.newHashMap();
        profileNickMap.put("Tag", "Tag_Profile_IM_Nick");
        profileNickMap.put("Value", this.nick);

        Map<String, Object> profileFaceUrlMap = Maps.newHashMap();
        profileFaceUrlMap.put("Tag", "Tag_Profile_IM_Image");
        profileFaceUrlMap.put("Value", this.getFaceUrl());

        List<Map<String, Object>> profileItems = Lists.newArrayList(profileNickMap, profileFaceUrlMap);
        requestMap.put("ProfileItem", profileItems);
        return JsonUtils.object2Json(requestMap);
    }

}
