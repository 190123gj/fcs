/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义字段类型
 * 
 * @author Ji
 * 
 */
public enum FieldTypeEnum {
	
	TEXT("TEXT", "文本型"),
	SELECT("SELECT", "选择型"),
	SELECT_CONTION_RELATION("SELECT_CONTION_RELATION", "条件关联"),
	DATE("DATE", "日期型"),
	NUMBER("NUMBER", "数值型"),
	ADMINISTRATIVE_PLAN("ADMINISTRATIVE_PLAN", "行政区划"),
	MTEXT("MTEXT", "多行表格");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>BooleanEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FieldTypeEnum(String code, String message) {
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
	 * @return BooleanEnum
	 */
	public static FieldTypeEnum getByCode(String code) {
		for (FieldTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BooleanEnum>
	 */
	public static List<FieldTypeEnum> getAllEnum() {
		List<FieldTypeEnum> list = new ArrayList<FieldTypeEnum>();
		for (FieldTypeEnum _enum : values()) {
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
		for (FieldTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
