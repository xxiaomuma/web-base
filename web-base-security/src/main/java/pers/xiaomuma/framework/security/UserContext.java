package pers.xiaomuma.framework.security;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pers.xiaomuma.framework.security.user.CustomUser;
import java.util.Collection;
import java.util.Optional;

public class UserContext {

    private static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    public static boolean isAuthentication() {
        Authentication authentication = getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication auth = getAuthentication();
        return auth.getAuthorities();
    }

    public static Integer getUserId() {
        return Optional.ofNullable(getAuthentication())
                .filter(authentication -> authentication.getPrincipal() instanceof CustomUser)
                .map(auth -> (CustomUser) auth.getPrincipal())
                .map(CustomUser::getUserId).orElse(null);
    }
}
