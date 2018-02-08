/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业规模
 *
 * @author wuzj
 */
public enum EnterpriseScaleEnum {
	
	HUGE("HUGE", "特大", 5),
	
	BIG("BIG", "大型", 4),
	
	MEDIUM("MEDIUM", "中型", 3),
	
	SMALL("SMALL", "小型", 2),
	
	TINY("TINY", "微型", 1);
	
	/** 枚举值 */
	private final String code;
	
	private final int value;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>EnterpriseScaleEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private EnterpriseScaleEnum(String code, String message, int value) {
		this.code = code;
		this.message = message;
		this.value = value;
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
	
	public int getValue() {
		return value;
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
	
	public int value() {
		return value;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return BooleanEnum
	 */
	public static EnterpriseScaleEnum getByCode(String code) {
		for (EnterpriseScaleEnum _enum : values()) {
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
	public static List<EnterpriseScaleEnum> getAllEnum() {
		List<EnterpriseScaleEnum> list = new ArrayList<EnterpriseScaleEnum>();
		for (EnterpriseScaleEnum _enum : values()) {
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
		for (EnterpriseScaleEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
