package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 信号等级
 * 
 * @author lirz
 * 
 * 2016-4-15 下午3:55:27
 */
public enum SignalLevelEnum {
	
	SPECIAL("SPECIAL", "特别预警"),
	
	NOMAL("NOMAL", "一般预警"),
	
	HAVE_LIFTED("HAVE_LIFTED", "已解除"),
	
	NOTHING("NOTHING", "无");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SignalLevelEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SignalLevelEnum(String code, String message) {
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
	 * @return SignalLevelEnum
	 */
	public static SignalLevelEnum getByCode(String code) {
		for (SignalLevelEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SignalLevelEnum>
	 */
	public static List<SignalLevelEnum> getAllEnum() {
		List<SignalLevelEnum> list = new ArrayList<SignalLevelEnum>();
		for (SignalLevelEnum _enum : values()) {
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
		for (SignalLevelEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
