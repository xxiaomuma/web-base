package pers.xiaomuma.framework.response;

import lombok.*;
import pers.xiaomuma.framework.standard.service.ServiceResult;
import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应编码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message = "";

    /**
     * 是否成功
     */
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(ResponseCode code) {
        this.message = code.getMsg();
        this.code = code.getCode();
    }

    public BaseResponse(String message, ResponseCode code) {
        this.message = message;
        this.code = code.getCode();
    }

    public BaseResponse(String message, T data, ResponseCode code) {
        this.message = message;
        this.code = code.getCode();
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>("ok", ResponseCode.SUCCESS);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("ok", data, ResponseCode.SUCCESS);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(message, data, ResponseCode.SUCCESS);
    }

    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(message, ResponseCode.SUCCESS);
    }

    public static <T> BaseResponse<T> failed(String message) {
        return new BaseResponse<>(message, ResponseCode.APP_BIZ_ERROR);
    }

    public static <T> BaseResponse<T> failed(String message, ResponseCode code) {
        return new BaseResponse<>(message, code);
    }

    public static <T> BaseResponse<T> failed(ResponseCode code) {
        return new BaseResponse<>(code.getMsg(), code);
    }

    public static <T> BaseResponse<T> response(ServiceResult<T> serviceResult) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(serviceResult.isSuccess()? ResponseCode.SUCCESS.getCode() : ResponseCode.APP_BIZ_ERROR.getCode());
        response.setMessage(serviceResult.getErrorMsg());
        response.setData(serviceResult.getReturnValue());
        return response;
    }

}
