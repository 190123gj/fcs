package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 押品类型
 * 
 * @author lirz
 *
 * 2016-3-9 下午4:02:26
 */
public enum PledgeTypeEnum {
	
	LAND("LAND", "土地", "平方米"),
	HOUSE("HOUSE", "房产", "套"),
	INVENTORY("INVENTORY", "存货", "万元"),
	EQUIPMENT("EQUIPMENT", "机器设备", "台"),
	FUNDS("FUNDS", "应收帐款", "万元"),
	STOCK("STOCK", "股权", "%");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 对应单位 */
	private final String unit;
	
	/**
	 * 构造一个<code>PledgeTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private PledgeTypeEnum(String code, String message, String unit) {
		this.code = code;
		this.message = message;
		this.unit = unit;
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
	 * @return Returns the unit.
	 */
	public String getUnit() {
		return unit;
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
	 * @return Returns the unit.
	 */
	public String unit() {
		return unit;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return PledgeTypeEnum
	 */
	public static PledgeTypeEnum getByCode(String code) {
		for (PledgeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PledgeTypeEnum>
	 */
	public static List<PledgeTypeEnum> getAllEnum() {
		List<PledgeTypeEnum> list = new ArrayList<PledgeTypeEnum>();
		for (PledgeTypeEnum _enum : values()) {
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
		for (PledgeTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
