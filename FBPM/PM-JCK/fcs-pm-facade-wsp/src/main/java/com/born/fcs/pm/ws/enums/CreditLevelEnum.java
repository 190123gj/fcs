package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户主体评级(授信方案)
 * 
 * @author lirz
 *
 * 2016-4-5 下午2:33:00
 */
public enum CreditLevelEnum {
	
	A1("A1", "AAA"),
	A2("A2", "AA"),
	A3("A3", "AA-"),
	A4("A4", "A+"),
	A5("A5", "A"),
	
	B1("B1", "BBB"),
	B2("B2", "BB"),
	B3("B3", "B"),
	
	F("F", "F");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CreditLevelEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CreditLevelEnum(String code, String message) {
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
	 * @return CreditLevelEnum
	 */
	public static CreditLevelEnum getByCode(String code) {
		for (CreditLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CreditLevelEnum>
	 */
	public static List<CreditLevelEnum> getAllEnum() {
		List<CreditLevelEnum> list = new ArrayList<CreditLevelEnum>();
		for (CreditLevelEnum _enum : values()) {
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
		for (CreditLevelEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
