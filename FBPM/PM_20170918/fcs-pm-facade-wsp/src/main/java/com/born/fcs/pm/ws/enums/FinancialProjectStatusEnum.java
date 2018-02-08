package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财项目状态
 * 
 * @author wuzj
 */
public enum FinancialProjectStatusEnum {
	
	PURCHASING("PURCHASING", "待购买"),
	PURCHASED("PURCHASED", "已购买"),
	EXPIRE("EXPIRE", "已到期 "),
	FINISH("FINISH", "已完成");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialProjectStatusEnum(String code, String message) {
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
	 * @return EnterpriseNatureEnum
	 */
	public static FinancialProjectStatusEnum getByCode(String code) {
		for (FinancialProjectStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EnterpriseNatureEnum>
	 */
	public static List<FinancialProjectStatusEnum> getAllEnum() {
		List<FinancialProjectStatusEnum> list = new ArrayList<FinancialProjectStatusEnum>();
		for (FinancialProjectStatusEnum _enum : values()) {
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
		for (FinancialProjectStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
