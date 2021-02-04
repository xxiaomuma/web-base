package pers.xiaomuma.base.thirdparty.wx.mp.request;


import cn.hutool.http.ContentType;
import okhttp3.OkHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.base.thirdparty.wx.config.WxProperties;
import pers.xiaomuma.base.thirdparty.wx.mp.url.WxUrlBuilder;
import pers.xiaomuma.base.web.http.okhttp.OkHttpClientBuilder;


public class WxRequestManager implements WxRequest {

    private RestTemplate restTemplate;
    private WxProperties properties;

    public WxRequestManager(WxProperties properties) {
        OkHttpClient okHttpClient = OkHttpClientBuilder.newHttpClient();
        this.restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
        this.properties = properties;
    }

    public WxRequestManager(WxProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<String> fetchAccessToken() {
        String requestAddress = WxUrlBuilder.accessTokenUrlBuilder()
                .appId(properties.getAppId())
                .secret(properties.getSecret())
                .build();
        return restTemplate.getForEntity(requestAddress, String.class);
    }

    @Override
    public ResponseEntity<String> fetchCode2Session(String authorizeCode) {
        String requestAddress = WxUrlBuilder.code2SessionUrlBuilder()
                .appId(properties.getAppId())
                .secret(properties.getSecret())
                .jsCode(authorizeCode)
                .build();
        return restTemplate.getForEntity(requestAddress, String.class);
    }

    @Override
    public ResponseEntity<String> fetchUserInfo(String accessToken, String openId) {
        String requestAddress = WxUrlBuilder.userInfoUrlBuilder()
                .accessToken(accessToken)
                .openId(openId)
                .build();
        return restTemplate.getForEntity(requestAddress, String.class);
    }

    @Override
    public ResponseEntity<String> sendTemplateMsg(String accessToken, String message) {
        String requestAddress = WxUrlBuilder.uniformTemplateMsgUrlBuilder()
                .accessToken(accessToken).build();
        HttpEntity<String> httpEntity = this.setPostHttpEntity(message);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> sendCustomizedMsg(String accessToken, String message) {
        String requestAddress = WxUrlBuilder.customMsgUrlBuilder()
                .accessToken(accessToken).build();
        HttpEntity<String> httpEntity = this.setPostHttpEntity(message);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<byte[]> fetchUnlimited(String accessToken, String params) {
        String requestAddress = WxUrlBuilder.unLimitUrlBuilder()
                .accessToken(accessToken).build();
        HttpEntity<String> httpEntity = this.setPostHttpEntity(params);
        return restTemplate.postForEntity(requestAddress, httpEntity, byte[].class);

    }

    private HttpEntity<String> setPostHttpEntity(String params) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_TYPE, ContentType.JSON.toString());
        return new HttpEntity<>(params, header);
    }

}
