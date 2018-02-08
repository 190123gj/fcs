package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险处置会 - 处置方案
 * 
 * @author wuzj
 *
 */
public enum RiskHandleSchemeEnum {
	
	COMPENSATORY("COMPENSATORY", "代偿"),
	
	DELAY("DELAY", "展期"),
	
	OTHER("OTHER", "其他组合");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>RiskHandleSchemeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private RiskHandleSchemeEnum(String code, String message) {
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
	 * @return RiskHandleSchemeEnum
	 */
	public static RiskHandleSchemeEnum getByCode(String code) {
		for (RiskHandleSchemeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<RiskHandleSchemeEnum>
	 */
	public static List<RiskHandleSchemeEnum> getAllEnum() {
		List<RiskHandleSchemeEnum> list = new ArrayList<RiskHandleSchemeEnum>();
		for (RiskHandleSchemeEnum _enum : values()) {
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
		for (RiskHandleSchemeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
