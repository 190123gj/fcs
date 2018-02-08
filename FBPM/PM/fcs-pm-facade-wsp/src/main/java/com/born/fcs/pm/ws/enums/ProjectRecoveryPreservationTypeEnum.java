/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:01:58 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 保全措施枚举 from-追偿跟踪表 - 诉讼-诉前保全-保全措施
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryPreservationTypeEnum {
	
	/*** 查封 **/
	CLOSED_DOWN("CLOSED_DOWN", "查封"),
	
	/*** 冻结 **/
	FREEZE("FREEZE", "冻结"),
	
	/*** 扣押 **/
	DISTRAIN("DISTRAIN", "扣押"),
	
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
	private ProjectRecoveryPreservationTypeEnum(String code, String message) {
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
	 * @return ProjectRecoveryPreservationTypeEnum
	 */
	public static ProjectRecoveryPreservationTypeEnum getByCode(String code) {
		for (ProjectRecoveryPreservationTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryPreservationTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryPreservationTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryPreservationTypeEnum> list = new java.util.ArrayList<ProjectRecoveryPreservationTypeEnum>(
			values().length);
		for (ProjectRecoveryPreservationTypeEnum _enum : values()) {
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
		for (ProjectRecoveryPreservationTypeEnum _enum : values()) {
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
		ProjectRecoveryPreservationTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryPreservationTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
