package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后 - 诉讼保函 - 案件状态
 *
 *
 * @author lirz
 *
 * 2016-7-22 上午11:41:03
 */
public enum CaseStatusEnum {
	
	
	WIN("WIN", "胜诉"),
	LOSE("LOSE", "败诉"),
	WITHDRAW("WITHDRAW", "客户撤诉"),
	AGAIN("AGAIN", "二审");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CaseStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CaseStatusEnum(String code, String message) {
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
	 * @return CaseStatusEnum
	 */
	public static CaseStatusEnum getByCode(String code) {
		for (CaseStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CaseStatusEnum>
	 */
	public static List<CaseStatusEnum> getAllEnum() {
		List<CaseStatusEnum> list = new ArrayList<CaseStatusEnum>();
		for (CaseStatusEnum _enum : values()) {
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
		for (CaseStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
