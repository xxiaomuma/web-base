package pers.xiaomuma.base.thirdparty.wx.mp.request;

import org.springframework.http.ResponseEntity;

public interface WxRequest {

    /**
     * 获取access_token
     *  https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/access-token/auth.getAccessToken.html
     */
    ResponseEntity<String> fetchAccessToken();

    /**
     * 根据code获取微信会话
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
     */
    ResponseEntity<String> fetchCode2Session(String authorizeCode);

    /**
     * 获取微信用户基本信息
     * https://developers.weixin.qq.com/doc/offiaccount/User_Management/Get_users_basic_information_UnionID.html#UinonId
     */
    ResponseEntity<String> fetchUserInfo(String accessToken, String openId);

    /**
     * 发送模板消息
     * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html#5
     */
    ResponseEntity<String> sendTemplateMsg(String accessToken, String message);

    /**
     *  发送客服消息
     *  https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Service_Center_messages.html#7
     */
    ResponseEntity<String> sendCustomizedMsg(String accessToken, String message);

    /**
     * 获取小程序二维码
     */
    ResponseEntity<byte[]> fetchUnlimited(String accessToken, String params);

}
