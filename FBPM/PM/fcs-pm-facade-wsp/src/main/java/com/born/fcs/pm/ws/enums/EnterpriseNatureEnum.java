package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业性质
 *
 * 国有/非国有
 *
 * @author wuzj
 */
public enum EnterpriseNatureEnum {
	
	STATE_OWNED("STATE_OWNED", "国有企业"),
	PRIVATE("PRIVATE", "民营企业"),
	FOREIGN_OWNED("FOREIGN_OWNED", "外资企业"),
	OTHER("OTHER", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>EnterpriseNatureEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private EnterpriseNatureEnum(String code, String message) {
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
	public static EnterpriseNatureEnum getByCode(String code) {
		for (EnterpriseNatureEnum _enum : values()) {
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
	public static List<EnterpriseNatureEnum> getAllEnum() {
		List<EnterpriseNatureEnum> list = new ArrayList<EnterpriseNatureEnum>();
		for (EnterpriseNatureEnum _enum : values()) {
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
		for (EnterpriseNatureEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
