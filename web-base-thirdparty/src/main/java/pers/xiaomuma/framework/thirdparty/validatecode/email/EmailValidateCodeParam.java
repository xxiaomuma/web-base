package pers.xiaomuma.framework.thirdparty.validatecode.email;

import pers.xiaomuma.framework.thirdparty.validatecode.ValidateCodeParam;

public class EmailValidateCodeParam implements ValidateCodeParam {

    private String email;

    private String subject;

    private String body;

    @Override
    public String getTarget() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
