package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 所属
 * 
 * @author lirz
 *
 * 2016-3-19 下午3:04:47
 */
public enum OwnerEnum {
	
	CUSTOMER("CUSTOMER", "客户"),
	
	GUARANTOR("GUARANTOR", "担保人"),
	
	/** 授信方案主要事项合理性评价-法人 */
	GUARANTOR_NEW("GUARANTOR_NEW", "担保人")
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>OwnerEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private OwnerEnum(String code, String message) {
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
	 * @return OwnerEnum
	 */
	public static OwnerEnum getByCode(String code) {
		for (OwnerEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<OwnerEnum>
	 */
	public static List<OwnerEnum> getAllEnum() {
		List<OwnerEnum> list = new ArrayList<OwnerEnum>();
		for (OwnerEnum _enum : values()) {
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
		for (OwnerEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
