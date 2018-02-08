package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 承销项目 收费方式
 * 
 * @author lirz
 *
 * 2016-3-23 下午4:52:48
 */
public enum UnderwritingChargeWaytEnum {
	
	SINGLE("SINGLE", "发行时一次性收取"),
	
	BY_YEAR("BY_YEAR", "按年");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>UnderwritingChargeWaytEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private UnderwritingChargeWaytEnum(String code, String message) {
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
	 * @return UnderwritingChargeWaytEnum
	 */
	public static UnderwritingChargeWaytEnum getByCode(String code) {
		for (UnderwritingChargeWaytEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<UnderwritingChargeWaytEnum>
	 */
	public static List<UnderwritingChargeWaytEnum> getAllEnum() {
		List<UnderwritingChargeWaytEnum> list = new ArrayList<UnderwritingChargeWaytEnum>();
		for (UnderwritingChargeWaytEnum _enum : values()) {
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
		for (UnderwritingChargeWaytEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
