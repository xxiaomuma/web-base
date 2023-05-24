package pers.xiaomuma.framework.thirdparty.im.tencent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.serialize.JsonUtils;
import pers.xiaomuma.framework.thirdparty.im.IMResult;
import pers.xiaomuma.framework.thirdparty.im.tencent.param.*;
import pers.xiaomuma.framework.thirdparty.im.tencent.request.TencentIMRequest;
import pers.xiaomuma.framework.thirdparty.im.tencent.request.TencentIMRequestManager;
import pers.xiaomuma.framework.thirdparty.im.tencent.response.TencentIMResponse;


public class TencentIMAPI {

    private final Logger logger = LoggerFactory.getLogger(TencentIMAPI.class);
    private final TencentIMRequest request;

    public TencentIMAPI(TencentIMProperties properties, RestTemplate restTemplate) {
        this.request = new TencentIMRequestManager(properties, restTemplate);
    }

    public IMResult<String> generateAdminSig() {
        String userSig = request.generateAdminSig();
        return IMResult.success(userSig);
    }

    public IMResult<String> generateUserSig(String userId) {
        String userSig = request.generateUserSig(userId);
        return IMResult.success(userSig);
    }

    public IMResult<Void> register(String adminSig, TencentIMRegisterAccountParam param) {
        ResponseEntity<String> response = request.register(adminSig, param);
        logger.debug("Tencent IM注册响应, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM注册失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> modifyAccount(String adminSig, TencentIMModifyAccountParam param) {
        ResponseEntity<String> response = request.modifyAccount(adminSig, param);
        logger.debug("Tencent IM修改用户资料失败, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM修改用户资料失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> addFriend(String adminSig, TencentIMAddFriendParam param) {
        ResponseEntity<String> response = request.addFriend(adminSig, param);
        logger.debug("Tencent IM强制添加双向好友, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM强制添加双向好友失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> removeFriend(String adminSig, TencentIMRemoveFriendParam param) {
        ResponseEntity<String> response = request.removeFriend(adminSig, param);
        logger.debug("Tencent IM删除双向好友, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM删除双向好友失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> addGroup(String adminSig, TencentIMAddGroupParam param) {
        ResponseEntity<String> response = request.addGroup(adminSig, param);
        logger.debug("Tencent IM创建群聊, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM创建群聊失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> addGroupMember(String adminSig, TencentIMAddGroupMemberParam param) {
        ResponseEntity<String> response = request.addGroupMember(adminSig, param);
        logger.debug("Tencent IM添加群成员, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM添加群成员失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }

    public IMResult<Void> removeGroupMember(String adminSig, TencentIMRemoveGroupMemberParam param) {
        ResponseEntity<String> response = request.removeGroupMember(adminSig, param);
        logger.debug("Tencent IM移除群成员, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM移除群成员失败, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }


    public <T> IMResult<Void> sendMessage(String adminSig, TencentIMSenderMessageParam<T> param) {
        ResponseEntity<String> response = request.sendMessage(adminSig, param);
        logger.debug("Tencent IM发送消息, {}", response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()) {
            return IMResult.error("Tencent IM发送消息, response:" + response.getBody());
        }
        TencentIMResponse imResponse = JsonUtils.json2Object(response.getBody(), TencentIMResponse.class);
        if (imResponse.isSuccess()) {
            return IMResult.success();
        }
        return IMResult.error(imResponse.getErrorInfo());
    }
}
