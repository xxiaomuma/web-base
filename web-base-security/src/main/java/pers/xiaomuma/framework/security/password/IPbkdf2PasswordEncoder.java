package pers.xiaomuma.framework.security.password;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.xiaomuma.framework.encrypt.PasswordHash;

public class IPbkdf2PasswordEncoder implements PasswordEncoder {

    @Override
    @SneakyThrows
    public String encode(CharSequence rawPassword) {
        return PasswordHash.createHash(rawPassword.toString());
    }

    @Override
    @SneakyThrows
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return PasswordHash.validatePassword(rawPassword.toString(), encodedPassword);
    }
}
