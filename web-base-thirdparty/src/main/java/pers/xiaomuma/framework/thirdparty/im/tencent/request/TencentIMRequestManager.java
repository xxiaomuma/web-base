package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.thirdparty.im.tencent.TencentIMProperties;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMFriendAddParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMFriendRemoveParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMModifyAccountParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMRegisterAccountParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.url.TencentIMUrlBuilder;
import pers.xiaomuma.framework.thirdparty.utils.GenerateUserSigUtils;

import java.nio.charset.StandardCharsets;

public class TencentIMRequestManager implements TencentIMRequest {

    private final RestTemplate restTemplate;
    private final TencentIMProperties properties;

    public TencentIMRequestManager(TencentIMProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAdminSig() {
        return GenerateUserSigUtils.genTLSSignature(properties.getAppId(), properties.getUsername(), properties.getExpireTime(), null, properties.getSecret());
    }

    @Override
    public String generateUserSig(String userId) {
        return GenerateUserSigUtils.genTLSSignature(properties.getAppId(), userId, properties.getExpireTime(), null, properties.getSecret());
    }

    @Override
    public ResponseEntity<String> register(String adminSig, TencentIMRegisterAccountParam param) {
        String requestAddress = TencentIMUrlBuilder.accountImportUrlBuilder()
                .domain(properties.getDomain())
                .version(properties.getVersion())
                .sdkappid(properties.getAppId())
                .identifier(properties.getUsername())
                .usersig(adminSig)
                .build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(requestAddress, HttpMethod.POST, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> modifyAccount(String adminSig, TencentIMModifyAccountParam param) {
        String requestAddress = TencentIMUrlBuilder.accountModifyUrlBuilder()
                .domain(properties.getDomain())
                .version(properties.getVersion())
                .sdkappid(properties.getAppId())
                .identifier(properties.getUsername())
                .usersig(adminSig)
                .build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(requestAddress, HttpMethod.POST, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> addFriend(String adminSig, TencentIMFriendAddParam param) {
        String requestAddress = TencentIMUrlBuilder.friendAddBuilder()
                .domain(properties.getDomain())
                .version(properties.getVersion())
                .sdkappid(properties.getAppId())
                .identifier(properties.getUsername())
                .usersig(adminSig)
                .build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(requestAddress, HttpMethod.POST, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> removeFriend(String adminSig, TencentIMFriendRemoveParam param) {
        String requestAddress = TencentIMUrlBuilder.friendRemoveBuilder()
                .domain(properties.getDomain())
                .version(properties.getVersion())
                .sdkappid(properties.getAppId())
                .identifier(properties.getUsername())
                .usersig(adminSig)
                .build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(requestAddress, HttpMethod.POST, httpEntity, String.class);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("ContentType", "application/json;charset=UTF-8");
        headers.set("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }


}
