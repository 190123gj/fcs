package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信方案 - 过程控制
 * 
 * @author lirz
 *
 * 2016-3-21 下午4:07:08
 */
public enum ProcessControlEnum {
	
	CUSTOMER_GRADE("CUSTOMER_GRADE", "客户主体评价"),
	
	DEBT_RATIO("DEBT_RATIO", "资产负债率"),
	
	OTHER("OTHER", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProcessControlEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProcessControlEnum(String code, String message) {
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
	 * @return ProcessControlEnum
	 */
	public static ProcessControlEnum getByCode(String code) {
		for (ProcessControlEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProcessControlEnum>
	 */
	public static List<ProcessControlEnum> getAllEnum() {
		List<ProcessControlEnum> list = new ArrayList<ProcessControlEnum>();
		for (ProcessControlEnum _enum : values()) {
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
		for (ProcessControlEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
