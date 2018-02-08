package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 解保项目列表状态
 * 
 * @author lirz
 *
 * 2016-6-1 下午3:37:58
 */
public enum ReleaseProjectStatusEnum {
	
	AVAILABLE("AVAILABLE", "可解保"),
	
	DISABLE("DISABLE", "不可解保"),
	
	RELEASING("RELEASING", "解保中"),
	
	FINISHED("FINISHED", "已解保");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ReleaseProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ReleaseProjectStatusEnum(String code, String message) {
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
	 * @return ReleaseProjectStatusEnum
	 */
	public static ReleaseProjectStatusEnum getByCode(String code) {
		for (ReleaseProjectStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ReleaseProjectStatusEnum>
	 */
	public static List<ReleaseProjectStatusEnum> getAllEnum() {
		List<ReleaseProjectStatusEnum> list = new ArrayList<ReleaseProjectStatusEnum>();
		for (ReleaseProjectStatusEnum _enum : values()) {
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
		for (ReleaseProjectStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
