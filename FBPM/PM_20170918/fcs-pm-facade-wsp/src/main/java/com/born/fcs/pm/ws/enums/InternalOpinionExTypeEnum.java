package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 合规检查
 * 
 */
public enum InternalOpinionExTypeEnum {
	
	WP_CONFIRM("WP_CONFIRM", "《合规检查工作底稿》确认表"),
	REPORT_SEEK_OP("REPORT_SEEK_OP", "《合规检查报告》征求意见表"),
	REPORT_DELI("REPORT_DELI", "《合规检查报告》送达表");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>InternalOpinionExTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private InternalOpinionExTypeEnum(String code, String message) {
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
	 * @return InternalOpinionExTypeEnum
	 */
	public static InternalOpinionExTypeEnum getByCode(String code) {
		for (InternalOpinionExTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<InternalOpinionExTypeEnum>
	 */
	public static List<InternalOpinionExTypeEnum> getAllEnum() {
		List<InternalOpinionExTypeEnum> list = new ArrayList<InternalOpinionExTypeEnum>();
		for (InternalOpinionExTypeEnum _enum : values()) {
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
		for (InternalOpinionExTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
