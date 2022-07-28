package pers.xiaomuma.framework.thirdparty.wx;

public class WxResult<T> {

    private boolean success;
    private String errorMsg;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }

    public WxResult(boolean success, String errorMsg, T data) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> WxResult<T> success(T data) {
        return new WxResult<>(true, "ok", data);
    }

    public static <T> WxResult<T> error(String msg) {
        return new WxResult<>(false, msg, null);
    }



}
