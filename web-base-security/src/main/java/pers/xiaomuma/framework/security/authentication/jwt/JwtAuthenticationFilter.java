package pers.xiaomuma.framework.security.authentication.jwt;


import cn.hutool.core.map.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.xiaomuma.framework.security.user.CustomUser;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthenticationFilter(JwtTokenGenerator jwtTokenGenerator) {
        Objects.requireNonNull(jwtTokenGenerator, "JwtTokenGenerator must not be null");
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.toLowerCase().startsWith("bearer ")) {
            String token = this.extractTokenFromHeader(header);
            Authentication authentication = handleAuthentication(token, request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String extractTokenFromHeader(String header) {
        return header.substring(7);
    }

    private Authentication handleAuthentication(String jwtToken, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Assert.hasText(jwtToken, "jwt token must not be bank");
        Map<String, String> data = this.jwtTokenGenerator.verify(jwtToken);
        if (MapUtil.isEmpty(data)) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("token [{}]  is  invalid", jwtToken);
            }
            throw new BadCredentialsException("token is invalid");
        }
        if (this.jwtTokenGenerator.getProperties().isAutoRefreshToken()) {
            autoRefreshToken(data, response);
        }
        String aud = data.get("aud");
        Collection<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(data.get("authorities"));
        User user = new User("[Blank]", "[PROTECTED]", authorities);
        CustomUser customUser = new CustomUser(Integer.parseInt(aud), user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
        authenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
        return authenticationToken;
    }

    private void autoRefreshToken(Map<String, String> data, HttpServletResponse response) {
        LocalDateTime exp = LocalDateTime.parse(data.get("exp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Duration duration = Duration.between(LocalDateTime.now(), exp);
        long minutes = duration.toMinutes();
        if (minutes <= 5) {
            String aud = data.get("aud");
            Collection<GrantedAuthority> authorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(data.get("authorities"));
            String jwtToken = this.jwtTokenGenerator.generatePassport(aud, authorities).get("token");
            response.addHeader("Authorization", jwtToken);
        }
    }
}