package pers.xiaomuma.framework.thirdparty.wx.utils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class WX_AES_GCMUtil {

	static final int KEY_LENGTH_BYTE = 32;
	static final int TAG_LENGTH_BIT = 128;

	public static String decryptData(byte[] aesKey, byte[] associatedData, byte[] nonce, String ciphertext)
			throws GeneralSecurityException, IOException {
		if (aesKey.length != KEY_LENGTH_BYTE) {
			throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节");
		}
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
			GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);

			cipher.init(Cipher.DECRYPT_MODE, key, spec);
			cipher.updateAAD(associatedData);

			return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), "utf-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new IllegalStateException(e);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
