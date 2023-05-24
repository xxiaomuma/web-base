package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.thirdparty.im.tencent.TencentIMProperties;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.*;
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
        String requestAddress = TencentIMUrlBuilder.importAccountUrlBuilder()
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
        String requestAddress = TencentIMUrlBuilder.modifyAccountUrlBuilder()
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
    public ResponseEntity<String> addFriend(String adminSig, TencentIMAddFriendParam param) {
        String requestAddress = TencentIMUrlBuilder.addFriendBuilder()
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
    public ResponseEntity<String> removeFriend(String adminSig, TencentIMRemoveFriendParam param) {
        String requestAddress = TencentIMUrlBuilder.removeFriendBuilder()
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
    public ResponseEntity<String> addGroup(String adminSig, TencentIMAddGroupParam param) {
        String requestAddress = TencentIMUrlBuilder.addGroupBuilder()
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
    public ResponseEntity<String> addGroupMember(String adminSig, TencentIMAddGroupMemberParam param) {
        String requestAddress = TencentIMUrlBuilder.addGroupMemberBuilder()
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
    public ResponseEntity<String> removeGroupMember(String adminSig, TencentIMRemoveGroupMemberParam param) {
        String requestAddress = TencentIMUrlBuilder.removeGroupMemberBuilder()
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
    public <T> ResponseEntity<String> sendMessage(String adminSig, TencentIMSenderMessageParam<T> param) {
        String requestAddress = TencentIMUrlBuilder.senderMessageBuilder()
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
