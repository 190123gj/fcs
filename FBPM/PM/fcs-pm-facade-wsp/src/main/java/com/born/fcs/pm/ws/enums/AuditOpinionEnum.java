package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 财务报告审核意见
 * 
 * @author lirz
 *
 * 2016-4-11 上午9:38:36
 */
public enum AuditOpinionEnum {
	
	AUDIT_INFO_1("AUDIT_INFO_1", "无法表示意见的审计报告"),
	AUDIT_INFO_2("AUDIT_INFO_2", "无保留意见的审核报告"),
	AUDIT_INFO_3("AUDIT_INFO_3", "应出具保留意见的审核报告"),
	AUDIT_INFO_4("AUDIT_INFO_4", "否定意见的审核报告"),
	AUDIT_INFO_5("AUDIT_INFO_5", "应出具带强调查事项的无保留意见的审计报告");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>AuditOpinionEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AuditOpinionEnum(String code, String message) {
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
	 * @return AuditOpinionEnum
	 */
	public static AuditOpinionEnum getByCode(String code) {
		for (AuditOpinionEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AuditOpinionEnum>
	 */
	public static List<AuditOpinionEnum> getAllEnum() {
		List<AuditOpinionEnum> list = new ArrayList<AuditOpinionEnum>();
		for (AuditOpinionEnum _enum : values()) {
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
		for (AuditOpinionEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
