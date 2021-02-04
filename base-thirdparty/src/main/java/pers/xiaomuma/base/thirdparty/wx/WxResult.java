package pers.xiaomuma.base.thirdparty.wx;


public class WxResult<T> {

    private boolean success;
    private String errorMsg;
    private Integer errorCode;
    private T data;

    public WxResult(boolean success, Integer errorCode, String errorMsg, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> WxResult<T> error(String errorMessage) {
        return new WxResult<>(false, -1, errorMessage, null);
    }

    public static <T> WxResult<T> error(Integer errorCode, String errorMessage) {
        return new WxResult<>(false, errorCode, errorMessage, null);
    }

    public static <T> WxResult<T> success(T data) {
        return new WxResult<>(true, 0, "ok", data);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
