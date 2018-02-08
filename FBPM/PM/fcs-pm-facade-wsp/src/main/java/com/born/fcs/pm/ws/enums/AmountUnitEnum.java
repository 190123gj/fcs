package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 货币单位
 *
 * @author lirz
 *
 * 2016-7-20 下午5:35:55
 */
public enum AmountUnitEnum {
	
	
	Y("Y", "元"),
	W("W", "万元"),
	B("B", "亿元");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>AmountUnitEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AmountUnitEnum(String code, String message) {
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
	 * @return AmountUnitEnum
	 */
	public static AmountUnitEnum getByCode(String code) {
		for (AmountUnitEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AmountUnitEnum>
	 */
	public static List<AmountUnitEnum> getAllEnum() {
		List<AmountUnitEnum> list = new ArrayList<AmountUnitEnum>();
		for (AmountUnitEnum _enum : values()) {
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
		for (AmountUnitEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
