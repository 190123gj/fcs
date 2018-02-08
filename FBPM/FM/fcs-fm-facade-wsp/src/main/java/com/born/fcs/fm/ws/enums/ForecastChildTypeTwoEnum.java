package com.born.fcs.fm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 预测类型，子类型2
 * 
 * @author wuzj
 */
public enum ForecastChildTypeTwoEnum {
	
	/// 理财产品期限类型  
	
	BANK_FINANCING("BANK_FINANCING", "financial", "银行理财产品"),
	BOND("BOND", "financial", "债权"),
	ENTRUST("ENTRUST", "financial", "信托"),
	CAPITAL_MP("CAPITAL_MP", "financial", "资管计划"),
	INTEREST_RIGHT("INTEREST_RIGHT", "financial", "收益权"),
	CUSTOMIZED_DEPOSIT("CUSTOMIZED_DEPOSIT", "financial", "定制存款"),
	STRUCTURAL_DEPOSIT("STRUCTURAL_DEPOSIT", "financial", "结构性存款"),
	PROTOCOL_DEPOSIT("PROTOCOL_DEPOSIT", "financial", "协议存款"),
	OTHER("OTHER", "financial", "其他"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 类型 */
	private final String type;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ForecastChildTypeTwoEnum(String code, String type, String message) {
		this.code = code;
		this.type = type;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	public String getType() {
		return this.type;
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
	public static ForecastChildTypeTwoEnum getByCode(String code) {
		for (ForecastChildTypeTwoEnum _enum : values()) {
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
	public static List<ForecastChildTypeTwoEnum> getAllEnum() {
		List<ForecastChildTypeTwoEnum> list = new ArrayList<ForecastChildTypeTwoEnum>();
		for (ForecastChildTypeTwoEnum _enum : values()) {
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
		for (ForecastChildTypeTwoEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
