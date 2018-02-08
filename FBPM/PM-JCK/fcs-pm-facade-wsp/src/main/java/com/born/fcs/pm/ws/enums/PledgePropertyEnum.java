package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 抵押品性质
 * 
 * @author lirz
 *
 * 2016-3-9 下午4:06:26
 */
public enum PledgePropertyEnum {
	
	SELL("SELL", "出让"),
	TRANSFER("TRANSFER", "划拨"),
	RESERVE("RESERVE", "储备"), 
	
	HOUSE("HOUSE", "住房"),
	RESIDENTIAL("RESIDENTIAL", "商住"),
	BUSINESS("BUSINESS", "商业"), 
	
	SUPERVISE("SUPERVISE", "监管"),
	UN_SUPERVISE("UN_SUPERVISE", "不监管"),
	
	GENERAL("GENERAL", "通用"),
	SPECIAL("SPECIAL", "专用"),
	
	SIGNED("SIGNED", "已签署第三方协议"),
	UN_SIGNED("UN_SIGNED", "未签署第三方协议"),
	
	CIRCULATE("CIRCULATE", "流通"),
	LIMITED("LIMITED", "限售");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>PledgePropertyEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private PledgePropertyEnum(String code, String message) {
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
	 * @return PledgePropertyEnum
	 */
	public static PledgePropertyEnum getByCode(String code) {
		for (PledgePropertyEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PledgePropertyEnum>
	 */
	public static List<PledgePropertyEnum> getAllEnum() {
		List<PledgePropertyEnum> list = new ArrayList<PledgePropertyEnum>();
		for (PledgePropertyEnum _enum : values()) {
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
		for (PledgePropertyEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
