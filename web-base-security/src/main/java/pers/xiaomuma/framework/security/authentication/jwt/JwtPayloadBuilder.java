package pers.xiaomuma.framework.security.authentication.jwt;

import cn.hutool.core.util.IdUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.nimbusds.oauth2.sdk.util.MapUtils;
import org.springframework.security.core.GrantedAuthority;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

public class JwtPayloadBuilder {

    private Map<String, String> claims = Maps.newHashMap();
    private String iss;
    private String sub;
    private String aud;
    private LocalDateTime exp;
    private LocalDateTime iat;
    private String grantType;
    private Map<String, String> additional;
    private Collection<GrantedAuthority> authorities;

    public JwtPayloadBuilder() {

    }

    public JwtPayloadBuilder iss(String iss) {
        this.iss = iss;
        return this;
    }

    public JwtPayloadBuilder sub(String sub) {
        this.sub = sub;
        return this;
    }

    public JwtPayloadBuilder aud(String aud) {
        this.aud = aud;
        return this;
    }

    public JwtPayloadBuilder exp(LocalDateTime exp) {
        this.exp = exp;
        return this;
    }

    public JwtPayloadBuilder iat(LocalDateTime iat) {
        this.iat = iat;
        return this;
    }

    public JwtPayloadBuilder grantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public JwtPayloadBuilder authorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String build() {
        this.claims.put("iss", this.iss);
        this.claims.put("sub", this.sub);
        this.claims.put("aud", this.aud);
        this.claims.put("exp", this.exp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.claims.put("iat", this.iat.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.claims.put("jti", IdUtil.simpleUUID());
        if (MapUtils.isNotEmpty(this.additional)) {
            this.claims.putAll(this.additional);
        }
        this.claims.put("authorities", Joiner.on(",").join(this.authorities));
        this.claims.put("grant_type", this.grantType);
        return JsonUtils.object2Json(this.claims);
    }


}
