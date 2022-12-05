package pers.xiaomuma.framework.thirdparty.pay;

public class TransactionResult<T> {

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

    public TransactionResult(boolean success, String errorMsg, T data) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static <T> TransactionResult<T> success(T data) {
        return new TransactionResult<>(true, "ok", data);
    }

    public static <T> TransactionResult<T> error(String msg) {
        return new TransactionResult<>(false, msg, null);
    }



}
