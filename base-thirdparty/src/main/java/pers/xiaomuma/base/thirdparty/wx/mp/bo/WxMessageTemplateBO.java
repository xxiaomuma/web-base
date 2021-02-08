package pers.xiaomuma.base.thirdparty.wx.mp.bo;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Objects;

public class WxMessageTemplateBO {

    private String touser;
    private WxMessageBodyBO messageBody;

    public String toJson() {
        WxMessageBodyBO body = this.messageBody;
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.touser)).append(",");
        buffer.append("\"mp_template_msg\":{");
        buffer.append(String.format("\"appid\":\"%s\"", body.getAppId())).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", body.getTemplateId())).append(",");
        if (!StringUtils.isEmpty(body.getUrl())) {
            buffer.append(String.format("\"url\":\"%s\"", body.getUrl())).append(",");
        }
        if (Objects.nonNull(body.getMiniProgram())) {
            WxMessageMpPageBO program = body.getMiniProgram();
            buffer.append("\"miniprogram\":{");
            buffer.append(String.format("\"appid\":\"%s\"", program.getAppId())).append(",");
            buffer.append(String.format("\"pagepath\":\"%s\"", program.getPagePath()));
            buffer.append("}").append(",");
        }
        buffer.append("\"data\":{");
        List<WxMessageContextParamBO> params = body.getData();
        if (CollectionUtil.isNotEmpty(params)) {
            for(int i = 0; i < params.size(); ++i) {
                WxMessageContextParamBO param = params.get(i);
                buffer.append(String.format("\"%s\":{", param.getName()));
                buffer.append(String.format("\"value\":\"%s\"", param.getValue()));
                if (!StringUtils.isEmpty(param.getColor())) {
                    buffer.append(",").append(String.format("\"color\":\"%s\"", param.getColor()));
                }
                buffer.append("}");
                if (i < params.size() - 1) {
                    buffer.append(",");
                }
            }
        }
        buffer.append("}");
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public WxMessageBodyBO getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(WxMessageBodyBO messageBody) {
        this.messageBody = messageBody;
    }
}
