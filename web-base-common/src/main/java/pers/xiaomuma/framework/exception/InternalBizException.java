package pers.xiaomuma.framework.exception;

public class InternalBizException extends RuntimeException {

	public InternalBizException(String msg) {
		super(msg);
	}

	public InternalBizException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
