/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:51:20 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 主持人本次不议之后，后续操作类型
 * @author hjiajie
 * 
 */
public enum ProjectCouncilCompereMessageEnum {
	
	/** 修改尽职调查报告 (再次上会会生成新上会记录) **/
	INVESTIGATING_PHASES("INVESTIGATING_PHASES", "已退回"),
	
	/** 项目重新上会(修订原上会记录为待上会) **/
	RETURN_COUNCIL_APPLY("RETURN_COUNCIL_APPLY", "项目重新上会"),
	
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
	private ProjectCouncilCompereMessageEnum(String code, String message) {
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
	 * @return ProjectCouncilCompereMessageEnum
	 */
	public static ProjectCouncilCompereMessageEnum getByCode(String code) {
		for (ProjectCouncilCompereMessageEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectCouncilCompereMessageEnum>
	 */
	public static java.util.List<ProjectCouncilCompereMessageEnum> getAllEnum() {
		java.util.List<ProjectCouncilCompereMessageEnum> list = new java.util.ArrayList<ProjectCouncilCompereMessageEnum>(
			values().length);
		for (ProjectCouncilCompereMessageEnum _enum : values()) {
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
		for (ProjectCouncilCompereMessageEnum _enum : values()) {
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
		ProjectCouncilCompereMessageEnum _enum = getByCode(code);
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
	public static String getCode(ProjectCouncilCompereMessageEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
