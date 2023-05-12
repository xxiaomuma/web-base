package pers.xiaomuma.framework.thirdparty.im.tencent.param;

import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.util.Map;
import java.util.Set;

public class TencentIMRemoveGroupMemberParam {

    private String groupId;

    private Set<String> memberIds;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<String> memberIds) {
        this.memberIds = memberIds;
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("GroupId", this.getGroupId());
        requestMap.put("Silence", 1);
        requestMap.put("MemberToDel_Account", this.getMemberIds());
        return JsonUtils.object2Json(requestMap);
    }
}
