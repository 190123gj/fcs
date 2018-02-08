package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 信号分类
 * 
 * @author lirz
 *
 * 2016-4-15 下午4:46:39
 */
public enum SignalTypeEnum {
	
	COMPANY("COMPANY", "公司类"),
	
	MICRO_BUSINESSE("MICRO_BUSINESSE", "小微企业类"), 
	
	FINANCE("FINANCE", "金融类");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>SignalTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private SignalTypeEnum(String code, String message) {
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
	 * @return SignalTypeEnum
	 */
	public static SignalTypeEnum getByCode(String code) {
		for (SignalTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SignalTypeEnum>
	 */
	public static List<SignalTypeEnum> getAllEnum() {
		List<SignalTypeEnum> list = new ArrayList<SignalTypeEnum>();
		for (SignalTypeEnum _enum : values()) {
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
		for (SignalTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
