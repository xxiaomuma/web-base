package pers.xiaomuma.framework.thirdparty.im.tencent.constant;

import java.util.Objects;

public enum EventCommand {

    NA("NA", "未知事件命令"),
    STATE_STATE_CHANGE("State.StateChange", "状态变更回调"),
    PROFILE_CALLBACK_PORTRAIT_SET("Profile.CallbackPortraitSet", "更新资料之后回调"),
    SNS_CALLBACK_PREV_FRIEND_ADD("Sns.CallbackPrevFriendAdd", "添加好友之前回调"),
    SNS_CALLBACK_PREV_FRIEND_RESPONSE("Sns.CallbackPrevFriendResponse", "添加好友回应之前回调"),
    SNS_CALLBACK_FRIEND_ADD("Sns.CallbackFriendAdd", "添加好友之后回调"),
    SNS_CALLBACK_FRIEND_DELETE("Sns.CallbackFriendDelete", "删除好友之后回调"),
    SNS_CALLBACK_BLACK_LIST_ADD("Sns.CallbackBlackListAdd", "添加黑名单之后回调"),
    SNS_CALLBACK_BLACK_LIST_DELETE("Sns.CallbackBlackListDelete", "删除黑名单之后回调"),
    C2C_CALLBACK_BEFORE_SEND_MSG("C2C.CallbackBeforeSendMsg", "发单聊消息之前回调"),
    C2C_CALLBACK_AFTER_SEND_MSG("C2C.CallbackAfterSendMsg", "发单聊消息之后回调"),
    C2C_CALLBACK_AFTER_MSG_REPORT("C2C.CallbackAfterMsgReport", "单聊消息已读上报后回调"),
    C2C_CALLBACK_AFTER_MSG_WITH_DRAW("C2C.CallbackAfterMsgWithDraw", "单聊消息撤回后回调"),
    GROUP_CALLBACK_BEFORE_CREATE_GROUP("Group.CallbackBeforeCreateGroup", "创建群组之前回调"),
    GROUP_CALLBACK_AFTER_CREATE_GROUP("Group.CallbackAfterCreateGroup", "创建群组之后回调"),
    GROUP_CALLBACK_BEFORE_APPLY_JOIN_GROUP("Group.CallbackBeforeApplyJoinGroup", "申请入群之前回调"),
    GROUP_CALLBACK_BEFORE_INVITE_JOIN_GROUP("Group.CallbackBeforeInviteJoinGroup", "拉人入群之前回调"),
    GROUP_CALLBACK_AFTER_NEW_MEMBER_JOIN("Group.CallbackAfterNewMemberJoin", "新成员入群之后回调"),
    GROUP_CALLBACK_AFTER_MEMBER_EXIT("Group.CallbackAfterMemberExit", "群成员离开之后回调"),
    GROUP_CALLBACK_BEFORE_SEND_MSG("Group.CallbackBeforeSendMsg", "群内发言之前回调"),
    GROUP_CALLBACK_AFTER_SEND_MSG("Group.CallbackAfterSendMsg", "群内发言之后回调"),
    GROUP_CALLBACK_AFTER_GROUP_FULL("Group.CallbackAfterGroupFull", "群组满员之后回调"),
    GROUP_CALLBACK_AFTER_GROUP_DESTROYED("Group.CallbackAfterGroupDestroyed", "群组解散之后回调"),
    GROUP_CALLBACK_AFTER_GROUP_INFO_CHANGED("Group.CallbackAfterGroupInfoChanged", "群组资料变动之后回调"),
    GROUP_CALLBACK_ON_MEMBER_STATE_CHANGE("Group.CallbackOnMemberStateChange", "直播群成员在线状态回调"),
    GROUP_CALLBACK_SEND_MSG_EXCEPTION("Group.CallbackSendMsgException", "发送群聊消息异常回调"),
    GROUP_CALLBACK_BEFORE_CREATE_TOPIC("Group.CallbackBeforeCreateTopic", "创建话题之前回调"),
    GROUP_CALLBACK_AFTER_CREATE_TOPIC("Group.CallbackAfterCreateTopic", "创建话题之后回调"),
    GROUP_CALLBACK_AFTER_TOPIC_DESTROYED("Group.CallbackAfterTopicDestroyed", "解散话题之后回调"),
    GROUP_CALLBACK_AFTER_TOPIC_INFO_CHANGED("Group.CallbackAfterTopicInfoChanged", "话题资料修改之后回调");

    private String command;
    private String desc;

    EventCommand(String command, String desc) {
        this.command = command;
        this.desc = desc;
    }

    public static EventCommand of(String command) {
        for (EventCommand eventCommand : values()) {
            if (Objects.equals(eventCommand.getCommand(), command)) {
                return eventCommand;
            }
        }
        return NA;
    }

    public String getCommand() {
        return command;
    }

    public String getDesc() {
        return desc;
    }
}
