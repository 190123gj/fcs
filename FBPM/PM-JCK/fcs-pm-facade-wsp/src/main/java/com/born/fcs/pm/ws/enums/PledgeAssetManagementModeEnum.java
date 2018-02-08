/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:39:53 创建
 */
package com.born.fcs.pm.ws.enums;

/**
 * 
 * 抵债资产管理方式枚举
 * @author hjiajie
 * 
 */
public enum PledgeAssetManagementModeEnum {
	
	/*** 出租 **/
	RENT_OUT("RENT_OUT", "出租"),
	/*** 出售 **/
	SELL("SELL", "出售"),
	/*** 闲置 **/
	LEAVE_UNUSED("LEAVE_UNUSED", "闲置"),
	/*** 自用 **/
	PERSONAL("PERSONAL", "自用"),
	/*** 其他 **/
	OTHER("OTHER", "其他"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private PledgeAssetManagementModeEnum(String code, String message) {
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
	 * @return PledgeAssetManagementModeEnum
	 */
	public static PledgeAssetManagementModeEnum getByCode(String code) {
		for (PledgeAssetManagementModeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PledgeAssetManagementModeEnum>
	 */
	public static java.util.List<PledgeAssetManagementModeEnum> getAllEnum() {
		java.util.List<PledgeAssetManagementModeEnum> list = new java.util.ArrayList<PledgeAssetManagementModeEnum>(
			values().length);
		for (PledgeAssetManagementModeEnum _enum : values()) {
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
		for (PledgeAssetManagementModeEnum _enum : values()) {
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
		PledgeAssetManagementModeEnum _enum = getByCode(code);
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
	public static String getCode(PledgeAssetManagementModeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
