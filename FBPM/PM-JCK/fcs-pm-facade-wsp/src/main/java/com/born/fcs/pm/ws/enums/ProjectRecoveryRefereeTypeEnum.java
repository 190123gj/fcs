/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:02:54 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 裁判类型 from --追偿跟踪表 - 诉讼-裁判
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryRefereeTypeEnum {
	
	/*** 开庭--裁判 **/
	OPENING("OPENING", "开庭--裁判"),
	/*** 二审上诉--裁判 **/
	SECOND_APPEAL("SECOND_APPEAL", "二审上诉--裁判"),
	/*** 再审程序（一审）--裁判 **/
	ADJOURNED_PROCEDURE_FIRST("ADJOURNED_PROCEDURE_FIRST", "再审程序（一审）--裁判"),
	/*** 再审程序（二审）--裁判 **/
	ADJOURNED_PROCEDURE_SECOND("ADJOURNED_PROCEDURE_SECOND", "再审程序（二审）--裁判"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ProjectRecoveryRefereeTypeEnum(String code, String message) {
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
	 * @return ProjectRecoveryDescribeTypeEnum
	 */
	public static ProjectRecoveryRefereeTypeEnum getByCode(String code) {
		for (ProjectRecoveryRefereeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryDescribeTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryRefereeTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryRefereeTypeEnum> list = new java.util.ArrayList<ProjectRecoveryRefereeTypeEnum>(
			values().length);
		for (ProjectRecoveryRefereeTypeEnum _enum : values()) {
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
		for (ProjectRecoveryRefereeTypeEnum _enum : values()) {
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
		ProjectRecoveryRefereeTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryRefereeTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
