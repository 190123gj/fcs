package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财产品收益类型
 *
 * @author wuzj
 */
public enum FinancialProductInterestTypeEnum {
	
	BREAKEVEN_FLOAT_INTEREST("BREAKEVEN_FLOAT_INTEREST", "保本浮动收益"),
	
	NOT_BREAKEVEN_FLOAT_INTEREST("NOT_BREAKEVEN_FLOAT_INTEREST", "非保本浮动收益"),
	
	BREAKEVEN_FIX_INTEREST("BREAKEVEN_FIX_INTEREST", "保本固定收益");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductInterestTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProductInterestTypeEnum(String code, String message) {
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
	 * @return FinancialProductInterestTypeEnum
	 */
	public static FinancialProductInterestTypeEnum getByCode(String code) {
		for (FinancialProductInterestTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProductInterestTypeEnum>
	 */
	public static List<FinancialProductInterestTypeEnum> getAllEnum() {
		List<FinancialProductInterestTypeEnum> list = new ArrayList<FinancialProductInterestTypeEnum>();
		for (FinancialProductInterestTypeEnum _enum : values()) {
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
		for (FinancialProductInterestTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
