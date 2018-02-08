package com.bornsoft.utils.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.bornsoft.utils.exception.BornApiException;

public class AesUtil {
	
	static final String algorithmStr = "AES/ECB/PKCS5Padding";
	
	/*  
	 * AES/CBC/NoPadding 要求 
	 * 密钥必须是16位的；Initialization vector (IV) 必须是16位 
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常： 
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes 
	 *  
	 *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整 
	 *   
	 *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n， 
	 *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]， 
	 *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1). 
	 */
	public static String encrypt(String content, String password) {
		try {
			byte[] keyStr = getKey(password);
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(algorithmStr);
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return Base64Util.encode(result);
			//            return Base64.encodeToString(result, Base64.DEFAULT);     
		} catch (Exception e) {
			e.printStackTrace();
			throw new BornApiException(e);
		} 
	}
	
	public static String decrypt(String content, String password) {
		try {
			byte[] keyStr = getKey(password);
			SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
			Cipher cipher = Cipher.getInstance(algorithmStr);
			byte[] byteContent = content.getBytes("utf-8");
			byte[] decodeByte = Base64Util.decode(byteContent);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(decodeByte);
			return new String(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BornApiException(e);
		} 
	}
	
	private static byte[] getKey(String password) {
		byte[] rByte = null;
		if (password != null) {
			rByte = password.getBytes();
		} else {
			rByte = new byte[24];
		}
		return rByte;
	}
	
}
