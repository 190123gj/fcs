package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司类型
 * 
 * @author lirz
 *
 * 2016-3-10 下午2:56:11
 */
public enum CompanyTypeEnum {
	
	SUBSIDIARY("SUBSIDIARY", "子公司"),
	
	PARTICIPATION("PARTICIPATION", "参股公司"),
	
	CORRELATION("CORRELATION", "关联公司"),
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CompanyTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CompanyTypeEnum(String code, String message) {
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
	 * @return CompanyTypeEnum
	 */
	public static CompanyTypeEnum getByCode(String code) {
		for (CompanyTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CompanyTypeEnum>
	 */
	public static List<CompanyTypeEnum> getAllEnum() {
		List<CompanyTypeEnum> list = new ArrayList<CompanyTypeEnum>();
		for (CompanyTypeEnum _enum : values()) {
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
		for (CompanyTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
