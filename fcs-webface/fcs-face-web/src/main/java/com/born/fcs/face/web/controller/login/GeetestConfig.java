package com.born.fcs.face.web.controller.login;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "94f111be5cdd1017d44c117a374412d8";
	private static final String geetest_key = "a79a69644f7706b0503cf302f9998e92";

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}

}
