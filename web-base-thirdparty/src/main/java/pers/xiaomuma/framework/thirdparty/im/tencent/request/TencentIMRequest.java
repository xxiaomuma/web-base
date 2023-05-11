package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.ResponseEntity;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMFriendAddParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMFriendRemoveParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMModifyAccountParam;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.TencentIMRegisterAccountParam;

public interface TencentIMRequest {

    String generateAdminSig();

    String generateUserSig(String userId);

    ResponseEntity<String> register(String adminSig, TencentIMRegisterAccountParam param);

    ResponseEntity<String> modifyAccount(String adminSig, TencentIMModifyAccountParam param);

    ResponseEntity<String> addFriend(String adminSig, TencentIMFriendAddParam param);

    ResponseEntity<String> removeFriend(String adminSig, TencentIMFriendRemoveParam param);
}
