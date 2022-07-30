package pers.xiaomuma.framework.thirdparty.email;


import lombok.Data;
import pers.xiaomuma.framework.thirdparty.email.render.EmailBodyType;
import java.io.File;
import java.util.List;

@Data
public class EmailParamBuilder {

    private EmailDetails emailDetails = new EmailDetails();

    public EmailParamBuilder subject(String subject) {
        emailDetails.setSubject(subject);
        return this;
    }

    public EmailParamBuilder receivers(List<String> receivers) {
        emailDetails.setReceivers(receivers);
        return this;
    }

    public EmailParamBuilder body(Object body) {
        emailDetails.setBody(body);
        return this;
    }

    public EmailParamBuilder bodyType(EmailBodyType emailBodyType) {
        emailDetails.setEmailBodyType(emailBodyType);
        return this;
    }

    public EmailParamBuilder attachFiles(List<File> attachFiles) {
        emailDetails.setAttachFiles(attachFiles);
        return this;
    }


    public EmailParam build() {
        return emailDetails;
    }

    public static class EmailDetails implements EmailParam {

        private EmailBodyType emailBodyType;
        private String subject;
        private Object body;
        private List<String> receivers;
        private List<File> attachFiles;

        @Override
        public EmailBodyType getRenderType() {
            return this.emailBodyType;
        }

        @Override
        public Object getBody() {
            return this.body;
        }

        @Override
        public List<String> getReceivers() {
            return this.receivers;
        }

        @Override
        public String getSubject() {
            return this.subject;
        }

        @Override
        public List<File> getAttachFiles() {
            return this.attachFiles;
        }


        public void setEmailBodyType(EmailBodyType emailBodyType) {
            this.emailBodyType = emailBodyType;
        }

        public void setBody(Object content) {
            this.body = content;
        }

        public void setReceivers(List<String> receivers) {
            this.receivers = receivers;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setAttachFiles(List<File> attachFiles) {
            this.attachFiles = attachFiles;
        }
    }

}
