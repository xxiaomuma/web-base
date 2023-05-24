package pers.xiaomuma.framework.thirdparty.im.tencent.param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.util.Map;

@Data
public class TencentIMSenderMessageParam<T> {

    private String fromUserId;

    private String toUserId;

    private Integer random;

    private T body;

    @Data
    public static class CustomContentParam {

        private String data;

        private String desc;

        private String ext;

        private String sound;

    }

    @Data
    public static class TextContentParam {

        private String text;

    }

    public String parse2RequestBody() {
        long timestamp = System.currentTimeMillis() / 1000L;
        Map<String, Object> requestMap = Maps.newHashMap();
        requestMap.put("SyncOtherMachine", 1);
        requestMap.put("From_Account", this.getFromUserId());
        requestMap.put("To_Account", this.getToUserId());
        requestMap.put("MsgRandom", (int) timestamp);

        Map<String, Object> bodyMap = Maps.newHashMap();
        if (this.getBody() instanceof TencentIMSenderMessageParam.CustomContentParam) {

        } else if (this.getBody() instanceof TencentIMSenderMessageParam.TextContentParam) {
            Map<String, Object> contextMap = Maps.newHashMap();
            TencentIMSenderMessageParam.TextContentParam contentParam = (TencentIMSenderMessageParam.TextContentParam) this.getBody();
            contextMap.put("Text", contentParam.getText());

            bodyMap.put("MsgType", "TIMTextElem");
            bodyMap.put("MsgContent", contextMap);
        }
        requestMap.put("MsgBody", Lists.newArrayList(bodyMap));
        return JsonUtils.object2Json(requestMap);
    }
}
