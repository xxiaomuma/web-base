package pers.xiaomuma.base.thirdparty.wx.mp.bo;

import java.util.Objects;

public class WxCustomMessageTemplateBO {

    private String toUser;
    private String msgType;
    private WxCustomTextMessageBodyBO textMessageBody;
    private WxCustomImageMessageBodyBO imageMessageBody;
    private WxCustomLinkMessageBodyBO linkMessageBody;
    private WxCustomMpPageMessageBodyBO miniProgramPageMessageBody;

    public String toJson() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");
        buffer.append(String.format("\"msgtype\":\"%s\"", this.msgType)).append(",");
        if (Objects.nonNull(this.textMessageBody)) {
            buffer.append("\"text\":{");
            buffer.append(String.format("\"content\":\"%s\"", this.textMessageBody.getContent()));
            buffer.append("}");
        } else if (Objects.nonNull(this.imageMessageBody)) {
            buffer.append("\"image\":{");
            buffer.append(String.format("\"media_id\":\"%s\"", this.imageMessageBody.getMediaId()));
            buffer.append("}");
        } else if (Objects.nonNull(this.linkMessageBody)) {
            buffer.append("\"link\":{");
            buffer.append(String.format("\"title\": \"%s\"", this.linkMessageBody.getTitle())).append(",");
            buffer.append(String.format("\"description\": \"%s\"", this.linkMessageBody.getDescription())).append(",");
            buffer.append(String.format("\"url\": \"%s\"", this.linkMessageBody.getUrl())).append(",");
            buffer.append(String.format("\"thumb_url\": \"%s\"", this.linkMessageBody.getThumbUrl()));
            buffer.append("}");
        } else {
            if (Objects.isNull(this.miniProgramPageMessageBody)) {
                throw new NullPointerException("未指定发送的消息体");
            }
            buffer.append("\"miniprogrampage\":{");
            buffer.append(String.format("\"title\":\"%s\"", this.miniProgramPageMessageBody.getTitle())).append(",");
            buffer.append(String.format("\"pagepath\":\"%s\"", this.miniProgramPageMessageBody.getPagePath())).append(",");
            buffer.append(String.format("\"thumb_media_id\":\"%s\"", this.miniProgramPageMessageBody.getThumbMediaId()));
            buffer.append("}");
        }

        buffer.append("}");
        return buffer.toString();
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public WxCustomTextMessageBodyBO getTextMessageBody() {
        return textMessageBody;
    }

    public void setTextMessageBody(WxCustomTextMessageBodyBO textMessageBody) {
        this.textMessageBody = textMessageBody;
    }

    public WxCustomImageMessageBodyBO getImageMessageBody() {
        return imageMessageBody;
    }

    public void setImageMessageBody(WxCustomImageMessageBodyBO imageMessageBody) {
        this.imageMessageBody = imageMessageBody;
    }

    public WxCustomLinkMessageBodyBO getLinkMessageBody() {
        return linkMessageBody;
    }

    public void setLinkMessageBody(WxCustomLinkMessageBodyBO linkMessageBody) {
        this.linkMessageBody = linkMessageBody;
    }

    public WxCustomMpPageMessageBodyBO getMiniProgramPageMessageBody() {
        return miniProgramPageMessageBody;
    }

    public void setMiniProgramPageMessageBody(WxCustomMpPageMessageBodyBO miniProgramPageMessageBody) {
        this.miniProgramPageMessageBody = miniProgramPageMessageBody;
    }
}
