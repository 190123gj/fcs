/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:49:44 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 
 * 周期
 * @author lzb
 * 
 */
public enum PeriodEnum {
	
	YEAR("YEAR", "年度", 3),
	
	MONTH("MONTH", "月度", 1),
	
	SEASON("SEASON", "季度", 2),
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 优先级 */
	private final int priority;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private PeriodEnum(String code, String message, int priority) {
		this.code = code;
		this.message = message;
		this.priority = priority;
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
	
	public int priority() {
		return priority;
	}
	
	public int getPriority() {
		return priority;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return VoucherStatusEnum
	 */
	public static PeriodEnum getByCode(String code) {
		for (PeriodEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<VoucherStatusEnum>
	 */
	public static java.util.List<PeriodEnum> getAllEnum() {
		java.util.List<PeriodEnum> list = new java.util.ArrayList<PeriodEnum>(
			values().length);
		for (PeriodEnum _enum : values()) {
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
		for (PeriodEnum _enum : values()) {
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
		PeriodEnum _enum = getByCode(code);
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
	public static String getCode(PeriodEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
