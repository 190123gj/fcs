package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告 在建项目-前十大客户-按期收回情况等
 * 
 * @author lirz
 *
 * 2016-6-8 下午4:28:08
 */
public enum CapitalTypeEnum {
	
	
	INCOME_DETAIL("INCOME_DETAIL", "企业收入调查工作底稿"),
	PROJECTING("PROJECTING", "在建项目情况表"),
	DEBT_DETAIL("DEBT_DETAIL", "企业资产负债划转明细"),
	CREDIT_DETAIL("CREDIT_DETAIL", "信用状况"),
	TEN_CUSTOMER("TEN_CUSTOMER", "前十大客户"),
	HOLDER_LOAN("HOLDER_LOAN", "股东担保贷款（或关联企业贷款）明细"),
	BACK_ON_TIME("BACK_ON_TIME", "按期收回情况"),
	SUCCESSFUL_PROJECT("SUCCESSFUL_PROJECT", "中标项目"),
	SUB_CONTRACTOR("SUB_CONTRACTOR", "项目分包商");
	
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
	private CapitalTypeEnum(String code, String message) {
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
	public static CapitalTypeEnum getByCode(String code) {
		for (CapitalTypeEnum _enum : values()) {
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
	public static List<CapitalTypeEnum> getAllEnum() {
		List<CapitalTypeEnum> list = new ArrayList<CapitalTypeEnum>();
		for (CapitalTypeEnum _enum : values()) {
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
		for (CapitalTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
