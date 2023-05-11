package pers.xiaomuma.framework.thirdparty.im.tencent.url;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/1643
 */
public class TencentIMFriendAddBuilder {

    private static final String FRIEND_ADD_URL = "%s/%s/sns/friend_add?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMFriendAddBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMFriendAddBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMFriendAddBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMFriendAddBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMFriendAddBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(FRIEND_ADD_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
