package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 尽调审核节点
 *
 * @author lirz
 * 
 * 2017-3-20 下午4:08:51
 *
 */
public enum CheckPointEnum {
	
	SOURCE("SOURCE", "源"), //客户经理提交的数据
	
	TEAM_LEADER("TEAM_LEADER", "部门负责人"),
	
	VICE_PRESIDENT_BUSINESS("VICE_PRESIDENT_BUSINESS", "分管业务副总");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CheckPointEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CheckPointEnum(String code, String message) {
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
	 * @return CheckPointEnum
	 */
	public static CheckPointEnum getByCode(String code) {
		for (CheckPointEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CheckPointEnum>
	 */
	public static List<CheckPointEnum> getAllEnum() {
		List<CheckPointEnum> list = new ArrayList<CheckPointEnum>();
		for (CheckPointEnum _enum : values()) {
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
		for (CheckPointEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
