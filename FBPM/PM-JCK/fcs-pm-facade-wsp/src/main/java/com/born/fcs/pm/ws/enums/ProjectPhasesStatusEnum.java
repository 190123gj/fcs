package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目状态
 *
 * @author wuzj
 */
public enum ProjectPhasesStatusEnum {
	
	WAITING("WAITING", "待填写"),
	
	DRAFT("DRAFT", "填写中"),
	
	AUDITING("AUDITING", "审核中"),
	
	NOPASS("NOPASS", "未通过"),
	
	APPROVAL("APPROVAL", "通过");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectStatusEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectPhasesStatusEnum(String code, String message) {
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
	 * @return EnterpriseNatureEnum
	 */
	public static ProjectPhasesStatusEnum getByCode(String code) {
		for (ProjectPhasesStatusEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EnterpriseNatureEnum>
	 */
	public static List<ProjectPhasesStatusEnum> getAllEnum() {
		List<ProjectPhasesStatusEnum> list = new ArrayList<ProjectPhasesStatusEnum>();
		for (ProjectPhasesStatusEnum _enum : values()) {
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
		for (ProjectPhasesStatusEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
