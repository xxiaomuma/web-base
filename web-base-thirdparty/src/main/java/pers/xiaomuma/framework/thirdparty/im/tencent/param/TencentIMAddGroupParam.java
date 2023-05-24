package pers.xiaomuma.framework.thirdparty.im.tencent.param;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.util.List;
import java.util.Map;

public class TencentIMAddGroupParam {

    private String groupId;

    private String name;

    private String faceUrl;

    private List<GroupMemberParam> memberParams;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public List<GroupMemberParam> getMemberParams() {
        return memberParams;
    }

    public void setMemberParams(List<GroupMemberParam> memberParams) {
        this.memberParams = memberParams;
    }

    public static class GroupMemberParam {

        private Integer memberId;

        private String role;

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public String parse2RequestBody() {
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("Type", "Private");
        requestMap.put("GroupId", this.getGroupId());
        requestMap.put("Name", this.getName());
        requestMap.put("FaceUrl", this.getFaceUrl());

        List<Map<String, Object>> memberList = Lists.newArrayList();
        for (GroupMemberParam param : this.getMemberParams()) {
            Map<String, Object> memberMap = Maps.newHashMap();
            memberMap.put("Member_Account", param.getMemberId());
            if ("ADMIN".equals(param.getRole())) {
                memberMap.put("Role", "ADMIN");
            }
            memberList.add(memberMap);
        }
        requestMap.put("MemberList", memberList);
        return JsonUtils.object2Json(requestMap);
    }

}
