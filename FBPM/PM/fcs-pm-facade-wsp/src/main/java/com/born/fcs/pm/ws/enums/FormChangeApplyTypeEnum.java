/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 签报类型
 * 
 * @Author wuzj
 */
public enum FormChangeApplyTypeEnum {
	
	FORM("FORM", "表单签报"),
	
	ITEM("ITEM", "事项签报"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FromChangeTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormChangeApplyTypeEnum(String code, String message) {
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
	 * @return FromChangeTypeEnum
	 */
	public static FormChangeApplyTypeEnum getByCode(String code) {
		for (FormChangeApplyTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FromChangeTypeEnum>
	 */
	public static List<FormChangeApplyTypeEnum> getAllEnum() {
		List<FormChangeApplyTypeEnum> list = new ArrayList<FormChangeApplyTypeEnum>();
		for (FormChangeApplyTypeEnum _enum : values()) {
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
		for (FormChangeApplyTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
