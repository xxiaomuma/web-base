package pers.xiaomuma.framework.thirdparty.utils;

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

	private final static Logger LOGGER = LoggerFactory.getLogger(WxPayUtils.class);

	public static String genNonceStr() {
		return RandomUtil.randomString(32);
	}

	public static PrivateKey getPrivateKey(String filename) {
		String content = getPrivateContent(filename);
		try {
			String privateKey = content
					.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\\s+", "");
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("当前Java环境不支持RSA", e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("无效的密钥格式");
		}
	}

	public static X509Certificate getCertificate(String filename) throws IOException {
		InputStream inputStream = WxPayUtils.class.getClassLoader()
				.getResourceAsStream(filename);
		if (Objects.isNull(inputStream)) {
			LOGGER.info("获取{}失败", filename);
			return null;
		}
		try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
			cert.checkValidity();
			return cert;
		} catch (CertificateExpiredException e) {
			throw new RuntimeException("证书已过期", e);
		} catch (CertificateNotYetValidException e) {
			throw new RuntimeException("证书尚未生效", e);
		} catch (CertificateException e) {
			throw new RuntimeException("无效的证书文件", e);
		}
	}

	private static String getPrivateContent(String filename) {
		InputStream inputStream = WxPayUtils.class.getClassLoader()
				.getResourceAsStream(filename);
		if (Objects.isNull(inputStream)) {
			LOGGER.info("获取{}失败", filename);
			return "";
		}
		StringBuilder sbf = new StringBuilder();
		try {
			int length;
			byte[] bytes = new byte[1024];
			while((length = inputStream.read(bytes)) != -1){
				sbf.append(new String(bytes, 0, length));
			}
		} catch (IOException e) {
			throw new RuntimeException("读取文件失败", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e1) {
				LOGGER.error("关闭 inputStream 失败, {}", e1.getMessage());
			}
		}
		return sbf.toString();
	}

}
