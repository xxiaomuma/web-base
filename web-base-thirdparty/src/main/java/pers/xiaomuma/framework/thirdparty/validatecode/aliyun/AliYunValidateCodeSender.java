package pers.xiaomuma.framework.thirdparty.validatecode.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.thirdparty.validatecode.AbstractValidateCodeSender;
import java.util.concurrent.ExecutorService;



public class AliYunValidateCodeSender extends AbstractValidateCodeSender {

    private final Logger logger = LoggerFactory.getLogger(AliYunValidateCodeSender.class);
    private IAcsClient acsClient;
    private AliYunValidateProperties properties;
    private ExecutorService customizedThreadPool;
    private static final String REGION_ID = "cn-hangzhou";
    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    public AliYunValidateCodeSender(boolean simulate, AliYunValidateProperties properties, ExecutorService customizedThreadPool) throws ClientException {
        this.properties = properties;
        this.customizedThreadPool = customizedThreadPool;
        String appKey = properties.getAppKey();
        String secret = properties.getSecret();

        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, appKey, secret);
        DefaultProfile.addEndpoint(REGION_ID, REGION_ID, PRODUCT, DOMAIN);
        acsClient = new DefaultAcsClient(profile);

        this.setCodeLength(6);
        this.setSimulate(simulate);
    }

    @Override
    public void doSend(String mobile, String code) {
        customizedThreadPool.submit(() -> processSendCode(mobile, code));
    }

    private void processSendCode(String mobile, String code) {
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(mobile);
        request.setSignName(this.properties.getSignName());
        request.setTemplateCode(this.properties.getCodeTemplate());
        request.setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            logger.error("发送短信验证码失败", e);
        }
        if(sendSmsResponse != null && StringUtils.equals(sendSmsResponse.getCode(), "OK")) {
            logger.info("发送短信验证码成功, mobile:{}", mobile);
        }
    }
}
