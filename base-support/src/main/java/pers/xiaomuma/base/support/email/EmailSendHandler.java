package pers.xiaomuma.base.support.email;


public interface EmailSendHandler {

    EmailResult syncSend(EmailParam emailDetails);

    void asyncSend(EmailParam emailDetails);
}
