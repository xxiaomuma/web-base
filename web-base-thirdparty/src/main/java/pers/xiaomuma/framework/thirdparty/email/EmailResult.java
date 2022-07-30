package pers.xiaomuma.framework.thirdparty.email;

import lombok.Data;


@Data
public class EmailResult {

    private boolean success;

    private String message;

    public boolean isSuccess() {
        return this.success;
    }

    public static EmailResult success() {
        EmailResult result = new EmailResult();
        result.setSuccess(true);
        result.setMessage("ok");
        return result;
    }

    public static EmailResult fail(String errorMsg) {
        EmailResult result = new EmailResult();
        result.setSuccess(false);
        result.setMessage(errorMsg);
        return result;
    }

    public static EmailResult fail() {
        EmailResult result = new EmailResult();
        result.setSuccess(false);
        return result;
    }
}
