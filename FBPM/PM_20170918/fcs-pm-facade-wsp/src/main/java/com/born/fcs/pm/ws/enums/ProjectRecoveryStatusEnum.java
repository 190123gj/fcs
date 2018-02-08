/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:06:16 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public enum ProjectRecoveryStatusEnum {
	
	/*** 草稿 **/
	DRAFT("DRAFT", "草稿"),
	
	/*** 追偿中 **/
	RECOVERYING("RECOVERYING", "追偿中"),
	
	/*** 关闭中 **/
	CLOSEING("CLOSEING", "追偿关闭"),
	
	/*** 已关闭 **/
	CLOSED("CLOSED", "项目关闭"),
	
	/*** 结束 **/
	END("END", "结束"),
	
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
	private ProjectRecoveryStatusEnum(String code, String message) {
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
	 * @return ProjectRecoveryStatus
	 */
	public static ProjectRecoveryStatusEnum getByCode(String code) {
		for (ProjectRecoveryStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRecoveryStatus>
	 */
	public static java.util.List<ProjectRecoveryStatusEnum> getAllEnum() {
		java.util.List<ProjectRecoveryStatusEnum> list = new java.util.ArrayList<ProjectRecoveryStatusEnum>(
			values().length);
		for (ProjectRecoveryStatusEnum _enum : values()) {
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
		for (ProjectRecoveryStatusEnum _enum : values()) {
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
		ProjectRecoveryStatusEnum _enum = getByCode(code);
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
	public static String getCode(ProjectRecoveryStatusEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
