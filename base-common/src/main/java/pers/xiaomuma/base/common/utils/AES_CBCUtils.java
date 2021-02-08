package pers.xiaomuma.base.common.utils;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

public class AES_CBCUtils {

    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String decryptData(String encryptDataB64, String ivB64, String sessionKeyB64) {
        byte[] bytes = decryptOfDiyIV(Base64.decode(encryptDataB64), Base64.decode(sessionKeyB64), Base64.decode(ivB64));
        return new String(bytes);
    }

    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        int base = 16;
        byte[] encryptedText;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            encryptedText = new byte[groups * base];
            Arrays.fill(encryptedText, (byte) 0);
            System.arraycopy(keyBytes, 0, encryptedText, 0, keyBytes.length);
            keyBytes = encryptedText;
        }

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(2, key, generateIV(ivs));
            encryptedText = cipher.doFinal(encryptedData);
            return encryptedText;
        } catch (Exception var7) {
            throw new RuntimeException("WX CBC解密失败", var7);
        }
    }

    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

}
