package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 授信方案 - （反）担保措施
 * 
 * @author lirz
 *
 * 2016-3-9 下午3:56:57
 */
public enum GuaranteeTypeEnum {
	
	PLEDGE("PLEDGE", "抵押"),
	
	MORTGAGE("MORTGAGE", "质押");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>GuaranteeTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private GuaranteeTypeEnum(String code, String message) {
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
	 * @return GuaranteeTypeEnum
	 */
	public static GuaranteeTypeEnum getByCode(String code) {
		for (GuaranteeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<GuaranteeTypeEnum>
	 */
	public static List<GuaranteeTypeEnum> getAllEnum() {
		List<GuaranteeTypeEnum> list = new ArrayList<GuaranteeTypeEnum>();
		for (GuaranteeTypeEnum _enum : values()) {
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
		for (GuaranteeTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
