package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 评级 低于/高于
 * 
 * @author lirz
 *
 * 2016-3-21 下午4:04:56
 */
public enum GradeLevelEnum {
	
	BELOW("BELOW", "低于"),
	
	OVER("OVER", "高于");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>GradeLevelEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private GradeLevelEnum(String code, String message) {
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
	 * @return GradeLevelEnum
	 */
	public static GradeLevelEnum getByCode(String code) {
		for (GradeLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<GradeLevelEnum>
	 */
	public static List<GradeLevelEnum> getAllEnum() {
		List<GradeLevelEnum> list = new ArrayList<GradeLevelEnum>();
		for (GradeLevelEnum _enum : values()) {
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
		for (GradeLevelEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
