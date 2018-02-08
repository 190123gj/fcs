package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典类型
 *
 * @author wuzj
 */
public enum DataCodeEnum {
	
	CURRENCY("CURRENCY", 2, false, "货币"),
	
	COUNCIL_PLACE("COUNCIL_PLACE", 1, false, "会议地点"),
	
	LOAN_PURPOSE("LOAN_PURPOSE", 1, true, "授信用途");
	
	/** 枚举值 */
	private final String code;
	
	/** 值个数 , 最大支持4个值 */
	private final int valueCount;
	
	/** 是否级联 */
	private final boolean cascade;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>DataCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private DataCodeEnum(String code, int valueCount, boolean cascade, String message) {
		this.code = code;
		this.valueCount = valueCount;
		this.cascade = cascade;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the valueCount.
	 */
	public int getValueCount() {
		return valueCount;
	}
	
	/**
	 * @return Returns the cascade.
	 */
	public boolean isCascade() {
		return this.cascade;
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
	 * @return Returns the valueCount.
	 */
	public int valueCount() {
		return valueCount;
	}
	
	/**
	 * @return Returns the cascade.
	 */
	public boolean cascade() {
		return this.cascade;
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
	 * @return DataCodeEnum
	 */
	public static DataCodeEnum getByCode(String code) {
		for (DataCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<DataCodeEnum>
	 */
	public static List<DataCodeEnum> getAllEnum() {
		List<DataCodeEnum> list = new ArrayList<DataCodeEnum>();
		for (DataCodeEnum _enum : values()) {
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
		for (DataCodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
