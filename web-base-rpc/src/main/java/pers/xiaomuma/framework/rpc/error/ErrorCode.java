package pers.xiaomuma.framework.rpc.error;

public interface ErrorCode {

    int MIN_BUSINESS_ERROR_STATUS = 600;

    int MAX_BUSINESS_ERROR_STATUS = 999;

    static boolean isBusinessStatus(int httpStatus) {
        return httpStatus >= MIN_BUSINESS_ERROR_STATUS && httpStatus <= MAX_BUSINESS_ERROR_STATUS;
    }

    @Deprecated
    String getCode();

    int getStatus();

    String getMessage();


}
