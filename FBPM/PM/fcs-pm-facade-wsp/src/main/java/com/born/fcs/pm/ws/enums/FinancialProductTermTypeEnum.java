package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财产品期限类型
 *
 * @author wuzj
 */
public enum FinancialProductTermTypeEnum {
	
	LONG_TERM("LONG_TERM", "中长期"),
	
	SHORT_TERM("SHORT_TERM", "短期");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductTermTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProductTermTypeEnum(String code, String message) {
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
	 * @return FinancialProductTermTypeEnum
	 */
	public static FinancialProductTermTypeEnum getByCode(String code) {
		for (FinancialProductTermTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProductTermTypeEnum>
	 */
	public static List<FinancialProductTermTypeEnum> getAllEnum() {
		List<FinancialProductTermTypeEnum> list = new ArrayList<FinancialProductTermTypeEnum>();
		for (FinancialProductTermTypeEnum _enum : values()) {
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
		for (FinancialProductTermTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
