package pers.xiaomuma.framework.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pers.xiaomuma.framework.serializer.ResultCodeDeserializer;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    @Builder.Default
    @Getter @Setter
    private String message = "";
    @Builder.Default
    @Getter @Setter
    @JsonDeserialize(using = ResultCodeDeserializer.class)
    private ResultCode code = ResultCode.SUCCESS;
    @Builder.Default
    @Getter @Setter
    private boolean success = true;

    public void setCode(ResultCode code) {
        this.code = code;
        this.success = (code == ResultCode.SUCCESS);
    }

    public BaseResponse(String message, ResultCode code) {
        this.message = message;
        this.code = code;
        this.success = (code == ResultCode.SUCCESS);
    }

    public BaseResponse(boolean success, String message, ResultCode code) {
        this.message = message;
        this.code = code;
        this.success = success;
    }


    public static BaseResponse success() {
        return new BaseResponse(true, "ok", ResultCode.SUCCESS);
    }

    public static BaseResponse success(String message) {
        return new BaseResponse(true, message, ResultCode.SUCCESS);
    }

    public static BaseResponse failed(String message) {
        return new BaseResponse(false, message, ResultCode.APP_BIZ_ERROR);
    }

    public static BaseResponse failed(String message, ResultCode code) {
        return new BaseResponse(false, message, code);
    }

    @Override
    public String toString() {
        return "{" + "\"message\":\"" + this.message +
                "\"," + "\"code\":" + code.toString() + "}";
    }

}
