package pers.xiaomuma.framework.thirdparty.validatecode.email;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pers.xiaomuma.framework.thirdparty.email.*;
import pers.xiaomuma.framework.thirdparty.email.render.EmailBodyType;
import pers.xiaomuma.framework.thirdparty.validatecode.AbstractValidateCodeSender;
import pers.xiaomuma.framework.thirdparty.validatecode.ValidateCodeParam;

import java.util.concurrent.ExecutorService;


public class EmailValidateCodeSender extends AbstractValidateCodeSender {

    private final Logger logger = LoggerFactory.getLogger(EmailValidateCodeSender.class);
    private ExecutorService customizedThreadPool;
    private JavaMailSendHandler javaMailSendHandler;

    public EmailValidateCodeSender(boolean simulate, EmailProperties properties, ExecutorService customizedThreadPool) {
        this.customizedThreadPool = customizedThreadPool;
        this.javaMailSendHandler = new JavaMailSendHandler(properties);
        this.setCodeLength(6);
        this.setSimulate(simulate);
    }

    public EmailValidateCodeSender(boolean simulate, EmailProperties properties, SpringTemplateEngine springTemplateEngine, ExecutorService customizedThreadPool) {
        this.customizedThreadPool = customizedThreadPool;
        this.javaMailSendHandler = new JavaMailSendHandler(properties, springTemplateEngine);
        this.setCodeLength(6);
        this.setSimulate(simulate);
    }

    @Override
    public void doSend(ValidateCodeParam param, String code) {
        customizedThreadPool.submit(() -> processSendCode(param, code));
    }

    private void processSendCode(ValidateCodeParam param, String code) {
        String email = ((EmailValidateCodeParam) param).getEmail();
        String subject = ((EmailValidateCodeParam) param).getSubject();
        String body = ((EmailValidateCodeParam) param).getBody();
        EmailParam emailParam = new EmailParamBuilder()
                .bodyType(EmailBodyType.RAW_HTML)
                .body(String.format(body, code))
                .subject(subject)
                .receivers(Lists.newArrayList(email)).build();
        EmailResult result = javaMailSendHandler.send(emailParam);
        if (result.isSuccess()) {
            logger.info("邮件发送成功");
        }
    }
}
