package pers.xiaomuma.framework.thirdparty.im.tencent.param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TencentIMAddGroupMemberParam {

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

        List<Map<String, Object>> memberList = Lists.newArrayList();
        for (String memberId : this.getMemberIds()) {
            Map<String, Object> memberMap = Maps.newHashMap();
            memberMap.put("Member_Account", memberId);
            memberList.add(memberMap);
        }
        requestMap.put("MemberList", memberList);
        return JsonUtils.object2Json(requestMap);
    }
}
