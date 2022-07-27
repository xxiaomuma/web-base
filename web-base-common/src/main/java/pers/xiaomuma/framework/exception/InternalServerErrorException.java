package pers.xiaomuma.framework.exception;

import lombok.Getter;
import pers.xiaomuma.framework.response.ResponseCode;

public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private final ResponseCode resultCode;

    public InternalServerErrorException(String message) {
        super(message);
        this.resultCode = ResponseCode.INTERNAL_SERVER_ERROR;
    }

    public InternalServerErrorException(ResponseCode resultCode, String msg) {
        this(msg, resultCode, null);
    }

    public InternalServerErrorException(ResponseCode resultCode, Throwable cause) {
        this("Internal Server Error", resultCode, cause);
    }

    public InternalServerErrorException(String msg, Throwable cause) {
        this(msg, ResponseCode.INTERNAL_SERVER_ERROR, cause);
    }

    public InternalServerErrorException(String msg, ResponseCode resultCode, Throwable cause) {
        super(msg, cause);
        this.resultCode = resultCode;
    }

    /**
     * for better log
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
