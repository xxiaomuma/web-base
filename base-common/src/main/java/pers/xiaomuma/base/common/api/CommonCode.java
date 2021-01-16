package pers.xiaomuma.base.common.api;

public enum CommonCode implements IResultCode {

	SUCCESS(0, "ok"),
	// HTTP
	BAD_REQUEST(400, "参数错误"),
	UNAUTHORIZED(401, "未登录"),
	FORBIDDEN(403, "无相关权限"),
	NOT_FOUND(404, "请求资源不存在"),
	METHOD_NOT_ALLOWED(405, "不支持该请求方式"),
	INTERNAL_SERVER_ERROR(500, "请求失败"),
	SERVICE_UNAVAILABLE(503, "服务暂不可用");

	private int code;
	private String msg;

	CommonCode(int code, String msg) {
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
