/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午7:47:28 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 函件类型
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryLetterTypeEnum {
	/*** 承担担保责任确认函 **/
	GUARANTEE_OBLIGATION_SURE("GUARANTEE_OBLIGATION_SURE", "承担担保责任确认函"),
	
	/*** 债务责任转移通知函 **/
	DEBT_OBLIGATION_CHANGE_NOTICE("DEBT_OBLIGATION_CHANGE_NOTICE", "债务责任转移通知函"),
	
	/*** 追偿通知函 **/
	RECOVERY_NOTICE("RECOVERY_NOTICE", "追偿通知函"),
	
	/*** 承担担保责任通知函 **/
	GUARANTEE_OBLIGATION_NOTICE("GUARANTEE_OBLIGATION_NOTICE", "承担担保责任通知函"),
	
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
	private ProjectRecoveryLetterTypeEnum(String code, String message) {
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
	 * @return projectRecoveryLetterTypeEnum
	 */
	public static ProjectRecoveryLetterTypeEnum getByCode(String code) {
		for (ProjectRecoveryLetterTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<projectRecoveryLetterTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryLetterTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryLetterTypeEnum> list = new java.util.ArrayList<ProjectRecoveryLetterTypeEnum>(
			values().length);
		for (ProjectRecoveryLetterTypeEnum _enum : values()) {
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
		for (ProjectRecoveryLetterTypeEnum _enum : values()) {
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
		ProjectRecoveryLetterTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryLetterTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
