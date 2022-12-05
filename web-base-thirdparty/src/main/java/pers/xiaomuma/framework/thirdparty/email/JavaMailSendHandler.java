package pers.xiaomuma.framework.thirdparty.email;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pers.xiaomuma.framework.serialize.JsonUtils;
import pers.xiaomuma.framework.thirdparty.email.render.CompositeEmailRender;
import pers.xiaomuma.framework.thirdparty.email.render.EmailBodyType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class JavaMailSendHandler implements EmailSendHandler {

    private final Logger logger = LoggerFactory.getLogger(JavaMailSendHandler.class);
    private final EmailProperties properties;
    private final JavaMailSender javaMailSender;
    private final CompositeEmailRender compositeEmailRender;

    public JavaMailSendHandler(EmailProperties properties) {
        this.properties = properties;
        this.javaMailSender = initializeSender(properties);
        this.compositeEmailRender = new CompositeEmailRender();
    }

    public JavaMailSendHandler(EmailProperties properties, SpringTemplateEngine springTemplateEngine) {
        this.properties = properties;
        this.javaMailSender = initializeSender(properties);
        this.compositeEmailRender = new CompositeEmailRender(springTemplateEngine);
    }

    public JavaMailSender initializeSender(EmailProperties properties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(properties.getPort());
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getPassword());
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setJavaMailProperties(extConfigProperties(properties.getTimeout(), properties.getPort()));
        return mailSender;
    }

    private Properties extConfigProperties(Integer timeout, Integer port) {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", Boolean.TRUE);
        properties.put("mail.smtp.starttls.enable", Boolean.TRUE);
        properties.put("mtp.starttls.required", Boolean.TRUE);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.ssl.enable", Boolean.TRUE);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.mime.splitlongparameters", Boolean.FALSE);
        return properties;
    }

    @Override
    public EmailResult send(EmailParam param) {
        try {
            MimeMessage mimeMessage = this.buildMimeMessage(param);
            javaMailSender.send(mimeMessage);
            logger.info("邮件发送成功, [{}]", JsonUtils.object2Json(param));
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("邮件发送失败, {}", e.getMessage());
            return EmailResult.fail(e.getMessage());
        }
        return EmailResult.success();
    }


    private MimeMessage buildMimeMessage(EmailParam param) throws MessagingException, UnsupportedEncodingException {
        EmailBodyType renderType = param.getRenderType();
        String contentBody = compositeEmailRender.render(renderType, param.getBody());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(properties.getUsername());
        helper.setTo(param.getReceivers().toArray(new String[]{}));
        helper.setSubject(param.getSubject());
        helper.setSentDate(new Date());
        helper.setText(contentBody, renderType.isHtml());
        List<File> attachFiles = param.getAttachFiles();
        if (CollectionUtils.isNotEmpty(attachFiles)) {
            for (File attachFile : attachFiles) {
                FileSystemResource resource = new FileSystemResource(attachFile);
                helper.addAttachment(MimeUtility.decodeText(attachFile.getName().replaceAll("\r", "").replaceAll("\n", "")), resource);
            }
        }
        return mimeMessage;
    }

}
