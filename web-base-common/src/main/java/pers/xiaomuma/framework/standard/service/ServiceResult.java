package pers.xiaomuma.framework.standard.service;

public class ServiceResult<T> {

	private boolean success;

	private String errorMsg;

	private T returnValue;

	public ServiceResult(boolean success, String errorMsg, T returnValue) {
		this.success = success;
		this.errorMsg = errorMsg;
		this.returnValue = returnValue;
	}

	public static <T> ServiceResult<T> success(){
		return new ServiceResult<>(true, "ok", null);
	}

	public static <T> ServiceResult<T> success(T returnValue){
		return new ServiceResult<>(true, "ok", returnValue);
	}

	public static <T> ServiceResult<T> failed(String errorMsg){
		return failed(errorMsg, null);
	}

	public static <T> ServiceResult<T> failed(String errorMsg, T returnValue){
		return new ServiceResult<>(false, errorMsg, returnValue);
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

	public T getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(T returnValue) {
		this.returnValue = returnValue;
	}
}
