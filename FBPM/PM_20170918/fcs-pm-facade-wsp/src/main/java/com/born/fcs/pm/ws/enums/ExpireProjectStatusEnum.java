package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 到期项目状态
 * 
 * @author lirz
 *
 * 2016-4-11 下午4:00:05
 */
public enum ExpireProjectStatusEnum {
	
	/** 未到期 */
	UNDUE("UNDUE", "未到期"),

	/** 到期 */
	EXPIRE("EXPIRE", "即将到期"),

	/** 追偿中 */
	RECOVERYING("RECOVERYING", "追偿中"),

	/** 逾期 */
	OVERDUE("OVERDUE", "逾期"), 
	
	/** 已完成 */
	DONE("DONE", "已完成");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ExpireProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ExpireProjectStatusEnum(String code, String message) {
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
	 * @return ExpireProjectStatusEnum
	 */
	public static ExpireProjectStatusEnum getByCode(String code) {
		for (ExpireProjectStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ExpireProjectStatusEnum>
	 */
	public static List<ExpireProjectStatusEnum> getAllEnum() {
		List<ExpireProjectStatusEnum> list = new ArrayList<ExpireProjectStatusEnum>();
		for (ExpireProjectStatusEnum _enum : values()) {
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
		for (ExpireProjectStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
