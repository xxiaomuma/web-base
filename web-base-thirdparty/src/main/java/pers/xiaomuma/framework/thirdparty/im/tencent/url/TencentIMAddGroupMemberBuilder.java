package pers.xiaomuma.framework.thirdparty.im.tencent.url;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/1621
 */
public class TencentIMAddGroupMemberBuilder {

    private static final String GROUP_ADD_URL = "%s/%s/group_open_http_svc/add_group_member?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMAddGroupMemberBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMAddGroupMemberBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMAddGroupMemberBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMAddGroupMemberBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMAddGroupMemberBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(GROUP_ADD_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
