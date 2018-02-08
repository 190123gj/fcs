package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目扩展属性
 *
 *
 * @author wuzj
 *
 */
public enum ProjectExtendPropertyEnum {
	
	SUMMARY("SUMMARY", "项目小结"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectExtendPropertyEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectExtendPropertyEnum(String code, String message) {
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
	 * @return ProjectExtendPropertyEnum
	 */
	public static ProjectExtendPropertyEnum getByCode(String code) {
		for (ProjectExtendPropertyEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectExtendPropertyEnum>
	 */
	public static List<ProjectExtendPropertyEnum> getAllEnum() {
		List<ProjectExtendPropertyEnum> list = new ArrayList<ProjectExtendPropertyEnum>();
		for (ProjectExtendPropertyEnum _enum : values()) {
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
		for (ProjectExtendPropertyEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
