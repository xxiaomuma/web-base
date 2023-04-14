package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.ResponseEntity;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMRegisterAccountParam;

public interface TencentIMRequest {

    String generateAdminSig();

    String generateUserSig(String userId);

    ResponseEntity<String> register(String adminSig, TencentIMRegisterAccountParam param);
}
