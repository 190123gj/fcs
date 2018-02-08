/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 签报单/经纪业务状态
 * 
 * @Author wuzj
 */
public enum FormChangeApplyStatusEnum {
	
	DRAFT("DRAFT", "草稿"),
	AUDITING("AUDITING", "审核中"),
	APPROVAL("APPROVAL", "通过"),
	DENY("DENY", "未通过"),
	COUNCIL_WAITING("COUNCIL_WAITING", "上会中"),
	COUNCIL_APPROVAL("COUNCIL_APPROVAL", "上会通过"),
	COUNCIL_DENY("COUNCIL_DENY", "上会未通过"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FormChangeApplyStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormChangeApplyStatusEnum(String code, String message) {
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
	 * @return Returns the processService.
	 */
	public String processService() {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FormChangeApplyStatusEnum
	 */
	public static FormChangeApplyStatusEnum getByCode(String code) {
		for (FormChangeApplyStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormChangeApplyStatusEnum>
	 */
	public static List<FormChangeApplyStatusEnum> getAllEnum() {
		List<FormChangeApplyStatusEnum> list = new ArrayList<FormChangeApplyStatusEnum>();
		for (FormChangeApplyStatusEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (FormChangeApplyStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
