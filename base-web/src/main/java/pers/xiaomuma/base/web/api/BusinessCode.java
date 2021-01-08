package pers.xiaomuma.base.web.api;

public enum BusinessCode implements IResultCode {

	COMMON_BUSINESS_ERROR(9999, "业务异常"),
	NOT_EXIST(10001, "数据不存在"),
	USER_NOT_EXIT(20001, "账户不存在"),
	ACCOUNT_DISABLED(20002, "账户已禁用"),
	INVALID_PASSWORD(20003, "密码错误"),
	LOGIN_FAILED(20003, "登陆失败"),
	SERVICE_STOP_USING(40002, "服务已停用");

	private int code;
	private String msg;

	BusinessCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

}
