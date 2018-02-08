package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信条件类型
 * 
 * @author wuzj
 */
public enum CreditConditionTypeEnum {
	
	PLEDGE("PLEDGE", "抵押"),
	MORTGAGE("MORTGAGE", "质押"),
	GUARANTOR("GUARANTOR", "保证"),
	TEXT("TEXT", "文字授信条件");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CreditConditionTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CreditConditionTypeEnum(String code, String message) {
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
	 * @return CreditConditionTypeEnum
	 */
	public static CreditConditionTypeEnum getByCode(String code) {
		for (CreditConditionTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CreditConditionTypeEnum>
	 */
	public static List<CreditConditionTypeEnum> getAllEnum() {
		List<CreditConditionTypeEnum> list = new ArrayList<CreditConditionTypeEnum>();
		for (CreditConditionTypeEnum _enum : values()) {
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
		for (CreditConditionTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
