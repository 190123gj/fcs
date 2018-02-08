/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午11:38:30 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 保全措施类型
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryLitigationTypeEnum {
	
	/*** 诉前保全 **/
	LITIGATION_BEFORE("LITIGATION_BEFORE", "诉前保全"),
	/*** 诉讼保全 **/
	LITIGATION_IN("LITIGATION_IN", "诉讼保全"),
	
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
	private ProjectRecoveryLitigationTypeEnum(String code, String message) {
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
	 * @return ProjectRecoveryLitigationTypeEnum
	 */
	public static ProjectRecoveryLitigationTypeEnum getByCode(String code) {
		for (ProjectRecoveryLitigationTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryLitigationTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryLitigationTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryLitigationTypeEnum> list = new java.util.ArrayList<ProjectRecoveryLitigationTypeEnum>(
			values().length);
		for (ProjectRecoveryLitigationTypeEnum _enum : values()) {
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
		for (ProjectRecoveryLitigationTypeEnum _enum : values()) {
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
		ProjectRecoveryLitigationTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryLitigationTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
