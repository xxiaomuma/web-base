package pers.xiaomuma.framework.thirdparty.im.tencent.url;

import cn.hutool.core.date.DatePattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/1608
 */
public class TencentIMImportAccountUrlBuilder {

    private static final String ACCOUNT_IMPORT_URL = "%s/%s/im_open_login_svc/account_import?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMImportAccountUrlBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMImportAccountUrlBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMImportAccountUrlBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMImportAccountUrlBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMImportAccountUrlBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(ACCOUNT_IMPORT_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
