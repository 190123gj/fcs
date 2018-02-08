package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 收费基数基准
 * 
 * @author wuzj
 *
 */
public enum ChargeBaseEnum {
	
	LOAN_AMOUNT_THIS_YEAR("LOAN_AMOUNT_THIS_YEAR", "本年度放款金额"),
	
	USE_AMOUNT_THIS_YEAR("USE_AMOUNT_THIS_YEAR", "本年度用款金额"),
	
	/**
	 * 根据业务类型不同描述为
	 * 授信金额、确认发行金额、保全金额
	 */
	AMOUNT("AMOUNT", "金额");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ChargeBaseEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ChargeBaseEnum(String code, String message) {
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
	 * @return ChargeBaseEnum
	 */
	public static ChargeBaseEnum getByCode(String code) {
		for (ChargeBaseEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ChargeBaseEnum>
	 */
	public static List<ChargeBaseEnum> getAllEnum() {
		List<ChargeBaseEnum> list = new ArrayList<ChargeBaseEnum>();
		for (ChargeBaseEnum _enum : values()) {
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
		for (ChargeBaseEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
