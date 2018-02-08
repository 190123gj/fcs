/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:18:14 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 内部借款申请单 -- 付息方式
 * 
 * @author hjiajie
 * 
 */
public enum FormInnerLoanInterestTypeEnum {
	
	/*** 按月付息 */
	BY_MONTH("BY_MONTH", "按月付息"),
	/*** 按季付息 */
	BY_QUARTER("BY_QUARTER", "按季付息"),
	/*** 按半年付息 */
	BY_HALF_YEAR("BY_HALF_YEAR", "按半年付息"),
	/*** 按年付息 */
	BY_YEAR("BY_YEAR", "按年付息"),
	/*** 到期还本付息 */
	BY_TERM("BY_TERM", "到期还本付息"),
	
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
	private FormInnerLoanInterestTypeEnum(String code, String message) {
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
	 * @return FormInnerLoanInterestTypeEnum
	 */
	public static FormInnerLoanInterestTypeEnum getByCode(String code) {
		for (FormInnerLoanInterestTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormInnerLoanInterestTypeEnum>
	 */
	public static java.util.List<FormInnerLoanInterestTypeEnum> getAllEnum() {
		java.util.List<FormInnerLoanInterestTypeEnum> list = new java.util.ArrayList<FormInnerLoanInterestTypeEnum>(
			values().length);
		for (FormInnerLoanInterestTypeEnum _enum : values()) {
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
		for (FormInnerLoanInterestTypeEnum _enum : values()) {
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
		FormInnerLoanInterestTypeEnum _enum = getByCode(code);
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
	public static String getCode(FormInnerLoanInterestTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
