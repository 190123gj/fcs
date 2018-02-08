/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午11:03:28 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public enum ChangeSourceTypeEnum {
	
	/** 差旅费 */
	TRAVEL("TRAVEL", "差旅费"),
	
	/** 费用支付 */
	EXPENSE("EXPENSE", "费用支付"),
	
	/** 转账 */
	TRANSFER("TRANSFER", "转账"),
	
	/** 劳资支付 */
	LABOUR_CAPITAL("LABOUR_CAPITAL", "劳资支付"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ChangeSourceTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return AccountStatusEnum
	 */
	public static ChangeSourceTypeEnum getByCode(String code) {
		for (ChangeSourceTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AccountStatusEnum>
	 */
	public static java.util.List<ChangeSourceTypeEnum> getAllEnum() {
		java.util.List<ChangeSourceTypeEnum> list = new java.util.ArrayList<ChangeSourceTypeEnum>(
			values().length);
		for (ChangeSourceTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (ChangeSourceTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		ChangeSourceTypeEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(ChangeSourceTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
