package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 收费时间
 * 
 * @author lirz
 *
 * 2016-3-8 下午3:28:00
 */
public enum ChargePhaseEnum {
	
	BEFORE("BEFORE", "先收"),
	
	AFTER("AFTER", "后收");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ChargePhaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ChargePhaseEnum(String code, String message) {
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
	 * @return ChargePhaseEnum
	 */
	public static ChargePhaseEnum getByCode(String code) {
		for (ChargePhaseEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChargePhaseEnum>
	 */
	public static List<ChargePhaseEnum> getAllEnum() {
		List<ChargePhaseEnum> list = new ArrayList<ChargePhaseEnum>();
		for (ChargePhaseEnum _enum : values()) {
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
		for (ChargePhaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
