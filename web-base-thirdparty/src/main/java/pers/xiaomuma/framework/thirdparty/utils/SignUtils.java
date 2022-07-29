package pers.xiaomuma.framework.thirdparty.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Map;
import java.util.SortedMap;

public class SignUtils {

    public static String rsaSHA256Sign(byte[] message, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initSign(privateKey);
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException("WxPay: 获取sign失败", e);
        }
    }

    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = (str).getBytes(StandardCharsets.UTF_8);
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("md5加密失败", e);
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    public static String equalsSplicing(SortedMap<String, Object> signParamMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : signParamMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue().toString());
            sb.append("&");
        }
        return sb.toString();
    }

}
