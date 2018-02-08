package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 风险评级阶段
 * 
 * @author lirz
 *
 */
public enum CheckPhaseEnum {
	
	E("E", "初评"),
	
	RE("RE", "复评")

	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CheckPhaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CheckPhaseEnum(String code, String message) {
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
	 * @return CheckPhaseEnum
	 */
	public static CheckPhaseEnum getByCode(String code) {
		for (CheckPhaseEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CheckPhaseEnum>
	 */
	public static List<CheckPhaseEnum> getAllEnum() {
		List<CheckPhaseEnum> list = new ArrayList<CheckPhaseEnum>();
		for (CheckPhaseEnum _enum : values()) {
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
		for (CheckPhaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
