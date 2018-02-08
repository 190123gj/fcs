/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 评估公司状态
 * 
 * @author Ji
 *
 */
public enum AssessCompanyApplyStatusEnum {
	
	NOT_SPECIFIED("NOT_SPECIFIED", "未指定"),
	SPECIFIED_AUDIT("SPECIFIED_AUDIT", "指定审核"),
	SPECIFIED_UNDO("SPECIFIED_UNDO", "指定撤回"),
	SPECIFIED_REJECT("SPECIFIED_REJECT", "指定驳回"),
	SPECIFIED_AUDIT_NO_PASS("SPECIFIED_AUDIT_NO_PASS", "指定审核不通过"),
	SPECIFIED_AUDIT_PASS("SPECIFIED_AUDIT_PASS", "指定审核通过"),
	CHANGE_UNDO("CHANGE_UNDO", "更换撤销"),
	CHANGE_REJECT("CHANGE_REJECT", "更换驳回"),
	CHANGE_AUDIT("CHANGE_AUDIT", "更换审核"),
	CHANGE_AUDIT_NO_PASS("CHANGE_AUDIT_NO_PASS", "更换审核不通过"),
	CHANGE_AUDIT_PASS("CHANGE_AUDIT_PASS", "更换审核通过"),
	BE_CHANGE("BE_CHANGE", "被更换中"),
	BE_CHANGE_FINISH("BE_CHANGE_FINISH", "被更换通过"),
	BE_CHANGE_NOT_FINISH("BE_CHANGE_NOT_FINISH", "被更换不通过");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>BooleanEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AssessCompanyApplyStatusEnum(String code, String message) {
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
	 * @return BooleanEnum
	 */
	public static AssessCompanyApplyStatusEnum getByCode(String code) {
		for (AssessCompanyApplyStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BooleanEnum>
	 */
	public static List<AssessCompanyApplyStatusEnum> getAllEnum() {
		List<AssessCompanyApplyStatusEnum> list = new ArrayList<AssessCompanyApplyStatusEnum>();
		for (AssessCompanyApplyStatusEnum _enum : values()) {
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
		for (AssessCompanyApplyStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
