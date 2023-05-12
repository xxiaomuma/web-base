package pers.xiaomuma.framework.thirdparty.im.tencent.url;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/1643
 */
public class TencentIMAddFriendBuilder {

    private static final String FRIEND_ADD_URL = "%s/%s/sns/friend_add?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMAddFriendBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMAddFriendBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMAddFriendBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMAddFriendBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMAddFriendBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(FRIEND_ADD_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
