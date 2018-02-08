package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告-企业情况调查工作底稿类型
 * 
 * @author lirz
 *
 * 2016-6-6 下午1:40:16
 */
public enum FinancialTypeEnum {
	
	CAPITAL("CAPITAL", "资产"),
	DEBT("DEBT", "负债"),
	SUBJECTS("SUBJECTS", "科目"),
	OTHER("OTHER", "其它");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FinancialTypeEnum(String code, String message) {
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
	 * @return FinancialTypeEnum
	 */
	public static FinancialTypeEnum getByCode(String code) {
		for (FinancialTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialTypeEnum>
	 */
	public static List<FinancialTypeEnum> getAllEnum() {
		List<FinancialTypeEnum> list = new ArrayList<FinancialTypeEnum>();
		for (FinancialTypeEnum _enum : values()) {
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
		for (FinancialTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
