package pers.xiaomuma.framework.thirdparty.im.tencent.url;


import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/1640
 */
public class TencentIMModifyAccountUrlBuilder {

    private static final String ACCOUNT_MODIFY_URL = "%s/%s/profile/portrait_set?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMModifyAccountUrlBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMModifyAccountUrlBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMModifyAccountUrlBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMModifyAccountUrlBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMModifyAccountUrlBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(ACCOUNT_MODIFY_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
