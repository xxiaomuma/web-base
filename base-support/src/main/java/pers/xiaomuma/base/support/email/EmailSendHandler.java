package pers.xiaomuma.base.support.email;


public interface EmailSendHandler {

    EmailResult sendSync(EmailParam emailDetails);

    void sendAsync(EmailParam emailDetails);
}
