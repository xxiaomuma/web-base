package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.thirdparty.im.tencent.TencentIMProperties;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMRegisterAccountParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.url.TencentIMUrlBuilder;
import pers.xiaomuma.framework.thirdparty.utils.GenerateUserSigUtils;



public class TencentIMRequestManager implements TencentIMRequest {

    private final RestTemplate restTemplate;
    private final TencentIMProperties properties;

    public TencentIMRequestManager(TencentIMProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
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
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange(requestAddress, HttpMethod.POST, httpEntity, String.class);
    }

}
