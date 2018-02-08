package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财项目会议类型
 * 
 * @author wuzj
 *
 */
public enum ProjectCouncilEnum {
	
	SELF_GW("SELF_GW", "总经理办公会"),
	
	SELF_PR("SELF_PR", "项目评审会"),
	
	SELF_MOTHER_GW("SELF_MOTHER_GW", "本公司总经理办公会+母公司总经理办公会"),
	
	SELF_GW_MONTHER_PR("SELF_GW_MONTHER_PR", "本公司总经理办公会+母公司项目评审会");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProjectCouncilEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectCouncilEnum(String code, String message) {
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
	 * @return FinancialProjectCouncilEnum
	 */
	public static ProjectCouncilEnum getByCode(String code) {
		for (ProjectCouncilEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProjectCouncilEnum>
	 */
	public static List<ProjectCouncilEnum> getAllEnum() {
		List<ProjectCouncilEnum> list = new ArrayList<ProjectCouncilEnum>();
		for (ProjectCouncilEnum _enum : values()) {
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
		for (ProjectCouncilEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
