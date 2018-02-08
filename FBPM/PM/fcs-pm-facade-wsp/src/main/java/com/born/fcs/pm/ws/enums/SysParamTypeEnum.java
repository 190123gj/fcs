/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 系统参数类型枚举
 * @author hjiajie
 * 
 */
public enum SysParamTypeEnum {
	
	/** 默认 */
	DEFAULT("DEFAULT", "默认"),
	/** OA系统 */
	OA_SYSTEM("OA_SYSTEM", "OA系统"),
	/** 财务系统 */
	ACCOUNT_SYSTEM("ACCOUNT_SYSTEM", "财务系统"),
	/** 人资系统 */
	PERSON_SYSTEM("PERSON_SYSTEM", "人资系统"),
	/** 文档系统 */
	MICROSOFT_SYSTEM("MICROSOFT_SYSTEM", "文档系统"),
	/** 老业务系统 */
	OLD_PROJECT_SYSTEM("OLD_PROJECT_SYSTEM", "老业务系统"),
	/** 风险监控系统 */
	RISK_SYSTEM("RISK_SYSTEM", "风险监控系统"),
	/** 子系统配置 */
	CHILD_PROJECT_SYSTEM("CHILD_PROJECT_SYSTEM", "子系统配置"),
	/** OA系统数据对接配置 */
	OA_SYSTEM_PARAM("OA_SYSTEM_PARAM", "OA系统数据对接配置"),
	/** 财务系统对接配置 */
	ACCOUNT_SYSTEM_PARAM("ACCOUNT_SYSTEM_PARAM", "财务系统对接配置"),
	
	/** 人资系统对接配置 */
	PERSON_SYSTEM_PARAM("PERSON_SYSTEM_PARAM", "人资系统对接配置"),
	/** 文档系统对接配置 */
	MICROSOFT_SYSTEM_PARAM("MICROSOFT_SYSTEM_PARAM", "文档系统对接配置"),
	/** 老业务系统对接配置 */
	OLD_PROJECT_SYSTEM_PARAM("OLD_PROJECT_SYSTEM_PARAM", "老业务系统对接配置"),
	/** 风险监控系统对接配置 */
	RISK_SYSTEM_PARAM("RISK_SYSTEM_PARAM", "风险监控系统对接配置"), ;
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
	private SysParamTypeEnum(String code, String message) {
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
	public static SysParamTypeEnum getByCode(String code) {
		for (SysParamTypeEnum _enum : values()) {
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
	public static List<SysParamTypeEnum> getAllEnum() {
		List<SysParamTypeEnum> list = new ArrayList<SysParamTypeEnum>();
		for (SysParamTypeEnum _enum : values()) {
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
		for (SysParamTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
