package pers.xiaomuma.base.support.email;

import cn.hutool.extra.mail.MailException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.support.email.config.EmailProperties;
import pers.xiaomuma.base.support.email.render.CompositeEmailRender;
import pers.xiaomuma.base.support.email.render.EmailBodyType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class JavaMailSendHandler implements EmailSendHandler {

    private final Logger logger = LoggerFactory.getLogger(JavaMailSendHandler.class);
    private JavaMailSender javaMailSender;
    private CompositeEmailRender compositeEmailRender;
    private EmailProperties properties;
    private EmailSendScheduler emailSendScheduler;

    public JavaMailSendHandler(EmailProperties properties) {
        this.properties = properties;
        this.javaMailSender = initializeSender(properties);
        this.compositeEmailRender = new CompositeEmailRender();
        this.emailSendScheduler = new EmailSendScheduler(properties.getAsyncThreadNum());
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
        properties.put("mail.transport.protocol","smtp");
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
    public EmailResult sendSync(EmailParam emailDetails) {
        try {
            MimeMessage mimeMessage = this.assembleMimeMessage(emailDetails);
            javaMailSender.send(mimeMessage);
            logger.info("??????????????????, [{}]", JsonUtils.object2Json(emailDetails));
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("??????????????????, {}", e.getMessage());
            return EmailResult.fail(e.getMessage());
        }
        return EmailResult.success();
    }

    @Override
    public void sendAsync(EmailParam emailDetails) {
        MimeMessage mimeMessage;
        try {
            mimeMessage = this.assembleMimeMessage(emailDetails);
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error("??????????????????", e);
            return;
        }
        emailSendScheduler.submitTask(mimeMessage);
    }

    private MimeMessage assembleMimeMessage(EmailParam emailDetails) throws MessagingException, UnsupportedEncodingException {
        EmailBodyType renderType = emailDetails.getRenderType();
        String contentBody = compositeEmailRender.render(renderType, emailDetails.getBody());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(properties.getUsername());
        helper.setTo(emailDetails.getReceivers().toArray(new String[]{}));
        helper.setSubject(emailDetails.getSubject());
        helper.setSentDate(new Date());
        helper.setText(contentBody, renderType.isHtml());
        List<File> attachFiles = emailDetails.getAttachFiles();
        if (CollectionUtils.isNotEmpty(attachFiles)) {
            for (File attachFile : attachFiles) {
                FileSystemResource resource = new FileSystemResource(attachFile);
                helper.addAttachment(MimeUtility.decodeText(attachFile.getName().replaceAll("\r", "").replaceAll("\n", "")), resource);
            }
        }
        return mimeMessage;
    }

    private class EmailSendScheduler {

        private ExecutorService executor;
        private Logger logger = LoggerFactory.getLogger(getClass());

        public EmailSendScheduler(int asyncThreadNum) {
            executor = new ThreadPoolExecutor(
                    asyncThreadNum, asyncThreadNum,
                    60, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(2048),
                    (runnable, threadPoolExecutor) -> logger.error("?????????????????????????????????")
            );
        }

        public void submitTask(MimeMessage message) {
            if (Objects.isNull(message)) {
                return;
            }
            executor.submit(() -> {
                try {
                    javaMailSender.send(message);
                } catch (MailException e) {
                    logger.error("??????????????????", e);
                }
            });
        }

        public void submitTask(List<MimeMessage> messages) {
            if (CollectionUtils.isEmpty(messages)) {
                return;
            }
            executor.submit(() -> {
                try {
                    javaMailSender.send(messages.toArray(new MimeMessage[]{}));
                } catch (MailException e) {
                    logger.error("??????????????????", e);
                }
            });
        }

    }
}
