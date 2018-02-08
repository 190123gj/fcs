package com.born.fcs.fm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 预测类型，子类型1
 * 
 * @author wuzj
 */
public enum ForecastChildTypeOneEnum {
	
	/// 理财产品期限类型  
	
	LONG_TERM("LONG_TERM", "financial", "中长期"),
	
	SHORT_TERM("SHORT_TERM", "financial", "短期");
	
	/** 枚举值 */
	private final String code;
	
	/** 类型 */
	private final String type;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductTermTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ForecastChildTypeOneEnum(String code, String type, String message) {
		this.code = code;
		this.type = type;
		this.message = message;
	}
	
	public String getType() {
		return this.type;
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
	public static ForecastChildTypeOneEnum getByCode(String code) {
		for (ForecastChildTypeOneEnum _enum : values()) {
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
	public static List<ForecastChildTypeOneEnum> getAllEnum() {
		List<ForecastChildTypeOneEnum> list = new ArrayList<ForecastChildTypeOneEnum>();
		for (ForecastChildTypeOneEnum _enum : values()) {
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
		for (ForecastChildTypeOneEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
