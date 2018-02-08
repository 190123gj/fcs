package com.born.fcs.pm.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

public class ThreeDes {
	
	// 密钥的默认键  
	private static String defaultKey = "key";
	
	// 加密密码功能对象  
	private Cipher encryptCipher = null;
	
	// 解密密码功能对象  
	private Cipher decryptCipher = null;
	
	static {
		
	}
	
	/**
	 * 默认构造函数
	 */
	public ThreeDes() {
		this(defaultKey);
	}
	
	/**
	 * 使用指定的密钥键
	 * @param strKey 密钥键
	 */
	public ThreeDes(String strKey) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());
		try {
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 加密指定的字符串
	 * @param str 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public String encrypt(String str) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		// 加密后的字符串  
		String strEncrypt = "";
		try {
			byteMing = str.getBytes("UTF8");
			byteMi = encryptCipher.doFinal(byteMing);
			strEncrypt = Base64.encode(byteMi);
		} catch (Exception e) {
		} finally {
			byteMing = null;
			byteMi = null;
		}
		
		return strEncrypt;
	}
	
	/**
	 * 根据指定的加密字符串，获取解密后的明文
	 * @param strMi 加密的字符串
	 * @return 返回解密后的明文
	 */
	public String decrypt(String strMi) {
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = Base64.decode(strMi);
			byteMing = decryptCipher.doFinal(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
		} finally {
			byteMing = null;
			byteMi = null;
		}
		
		return strMing;
	}
	
	/**
	 * 根据指定的字节数组，构造一个密钥
	 * @param arrBTmp 字节数组
	 * @return 返回生成后的密钥
	 */
	private Key getKey(byte[] array) {
		// 创建一个空的8位字节数组  
		byte[] arrayTemp = new byte[8];
		int length = array.length;
		
		// 长度是否大于8  
		if (length > 8) {
			System.arraycopy(array, 0, arrayTemp, 0, 8);
		} else {
			System.arraycopy(array, 0, arrayTemp, 0, length);
		}
		// 生成密钥  
		Key key = new javax.crypto.spec.SecretKeySpec(arrayTemp, "DES");
		return key;
	}
}