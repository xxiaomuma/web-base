package pers.xiaomuma.framework.thirdparty.im.tencent.url;

public class TencentIMUrlBuilder {

    public static TencentIMImportAccountUrlBuilder importAccountUrlBuilder() {
        return new TencentIMImportAccountUrlBuilder();
    }

    public static TencentIMModifyAccountUrlBuilder modifyAccountUrlBuilder() {
        return new TencentIMModifyAccountUrlBuilder();
    }

    public static TencentIMAddFriendBuilder addFriendBuilder() {
        return new TencentIMAddFriendBuilder();
    }

    public static TencentIMRemoveFriendBuilder removeFriendBuilder() {
        return new TencentIMRemoveFriendBuilder();
    }

    public static TencentIMAddGroupBuilder addGroupBuilder() {
        return new TencentIMAddGroupBuilder();
    }

    public static TencentIMAddGroupMemberBuilder addGroupMemberBuilder() {
        return new TencentIMAddGroupMemberBuilder();
    }

    public static TencentIMRemoveGroupMemberBuilder removeGroupMemberBuilder() {
        return new TencentIMRemoveGroupMemberBuilder();
    }

    public static TencentIMsSenderMessageBuilder senderMessageBuilder() {
        return new TencentIMsSenderMessageBuilder();
    }
}
