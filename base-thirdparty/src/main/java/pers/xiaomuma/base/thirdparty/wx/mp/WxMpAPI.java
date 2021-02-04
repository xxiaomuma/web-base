package pers.xiaomuma.base.thirdparty.wx.mp;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.thirdparty.wx.BaseWxErrorResult;
import pers.xiaomuma.base.thirdparty.wx.WxResult;
import pers.xiaomuma.base.thirdparty.wx.config.WxProperties;
import pers.xiaomuma.base.thirdparty.wx.mp.bo.WxAccessTokenBO;
import pers.xiaomuma.base.thirdparty.wx.mp.request.WxRequestManager;
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

    @SneakyThrows
    private <T> WxResult<T> handleWxResponse(ResponseEntity<String> response, Class<T> clazz) {
        boolean success = response.getStatusCode().is2xxSuccessful();
        if (success) {
            String body = response.getBody();
            if (Objects.nonNull(body)) {
                BaseWxErrorResult errorResult = JsonUtils.json2Object(body, BaseWxErrorResult.class);
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
