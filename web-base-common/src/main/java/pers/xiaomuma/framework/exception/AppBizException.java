package pers.xiaomuma.framework.exception;


import pers.xiaomuma.framework.response.ResultCode;

public class AppBizException extends RuntimeException {

	private ResultCode resultCode;
	private String viewMessage;

	public AppBizException(String viewMessage){
		super(viewMessage);
		this.resultCode = ResultCode.APP_BIZ_ERROR;
		this.viewMessage = viewMessage;
	}

	public AppBizException(ResultCode resultCode, String viewMessage){
		super(viewMessage);
		this.resultCode = resultCode;
		this.viewMessage = viewMessage;
	}

	public ResultCode getResultCode() {
		return resultCode;
	}

	public String getViewMessage() {
		return viewMessage;
	}
}
