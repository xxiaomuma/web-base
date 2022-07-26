package pers.xiaomuma.framework.exception;

public class StartupFailureException extends InternalServerErrorException {

    public StartupFailureException(String message) {
        super(message);
    }
}
