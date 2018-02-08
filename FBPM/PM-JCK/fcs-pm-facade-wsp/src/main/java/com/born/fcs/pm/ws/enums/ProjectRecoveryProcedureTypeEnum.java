/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:15:08 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 再审程序【一审/二审】 from --追偿跟踪表 - 诉讼-再审程序
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryProcedureTypeEnum {
	
	/*** 一审 **/
	FIRST("FIRST", "一审"),
	/*** 二审 **/
	SECOND("SECOND", "二审"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ProjectRecoveryProcedureTypeEnum(String code, String message) {
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
	 * @return ProjectRecoveryProcedureTypeEnum
	 */
	public static ProjectRecoveryProcedureTypeEnum getByCode(String code) {
		for (ProjectRecoveryProcedureTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryProcedureTypeEnum>
	 */
	public static java.util.List<ProjectRecoveryProcedureTypeEnum> getAllEnum() {
		java.util.List<ProjectRecoveryProcedureTypeEnum> list = new java.util.ArrayList<ProjectRecoveryProcedureTypeEnum>(
			values().length);
		for (ProjectRecoveryProcedureTypeEnum _enum : values()) {
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
		for (ProjectRecoveryProcedureTypeEnum _enum : values()) {
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
		ProjectRecoveryProcedureTypeEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryProcedureTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
