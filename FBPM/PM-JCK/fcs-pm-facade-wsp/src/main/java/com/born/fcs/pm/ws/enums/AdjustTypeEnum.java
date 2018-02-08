package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户主体评级调整
 * 
 * @author lirz
 *
 * 2016-4-5 下午2:39:10
 */
public enum AdjustTypeEnum {
	
	UP_1_LEVEL("UP_1_LEVEL", "每上调一级"),
	
	DOWN_1_LEVEL("DOWN_1_LEVEL", "每下调一级");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>AdjustTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AdjustTypeEnum(String code, String message) {
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
	 * @return AdjustTypeEnum
	 */
	public static AdjustTypeEnum getByCode(String code) {
		for (AdjustTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AdjustTypeEnum>
	 */
	public static List<AdjustTypeEnum> getAllEnum() {
		List<AdjustTypeEnum> list = new ArrayList<AdjustTypeEnum>();
		for (AdjustTypeEnum _enum : values()) {
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
		for (AdjustTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
