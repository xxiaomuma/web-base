package pers.xiaomuma.framework.thirdparty.im.tencent.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TencentIMResponse {

    @JsonAlias("ActionStatus")
    private String actionStatus;

    @JsonAlias("ErrorCode")
    private Integer errorCode;

    @JsonAlias("ErrorInfo")
    private String errorInfo;


    public boolean isSuccess() {
        return errorCode == 0;
    }
}
