package pers.xiaomuma.framework.exception;


import pers.xiaomuma.framework.response.ResponseCode;

public class AppBizException extends RuntimeException {

	private final ResponseCode code;
	private final String message;

	public AppBizException(String message){
		super(message);
		this.code = ResponseCode.APP_BIZ_ERROR;
		this.message = message;
	}

	public AppBizException(ResponseCode code, String message){
		super(message);
		this.code = code;
		this.message = message;
	}

	public ResponseCode getResultCode() {
		return this.code;
	}

	public String getResultMessage() {
		return this.message;
	}
}
