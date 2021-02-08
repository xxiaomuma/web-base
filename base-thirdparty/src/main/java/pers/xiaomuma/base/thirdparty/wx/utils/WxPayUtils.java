package pers.xiaomuma.base.thirdparty.wx.utils;


import cn.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

public class WxPayUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxPayUtils.class);

    public WxPayUtils() {
    }

    public static String genNonceStr() {
        return RandomUtil.randomString(32);
    }

    public static PrivateKey getPrivateKey(String filename) {
        String content = getPrivateContent(filename);
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException var4) {
            throw new RuntimeException("当前Java环境不支持RSA", var4);
        } catch (InvalidKeySpecException var5) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

    public static X509Certificate getCertificate(String filename) throws IOException {
        InputStream inputStream = WxPayUtils.class.getClassLoader().getResourceAsStream(filename);
        if (Objects.isNull(inputStream)) {
            LOGGER.info("获取{}失败", filename);
            return null;
        } else {
            try {
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                Throwable var3 = null;

                X509Certificate var6;
                try {
                    CertificateFactory cf = CertificateFactory.getInstance("X509");
                    X509Certificate cert = (X509Certificate)cf.generateCertificate(bis);
                    cert.checkValidity();
                    var6 = cert;
                } catch (Throwable var18) {
                    var3 = var18;
                    throw var18;
                } finally {
                    if (bis != null) {
                        if (var3 != null) {
                            try {
                                bis.close();
                            } catch (Throwable var17) {
                                var3.addSuppressed(var17);
                            }
                        } else {
                            bis.close();
                        }
                    }
                }
                return var6;
            } catch (CertificateExpiredException var20) {
                throw new RuntimeException("证书已过期", var20);
            } catch (CertificateNotYetValidException var21) {
                throw new RuntimeException("证书尚未生效", var21);
            } catch (CertificateException var22) {
                throw new RuntimeException("无效的证书文件", var22);
            }
        }
    }

    private static String getPrivateContent(String filename) {
        InputStream inputStream = WxPayUtils.class.getClassLoader().getResourceAsStream(filename);
        if (Objects.isNull(inputStream)) {
            LOGGER.info("获取{}失败", filename);
            return "";
        } else {
            StringBuilder sbf = new StringBuilder();

            try {
                byte[] bytes = new byte[1024];

                int length;
                while((length = inputStream.read(bytes)) != -1) {
                    sbf.append(new String(bytes, 0, length));
                }
            } catch (IOException var12) {
                throw new RuntimeException("读取文件失败", var12);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException var11) {
                    LOGGER.error("关闭 inputStream 失败, {}", var11.getMessage());
                }

            }

            return sbf.toString();
        }
    }

    public static void main(String[] args) {
        String signature = "nK89QcuBfc2pUjd95iEpSBkcyGpKLL9ESyBPSBBjVFnqqnTAfMCM36tjUz+Q2wbWDe1j3k0iRskZI6Zljh7Fl0IRZseMOAVBvzue2wdnvOy8fCTrKPvs/YeXfteyKFWUaiij+a9YeOBpFDuf5lKRxMhfAWvlFN3Wxt+nWH48tNgLAi9yJvGQQQahK9ynOVjA3/y6clSA2/2TeUj2Y+qJeb/RMc4yStQZAOVq/qN4tPLQiCRyiZkdVjVgXG0+oA9+jY677AIv7vf8d3NHOs7RuIektG12Me5+IH2a4Tf8tUJVtgF6elgQa9EuMvVQ4+5XDdlYgQ54i7E4fEl01qMAXg==";
        System.out.println(signature.length());
    }
}

