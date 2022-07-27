package pers.xiaomuma.framework.exception;


import pers.xiaomuma.framework.response.ResponseCode;

public class AppBizException extends RuntimeException {

	private ResponseCode resultCode;
	private String viewMessage;

	public AppBizException(String viewMessage){
		super(viewMessage);
		this.resultCode = ResponseCode.APP_BIZ_ERROR;
		this.viewMessage = viewMessage;
	}

	public AppBizException(ResponseCode resultCode, String viewMessage){
		super(viewMessage);
		this.resultCode = resultCode;
		this.viewMessage = viewMessage;
	}

	public ResponseCode getResultCode() {
		return resultCode;
	}

	public String getViewMessage() {
		return viewMessage;
	}
}
