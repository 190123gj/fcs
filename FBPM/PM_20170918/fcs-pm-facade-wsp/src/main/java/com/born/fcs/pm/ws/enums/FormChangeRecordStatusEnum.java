/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 修改记录执行状态
 * 
 * @Author wuzj
 */
public enum FormChangeRecordStatusEnum {
	
	DRAFT("DRAFT", "草稿"),
	
	APPLY_AUDITING("APPLY_AUDITING", "签报审核中"),
	
	APPLY_APPROVAL("APPLY_APPROVAL", "签报审核通过"),
	
	APPLY_DENY("APPLY_DENY", "签报审核不通过"),
	
	EXECUTE_FAIL("EXECUTE_FAIL", "执行失败"),
	
	EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FromChangeRecordStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormChangeRecordStatusEnum(String code, String message) {
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
	 * @return FromChangeRecordStatusEnum
	 */
	public static FormChangeRecordStatusEnum getByCode(String code) {
		for (FormChangeRecordStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FromChangeRecordStatusEnum>
	 */
	public static List<FormChangeRecordStatusEnum> getAllEnum() {
		List<FormChangeRecordStatusEnum> list = new ArrayList<FormChangeRecordStatusEnum>();
		for (FormChangeRecordStatusEnum _enum : values()) {
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
		for (FormChangeRecordStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
