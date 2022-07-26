package pers.xiaomuma.framework.exception;


public class ServiceUnavailableException extends InternalServerErrorException {

    public ServiceUnavailableException(String message) {
        super("Service Unavailable :" + message);
    }

}
