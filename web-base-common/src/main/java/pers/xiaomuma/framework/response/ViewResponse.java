package pers.xiaomuma.framework.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import pers.xiaomuma.framework.serializer.ResultCodeDeserializer;
import pers.xiaomuma.framework.standard.service.ServiceResult;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ViewResponse <T> {

    private String message = "";

    @JsonDeserialize(using = ResultCodeDeserializer.class)
    private ResponseCode code = ResponseCode.SUCCESS;

    private boolean success = true;

    private T view = null;

    public static <T> ViewResponse<T> success() {
        return response(true, "ok", null);
    }

    public static <T> ViewResponse<T> success(T view) {
        return response(true, "ok", view);
    }

    public static ViewResponse<Void> failed(String message){
        return response(false, ResponseCode.APP_BIZ_ERROR ,message, null);
    }

    public static ViewResponse<Void> failed(ResponseCode code, String message) {
        return response(code, message, null);
    }

    public static <T> ViewResponse<T> response(boolean success, String message, T view) {
        return response(success, ResponseCode.SUCCESS, message, view);
    }

    public static <T> ViewResponse<T> response(ResponseCode resultCode, String message, T view) {
        return response(Objects.equals(resultCode, ResponseCode.SUCCESS), resultCode, message, view);
    }

    public static <T> ViewResponse<T> response(boolean success, ResponseCode resultCode, String message, T view) {
        ViewResponse<T> viewResponse = new ViewResponse<>();
        viewResponse.setSuccess(success);
        viewResponse.setCode(resultCode);
        viewResponse.setMessage(message);
        viewResponse.setView(view);
        return viewResponse;
    }

    public static <T> ViewResponse<T> response(ServiceResult<T> serviceResult) {
        ViewResponse<T> viewResponse = new ViewResponse<>();
        viewResponse.setSuccess(serviceResult.isSuccess());
        viewResponse.setCode(serviceResult.isSuccess()? ResponseCode.SUCCESS : ResponseCode.APP_BIZ_ERROR);
        viewResponse.setMessage(serviceResult.getErrorMsg());
        viewResponse.setView(serviceResult.getReturnValue());
        return viewResponse;
    }

}
