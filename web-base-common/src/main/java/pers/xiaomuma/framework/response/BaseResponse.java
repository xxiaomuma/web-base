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
    private ResponseCode code = ResponseCode.SUCCESS;
    @Builder.Default
    @Getter @Setter
    private boolean success = true;

    public void setCode(ResponseCode code) {
        this.code = code;
        this.success = (code == ResponseCode.SUCCESS);
    }

    public BaseResponse(String message, ResponseCode code) {
        this.message = message;
        this.code = code;
        this.success = (code == ResponseCode.SUCCESS);
    }

    public BaseResponse(boolean success, String message, ResponseCode code) {
        this.message = message;
        this.code = code;
        this.success = success;
    }


    public static BaseResponse success() {
        return new BaseResponse(true, "ok", ResponseCode.SUCCESS);
    }

    public static BaseResponse success(String message) {
        return new BaseResponse(true, message, ResponseCode.SUCCESS);
    }

    public static BaseResponse failed(String message) {
        return new BaseResponse(false, message, ResponseCode.APP_BIZ_ERROR);
    }

    public static BaseResponse failed(String message, ResponseCode code) {
        return new BaseResponse(false, message, code);
    }

    @Override
    public String toString() {
        return "{" + "\"message\":\"" + this.message +
                "\"," + "\"code\":" + code.toString() + "}";
    }

}
