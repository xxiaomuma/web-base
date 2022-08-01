package pers.xiaomuma.framework.security.authentication.jwt;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


public class JwtTokenGenerator {

    private KeyPair keyPair;
    private JwtProperties properties;

    public JwtTokenGenerator(JwtProperties properties) {
        this.properties = properties;
        this.keyPair = this.createKeyPair(properties.getKeyLocation(), properties.getKeyAlias(), properties.getKeyPassword());
    }

    private synchronized KeyPair createKeyPair(String keyLocation, String keyAlias, String keyPassword) {
        ClassPathResource resource = new ClassPathResource(keyLocation);
        char[] pem = keyPassword.toCharArray();
        try {
            KeyStore store = KeyStore.getInstance("jks");
            store.load(resource.getInputStream(), pem);
            RSAPrivateCrtKey key = (RSAPrivateCrtKey)store.getKey(keyAlias, pem);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            return new KeyPair(publicKey, key);
        } catch (Exception var) {
            throw new IllegalStateException("cannot load keys from store: " + resource, var);
        }
    }

    public Map<String, String> generatePassport(String aud, Collection<GrantedAuthority> authorities) {
        return this.generatePassport(aud, null, authorities);
    }

    public Map<String, String> generatePassport(String aud, Map<String, String> additional, Collection<GrantedAuthority> authorities) {
        LinkedHashMap<String, String> tokenMap = new LinkedHashMap<>(2);
        LocalDateTime now = LocalDateTime.now();
        tokenMap.put("create_time", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        int accessTokenValidSeconds = this.properties.getExpireSeconds();
        String accessToken = this.generateAccessToken(aud, authorities, null);
        tokenMap.put("token", accessToken);
        tokenMap.put("expire_time", now.plusSeconds(accessTokenValidSeconds).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return tokenMap;
    }

    public String generateAccessToken(String aud, Collection<GrantedAuthority> authorities, Map<String, String> additional) {
        LocalDateTime time = LocalDateTime.now();
        String claims = (new JwtPayloadBuilder())
                .aud(aud)
                .grantType("access_token")
                .iss(this.properties.getJwtIss())
                .sub("")
                .iat(time)
                .exp(time.plusSeconds(this.properties.getExpireSeconds()))
                .authorities(authorities)
                .additional(Optional.ofNullable(additional).orElse(Maps.newHashMap()))
                .build();
        return this.encode(claims);
    }

    public Map<String, String> verify(String jwtToken) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) this.keyPair.getPublic();
        RsaVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt;
        try {
            jwt = JwtHelper.decodeAndVerify(jwtToken, rsaVerifier);
        } catch (Exception var8) {
            throw new BadCredentialsException(var8.getMessage());
        }
        Map<String, String> claimsMap =  JsonUtils.json2Map(jwt.getClaims(), String.class, String.class);
        String iss = claimsMap.get("iss");
        if (StrUtil.equals(iss, this.properties.getJwtIss())) {
            LocalDateTime exp = LocalDateTime.parse(claimsMap.get("exp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            if (exp.isBefore(LocalDateTime.now())) {
                throw new CredentialsExpiredException("token expired");
            } else {
                return claimsMap;
            }
        }
        throw new BadCredentialsException("invalid and illegal iss");
    }

   /* public String generateRefreshToken(String aud, Collection<GrantedAuthority> authorities, Map<String, String> additional) {
        LocalDateTime time = LocalDateTime.now();
        String claims = (new JwtPayloadBuilder())
                .aud(aud)
                .grantType("refresh_token")
                .iss(this.properties.getJwtIss())
                .sub("")
                .iat(time)
                .exp(time.plusSeconds(this.properties.getRefreshTokenValidSeconds()))
                .authorities(authorities)
                .additional(Optional.ofNullable(additional).orElse(Maps.newHashMap()))
                .build();
        return this.encode(claims);
    }*/

    private String encode(String claims) {
        RSAPrivateKey privateKey = (RSAPrivateKey)this.keyPair.getPrivate();
        RsaSigner signer = new RsaSigner(privateKey);
        return JwtHelper.encode(claims, signer).getEncoded();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public JwtProperties getProperties() {
        return properties;
    }

    public void setProperties(JwtProperties properties) {
        this.properties = properties;
    }
}
