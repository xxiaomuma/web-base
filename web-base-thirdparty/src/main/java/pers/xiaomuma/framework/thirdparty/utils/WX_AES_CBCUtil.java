package pers.xiaomuma.framework.thirdparty.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * 解密微信小程序加密的内容
 * 1.对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
 * 2.对称解密的目标密文为 Base64_Decode(encryptedData)。
 * 3.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
 * 4.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
 * 解密相关文档地址：
 * https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject
 */
public class WX_AES_CBCUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        String data = "iF+TYiAwNG/liR5yrV0urC94ZKO1oTygWBOYi+1h/M3Qg42cqBpKFCw3yAFVq1JAtwvqZRnhz5lzmYwiV9kY8mu6xBsxz6LdXsZECX6RbkHttEK++RjkKpIWsEKaS0czmC+02DWpdPCpOzVkM7sqEGemLHVdMnXBq3HLn+R4owqLSkXli0BY7t5t+LCdyXUZ3Kkp0wvXifhUEGiFbDyecA==";
        String iv = "AfiiDBoxuH4U8NiLBWDySw==";
        String sessionKey = "AfiiDBoxuH4U8NiLBWDySw==";
        String result = decryptData(
                data, iv, sessionKey
        );
        System.out.println("result = " + result);
    }

    public static String decryptData(String encryptDataB64, String ivB64, String sessionKeyB64) {
        byte[] bytes =  decryptOfDiyIV(
                Base64.decode(encryptDataB64),
                Base64.decode(sessionKeyB64),
                Base64.decode(ivB64)
        );
        return new String(bytes);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs)  {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 转化成JAVA的密钥格式
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        byte[] encryptedText;
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, generateIV(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("WX CBC解密失败", e);
        }
        return encryptedText;
    }

    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_ALGORITHM);
        params.init(new IvParameterSpec(iv));
        return params;
    }

}