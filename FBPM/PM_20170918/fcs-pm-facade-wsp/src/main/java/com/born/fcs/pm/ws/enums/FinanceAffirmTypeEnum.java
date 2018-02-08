package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 财务确认-资金划付和收费通知
 * 
 * @author ji
 *
 */
public enum FinanceAffirmTypeEnum {
	
	CHARGE_NOTIFICATION("CHARGE_NOTIFICATION", "收费通知单"),
	CAPITAL_APPROPROATION_APPLY("CAPITAL_APPROPROATION_APPLY", "资金划付申请单");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CapitalTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinanceAffirmTypeEnum(String code, String message) {
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
	 * @return CapitalTypeEnum
	 */
	public static FinanceAffirmTypeEnum getByCode(String code) {
		for (FinanceAffirmTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CapitalTypeEnum>
	 */
	public static List<FinanceAffirmTypeEnum> getAllEnum() {
		List<FinanceAffirmTypeEnum> list = new ArrayList<FinanceAffirmTypeEnum>();
		for (FinanceAffirmTypeEnum _enum : values()) {
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
		for (FinanceAffirmTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
