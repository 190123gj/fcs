package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 信用类型
 * 
 * @author lirz
 *
 * 2016-3-10 下午3:11:26
 */
public enum CreditTypeEnum {
	
	CUSTOMER("CUSTOMER", "客户"),
	
	PERSONAL("PERSONAL", "个人");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CreditTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CreditTypeEnum(String code, String message) {
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
	 * @return CreditTypeEnum
	 */
	public static CreditTypeEnum getByCode(String code) {
		for (CreditTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CreditTypeEnum>
	 */
	public static List<CreditTypeEnum> getAllEnum() {
		List<CreditTypeEnum> list = new ArrayList<CreditTypeEnum>();
		for (CreditTypeEnum _enum : values()) {
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
		for (CreditTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
