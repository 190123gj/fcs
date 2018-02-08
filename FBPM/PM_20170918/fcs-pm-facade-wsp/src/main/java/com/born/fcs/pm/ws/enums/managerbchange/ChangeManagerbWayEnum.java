package com.born.fcs.pm.ws.enums.managerbchange;

import java.util.ArrayList;
import java.util.List;

/**
 * B角变更生效阶段
 * 
 * @author wuzj
 */
public enum ChangeManagerbWayEnum {
	
	DIRECT("DIRECT", "直接更换"),
	PERIOD("PERIOD", "时间"),
	PHASES("PHASES", "阶段更换"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ChargeWayPhaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ChangeManagerbWayEnum(String code, String message) {
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
	 * @return ChargeWayPhaseEnum
	 */
	public static ChangeManagerbWayEnum getByCode(String code) {
		for (ChangeManagerbWayEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChargeWayPhaseEnum>
	 */
	public static List<ChangeManagerbWayEnum> getAllEnum() {
		List<ChangeManagerbWayEnum> list = new ArrayList<ChangeManagerbWayEnum>();
		for (ChangeManagerbWayEnum _enum : values()) {
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
		for (ChangeManagerbWayEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
