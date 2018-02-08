package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财项目上会状态
 * 
 * @author wuzj
 *
 */
public enum FinancialProjectCouncilStatusEnum {
	
	COUNCIL_WAITING("COUNCIL_WAITING", "上会中"),
	
	COUNCIL_APPROVAL("COUNCIL_APPROVAL", "上会通过"),
	
	COUNCIL_DENY("COUNCIL_DENY", "上会不通过");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProjectCouncilStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProjectCouncilStatusEnum(String code, String message) {
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
	 * @return FinancialProjectCouncilStatusEnum
	 */
	public static FinancialProjectCouncilStatusEnum getByCode(String code) {
		for (FinancialProjectCouncilStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProjectCouncilStatusEnum>
	 */
	public static List<FinancialProjectCouncilStatusEnum> getAllEnum() {
		List<FinancialProjectCouncilStatusEnum> list = new ArrayList<FinancialProjectCouncilStatusEnum>();
		for (FinancialProjectCouncilStatusEnum _enum : values()) {
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
		for (FinancialProjectCouncilStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
