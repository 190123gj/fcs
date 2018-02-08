package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财产品类型
 *
 * @author wuzj
 */
public enum FinancialProductTypeEnum {
	
	BANK_FINANCING("BANK_FINANCING", "银行理财产品"),
	BOND("BOND", "债权"),
	ENTRUST("ENTRUST", "信托"),
	CAPITAL_MP("CAPITAL_MP", "资管计划"),
	INTEREST_RIGHT("INTEREST_RIGHT", "收益权");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProductTypeEnum(String code, String message) {
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
	 * @return FinancialProductTypeEnum
	 */
	public static FinancialProductTypeEnum getByCode(String code) {
		for (FinancialProductTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProductTypeEnum>
	 */
	public static List<FinancialProductTypeEnum> getAllEnum() {
		List<FinancialProductTypeEnum> list = new ArrayList<FinancialProductTypeEnum>();
		for (FinancialProductTypeEnum _enum : values()) {
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
		for (FinancialProductTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
