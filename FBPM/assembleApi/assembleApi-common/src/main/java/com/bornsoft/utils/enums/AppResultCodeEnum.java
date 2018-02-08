package com.bornsoft.utils.enums;

import com.bornsoft.utils.base.IBornEnum;

/**
 * @Description: App 结果枚举
 * @author taibai@yiji.com
 * @date 2016-8-30 下午5:08:53
 * @version V1.0
 */
public enum AppResultCodeEnum implements IBornEnum {
	SUCCESS("1", "成功"), 
	FAILED("0", "失败"),
	NOT_LOGIN("-1", "未登录"),
	;
	private String code;
	private String message;

	private AppResultCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}
}