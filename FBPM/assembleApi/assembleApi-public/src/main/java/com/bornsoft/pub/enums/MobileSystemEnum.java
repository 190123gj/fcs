package com.bornsoft.pub.enums;

import com.bornsoft.utils.base.IBornEnum;

public enum MobileSystemEnum implements IBornEnum{
	
	Android("android","安卓"),
	Ios("ios","苹果"),
	;
	
	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	/**
	 * @param code
	 * @param message
	 */
	private MobileSystemEnum(String code, String message) {
		this.code = code;
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
