package pers.xiaomuma.base.thirdparty.wx.utils;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;


public class SignUtils {

    public static String rsaSHA256Sign(byte[] message, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initSign(privateKey);
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException var3) {
            throw new RuntimeException("WxPay: 获取sign失败", var3);
        }
    }

    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = str.getBytes(StandardCharsets.UTF_8);
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException var4) {
            throw new RuntimeException("md5加密失败", var4);
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        byte[] var4 = byteArray;
        int var5 = byteArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            byte b = var4[var6];
            resultCharArray[index++] = hexDigits[b >>> 4 & 15];
            resultCharArray[index++] = hexDigits[b & 15];
        }

        return new String(resultCharArray);
    }

    public static String equalsSplicing(SortedMap<String, Object> signParamMap) {
        StringBuilder sb = new StringBuilder();
        Iterator var2 = signParamMap.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var2.next();
            if (entry.getValue() != null) {
                sb.append((String)entry.getKey()).append("=").append(entry.getValue().toString());
                sb.append("&");
            }
        }

        return sb.toString();
    }
}
