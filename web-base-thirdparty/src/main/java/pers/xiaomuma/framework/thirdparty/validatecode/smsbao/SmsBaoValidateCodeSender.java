package pers.xiaomuma.framework.thirdparty.validatecode.smsbao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.thirdparty.validatecode.AbstractValidateCodeSender;
import pers.xiaomuma.framework.thirdparty.validatecode.ValidateCodeParam;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class SmsBaoValidateCodeSender extends AbstractValidateCodeSender {

    private final Logger logger = LoggerFactory.getLogger(SmsBaoValidateCodeSender.class);
    private static final String SEND_SMS_URL = "http://api.smsbao.com/sms?u=%s&p=%s&m=%s&c=%s";
    private final ExecutorService customizedThreadPool;
    private final RestTemplate restTemplate;
    private final SmsBaoValidateProperties properties;

    public SmsBaoValidateCodeSender(boolean simulate, SmsBaoValidateProperties properties, RestTemplate restTemplate, ExecutorService customizedThreadPool) {
        this.properties = properties;
        this.customizedThreadPool = customizedThreadPool;
        this.restTemplate = restTemplate;
        this.setCodeLength(6);
        this.setSimulate(simulate);
    }

    @Override
    public void doSend(ValidateCodeParam param, String code) {
        customizedThreadPool.submit(() -> processSendCode(param, code));
    }

    private void processSendCode(ValidateCodeParam param, String code) {
        try {
            SmsBaoValidateCodeParam codeParam = (SmsBaoValidateCodeParam) param;
            String mobile = codeParam.getMobile();
            String content = String.format(codeParam.getContent(), code);
            content = URLEncoder.encode(content, "UTF-8");
            String requestAddress = String.format(SEND_SMS_URL, properties.getUsername(), properties.getPassword(), mobile, content);
            ResponseEntity<String> response = restTemplate.getForEntity(requestAddress, String.class);
            if (Objects.equals(response.getBody(), "0")) {
                logger.info("短信发送成功");
            }
        } catch (Exception e) {
            logger.info("发送短信失败, err:{}", e.getMessage());
        }

    }
}
