package pers.xiaomuma.framework.security.login.email;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;



public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    private String email;
    private String code;

    public EmailAuthenticationToken(String email, String code) {
        super(null);
        this.email = email;
        this.code = code;
    }

    public EmailAuthenticationToken(String email, String code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.code = code;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }
}
