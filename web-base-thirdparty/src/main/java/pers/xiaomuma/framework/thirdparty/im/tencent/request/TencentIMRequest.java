package pers.xiaomuma.framework.thirdparty.im.tencent.request;

import org.springframework.http.ResponseEntity;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.*;

public interface TencentIMRequest {

    String generateAdminSig();

    String generateUserSig(String userId);

    ResponseEntity<String> register(String adminSig, TencentIMRegisterAccountParam param);

    ResponseEntity<String> modifyAccount(String adminSig, TencentIMModifyAccountParam param);

    ResponseEntity<String> addFriend(String adminSig, TencentIMAddFriendParam param);

    ResponseEntity<String> removeFriend(String adminSig, TencentIMRemoveFriendParam param);

    ResponseEntity<String> addGroup(String adminSig, TencentIMAddGroupParam param);

    ResponseEntity<String> addGroupMember(String adminSig, TencentIMAddGroupMemberParam param);

    ResponseEntity<String> removeGroupMember(String adminSig, TencentIMRemoveGroupMemberParam param);

    <T> ResponseEntity<String> sendMessage(String adminSig, TencentIMSenderMessageParam<T> param);
}
