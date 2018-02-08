/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间选择范围
 *
 * @author Ji
 */
public enum TimeRangeEnum {

	RANDOM_TIME("RANDOM_TIME", "任意时间"), 
	SYSTEM_TIME_BEFORE("SYSTEM_TIME_BEFORE", "系统时间前"), 
	SYSTEM_TIME_AFTER("SYSTEM_TIME_AFTER", "系统时间后");

	/** 枚举值 */
	private final String code;

	/** 枚举描述 */
	private final String message;

	/**
	 * 构造一个<code>BooleanEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private TimeRangeEnum(String code, String message) {
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
	 * @return BooleanEnum
	 */
	public static TimeRangeEnum getByCode(String code) {
		for (TimeRangeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 * 
	 * @return List<BooleanEnum>
	 */
	public static List<TimeRangeEnum> getAllEnum() {
		List<TimeRangeEnum> list = new ArrayList<TimeRangeEnum>();
		for (TimeRangeEnum _enum : values()) {
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
		for (TimeRangeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
