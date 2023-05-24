package pers.xiaomuma.framework.thirdparty.im.tencent.url;


import cn.hutool.core.date.DatePattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://cloud.tencent.com/document/product/269/2282
 */
public class TencentIMsSenderMessageBuilder {

    private static final String SEND_MESSAGE_URL = "%s/%s/openim/sendmsg?sdkappid=%d&identifier=%s&usersig=%s&random=%s&contenttype=json";
    private String domain;
    private String version;
    private Long sdkappid;
    private String identifier;
    private String usersig;

    public TencentIMsSenderMessageBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public TencentIMsSenderMessageBuilder version(String version) {
        this.version = version;
        return this;
    }

    public TencentIMsSenderMessageBuilder sdkappid(Long sdkappid) {
        this.sdkappid = sdkappid;
        return this;
    }

    public TencentIMsSenderMessageBuilder identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TencentIMsSenderMessageBuilder usersig(String usersig) {
        this.usersig = usersig;
        return this;
    }

    public String build() {
        String random = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN));
        return String.format(SEND_MESSAGE_URL, this.domain, this.version, sdkappid, identifier, usersig, random);
    }
}
