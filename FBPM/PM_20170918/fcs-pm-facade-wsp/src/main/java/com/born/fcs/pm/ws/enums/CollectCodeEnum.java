package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告 资料收集类型
 * 
 * @author lirz
 *
 * 2016-6-14 上午11:13:06
 */
public enum CollectCodeEnum {
	
	BALANCE_SHEET("BALANCE_SHEET", "资产负债表"),
	INCOME_STATEMENT("INCOME_STATEMENT", "损益表"),
	CASH_FLOW("CASH_FLOW", "现金流量表"),
	OTHER("OTHER", "其他"),
	
	PERSONAL_GUARANTEE("PERSONAL_GUARANTEE", "个人保证"),
	LEGAL_GUARANTEE("LEGAL_GUARANTEE", "法人保证"),
	PLEDGE("PLEDGE", "抵押"),
	MORTGAGE("MORTGAGE", "质押");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CollectCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CollectCodeEnum(String code, String message) {
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
	 * @return CollectCodeEnum
	 */
	public static CollectCodeEnum getByCode(String code) {
		for (CollectCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CollectCodeEnum>
	 */
	public static List<CollectCodeEnum> getAllEnum() {
		List<CollectCodeEnum> list = new ArrayList<CollectCodeEnum>();
		for (CollectCodeEnum _enum : values()) {
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
		for (CollectCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 获取反担保方式枚举
	 * 
	 * @return List<CollectCodeEnum>
	 */
	public static List<CollectCodeEnum> getCounterGuaranteeEnum() {
		List<CollectCodeEnum> list = new ArrayList<CollectCodeEnum>();
		list.add(PERSONAL_GUARANTEE);
		list.add(LEGAL_GUARANTEE);
		list.add(PLEDGE);
		list.add(MORTGAGE);
		list.add(OTHER);
		return list;
	}
	
	/**
	 * 获取资料收集枚举
	 * 
	 * @return List<CollectCodeEnum>
	 */
	public static List<CollectCodeEnum> getCollectionEnum() {
		List<CollectCodeEnum> list = new ArrayList<CollectCodeEnum>();
		list.add(BALANCE_SHEET);
		list.add(INCOME_STATEMENT);
		list.add(CASH_FLOW);
		list.add(OTHER);
		return list;
	}
}
