package pers.xiaomuma.base.thirdparty.wx.mp;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.base.common.utils.AES_CBCUtils;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.thirdparty.wx.BaseWxResult;
import pers.xiaomuma.base.thirdparty.wx.WxResult;
import pers.xiaomuma.base.thirdparty.wx.config.WxProperties;
import pers.xiaomuma.base.thirdparty.wx.mp.bo.*;
import pers.xiaomuma.base.thirdparty.wx.mp.request.WxRequestManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;


public class WxMpAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxMpAPI.class);
    private WxRequestManager requestManager;

    public WxMpAPI(WxRequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public WxMpAPI(WxProperties properties) {
        this.requestManager = new WxRequestManager(properties);
    }

    public WxMpAPI(WxProperties properties, RestTemplate restTemplate) {
        this.requestManager = new WxRequestManager(properties, restTemplate);
    }

    public WxResult<WxAccessTokenBO> getAccessToken() {
        ResponseEntity<String> response = requestManager.fetchAccessToken();
        LOGGER.debug(response.getBody());
        return handleWxResponse(response, WxAccessTokenBO.class);
    }

    public WxResult<WxMpUserInfoBO> getUserInfo(String authorizeCode, String encryptData, String encryptIV) {
        String data = this.decryptWxData(authorizeCode, encryptData, encryptIV);
        if (StrUtil.isBlank(data)) {
            return WxResult.error("解密微信用户信息失败");
        }
        WxMpUserInfoBO userInfo = JsonUtils.json2Object(data, WxMpUserInfoBO.class);
        return WxResult.success(userInfo);
    }

    public WxResult<WxPhoneBO> getWxPhone(String authorizeCode, String encryptData, String encryptIV) {
        String data = this.decryptWxData(authorizeCode, encryptData, encryptIV);
        if (StrUtil.isBlank(data)) {
            return WxResult.error("解密微信手机号失败");
        }
        WxPhoneBO phone = JsonUtils.json2Object(data, WxPhoneBO.class);
        return WxResult.success(phone);
    }

    public WxResult<WxCode2SessionBO> getWxSessionKey(String authorizeCode) {
        ResponseEntity<String> response = requestManager.fetchCode2Session(authorizeCode);
        LOGGER.debug(response.getBody());
        return handleWxResponse(response, WxCode2SessionBO.class);
    }

    public WxResult<WxUserInfoBO> getUserInfo(String accessToken, String openId) {
        ResponseEntity<String> response = requestManager.fetchUserInfo(accessToken, openId);
        LOGGER.debug(response.getBody());
        return handleWxResponse(response, WxUserInfoBO.class);
    }

    public WxResult<BaseWxResult> sendTemplateMsg(String accessToken, WxMessageTemplateBO template) {
        ResponseEntity<String> response = requestManager.sendTemplateMsg(accessToken, template.toJson());
        LOGGER.debug(response.getBody());
        return handleWxResponse(response, BaseWxResult.class);
    }

    public WxResult<BaseWxResult> sendCustomizedMsg(String accessToken, WxCustomMessageTemplateBO template) {
        ResponseEntity<String> response = requestManager.sendCustomizedMsg(accessToken, template.toJson());
        return handleWxResponse(response, BaseWxResult.class);
    }

    public WxResult<InputStream> generateUnlimitedQrCode(String accessToken, WxAcodeUnlimitedBO unlimited) {
        ResponseEntity<byte[]> response = requestManager.fetchUnlimited(accessToken, unlimited.toJson());
        MediaType mediaType = response.getHeaders().getContentType();
        byte[] body = response.getBody();
        try {
            if (Objects.nonNull(body) && Objects.nonNull(mediaType) && mediaType.includes(MediaType.IMAGE_JPEG)) {
                return WxResult.success(new ByteArrayInputStream(body));
            }
        } catch (Exception e) {
            LOGGER.error("获取微信二维码失败: {}", response.getBody());
        }
        return WxResult.error("获取微信二维码失败");
    }

    private String decryptWxData(String authorizeCode, String encryptData, String encryptIV) {
        WxResult<WxCode2SessionBO> result = this.getWxSessionKey(authorizeCode);
        if (result.isSuccess()) {
            String sessionKey = result.getData().getSessionKey();
            try {
                return AES_CBCUtils.decryptData(encryptData, encryptIV, sessionKey);
            } catch (Exception var) {
                LOGGER.error("解密微信用户信息失败", var);
            }
        }
        return null;
    }

    @SneakyThrows
    private <T> WxResult<T> handleWxResponse(ResponseEntity<String> response, Class<T> clazz) {
        boolean success = response.getStatusCode().is2xxSuccessful();
        if (success) {
            String body = response.getBody();
            if (Objects.nonNull(body)) {
                BaseWxResult errorResult = JsonUtils.json2Object(body, BaseWxResult.class);
                if (Objects.isNull(errorResult.getErrCode()) || errorResult.getErrCode() == 0) {
                    T data = JsonUtils.json2Object(body, clazz);
                    return WxResult.success(data);
                } else {
                    return WxResult.error(errorResult.getErrCode(), errorResult.getErrMsg());
                }
            }
        }
        LOGGER.error("微信API请求异常, response: {}", response.getBody());
        return null;
    }
}
