package pers.xiaomuma.framework.thirdparty.email;


public interface EmailSendHandler {

    EmailResult syncSend(EmailParam emailDetails);

    void asyncSend(EmailParam emailDetails);
}
