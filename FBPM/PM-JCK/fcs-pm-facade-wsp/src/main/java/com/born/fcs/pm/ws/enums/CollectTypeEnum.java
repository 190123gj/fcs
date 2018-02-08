package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告 资料收集类型
 * 
 * @author lirz
 * 
 * 2016-6-14 上午11:13:06
 */
public enum CollectTypeEnum {
	
	COUNTER("COUNTER", "(反)担保方式"),
	COLLECTION("COLLECTION", "收集的资料");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CollectTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CollectTypeEnum(String code, String message) {
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
	 * @return CollectTypeEnum
	 */
	public static CollectTypeEnum getByCode(String code) {
		for (CollectTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CollectTypeEnum>
	 */
	public static List<CollectTypeEnum> getAllEnum() {
		List<CollectTypeEnum> list = new ArrayList<CollectTypeEnum>();
		for (CollectTypeEnum _enum : values()) {
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
		for (CollectTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
}
