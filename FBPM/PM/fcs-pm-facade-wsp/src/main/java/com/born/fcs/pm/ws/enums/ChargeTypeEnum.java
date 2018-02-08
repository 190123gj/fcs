/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 收费方式
 *
 *
 * @author wuzj
 *
 */
public enum ChargeTypeEnum {
	
	PERCENT("PERCENT", "%", "按费率收取"),
	
	AMOUNT("AMOUNT", "元", "按金额收取");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	private final String unit;
	
	/**
	 * 构造一个<code>ChargeTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ChargeTypeEnum(String code, String unit, String message) {
		this.code = code;
		this.message = message;
		this.unit = unit;
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
	
	public String getUnit() {
		return unit;
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
	
	public String unit() {
		return unit;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ChargeTypeEnum
	 */
	public static ChargeTypeEnum getByCode(String code) {
		for (ChargeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChargeTypeEnum>
	 */
	public static List<ChargeTypeEnum> getAllEnum() {
		List<ChargeTypeEnum> list = new ArrayList<ChargeTypeEnum>();
		for (ChargeTypeEnum _enum : values()) {
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
		for (ChargeTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
