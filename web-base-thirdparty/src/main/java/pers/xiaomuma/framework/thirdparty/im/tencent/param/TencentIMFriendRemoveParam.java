package pers.xiaomuma.framework.thirdparty.im.tencent.param;


import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.util.Map;

public class TencentIMFriendRemoveParam {

    private String userId;

    private String friendId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("From_Account", this.getUserId());
        requestMap.put("To_Account", this.getFriendId());
        requestMap.put("DeleteType", "Delete_Type_Both");
        return JsonUtils.object2Json(requestMap);
    }
}
