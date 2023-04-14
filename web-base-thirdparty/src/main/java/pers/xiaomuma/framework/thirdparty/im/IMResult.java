package pers.xiaomuma.framework.thirdparty.im;


public class IMResult<T> {

    private final boolean success;
    private final String errorMsg;
    private final T data;

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getData() {
        return data;
    }

    public IMResult(boolean success, String errorMsg, T data) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> IMResult<T> success() {
        return new IMResult<>(true, "ok", null);
    }

    public static <T> IMResult<T> success(T data) {
        return new IMResult<>(true, "ok", data);
    }

    public static <T> IMResult<T> error(String msg) {
        return new IMResult<>(false, msg, null);
    }
}
