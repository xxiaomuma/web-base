package pers.xiaomuma.base.common.api;

public class ResultDTO<T> {

	private int code;
	private String msg;
	private T data;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	public ResultDTO(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultDTO(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultDTO(IResultCode resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
	}

	public ResultDTO(IResultCode resultCode, T data) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
		this.data = data;
	}

	public static <T> ResultDTO<T> create(int code, String msg) {
		return new ResultDTO<>(code, msg);
	}

	public static <T> ResultDTO<T> create(IResultCode code) {
		return new ResultDTO<>(code);
	}

	public static <T> ResultDTO<T> create(IResultCode code, T data) {
		return new ResultDTO<>(code, data);
	}

	public static <T> ResultDTO<T> success() {
		return new ResultDTO<>(CommonCode.SUCCESS);
	}

	public static <T> ResultDTO<T> success(T data) {
		return new ResultDTO<>(CommonCode.SUCCESS, data);
	}


}
