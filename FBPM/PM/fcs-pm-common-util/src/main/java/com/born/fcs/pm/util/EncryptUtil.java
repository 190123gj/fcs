package com.born.fcs.pm.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/***
 * BPM的密码生成
 * @author wuzj
 */
public class EncryptUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	
	public static String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(encryptSha256("111111"));
	}
}
