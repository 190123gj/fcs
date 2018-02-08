package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财产品结息方式
 *
 * @author wuzj
 */
public enum InterestSettlementWayEnum {
	
	ONE_TIME("ONE_TIME", "一次性还本付息"),
	DAY("DAY", "按日结息"),
	WEEK("WEEK", "按周结息"),
	MONTH("MONTH", "按月结息"),
	SESSON("SEASON", "按季结息"),
	HALF_YEAR("HALF_YEAR", "按半年结息"),
	YEAR("YEAR", "按年结息");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>BusiTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private InterestSettlementWayEnum(String code, String message) {
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
	 * @return BusiTypeEnum
	 */
	public static InterestSettlementWayEnum getByCode(String code) {
		for (InterestSettlementWayEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BusiTypeEnum>
	 */
	public static List<InterestSettlementWayEnum> getAllEnum() {
		List<InterestSettlementWayEnum> list = new ArrayList<InterestSettlementWayEnum>();
		for (InterestSettlementWayEnum _enum : values()) {
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
		for (InterestSettlementWayEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
