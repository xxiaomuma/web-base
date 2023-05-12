package pers.xiaomuma.framework.thirdparty.im.tencent.param;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.util.List;
import java.util.Map;

public class TencentIMAddFriendParam {

    private String userId;

    private String friendId;

    private String source;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("From_Account", this.getUserId());
        requestMap.put("AddType", "Add_Type_Both");
        requestMap.put("ForceAddFlags", 1);

        Map<String, Object> friendMap = Maps.newHashMap();
        friendMap.put("To_Account", this.getFriendId());
        friendMap.put("AddSource", this.getSource());

        List<Map<String, Object>> friendItems = Lists.newArrayList(friendMap);
        requestMap.put("AddFriendItem", friendItems);
        return JsonUtils.object2Json(requestMap);
    }
}
