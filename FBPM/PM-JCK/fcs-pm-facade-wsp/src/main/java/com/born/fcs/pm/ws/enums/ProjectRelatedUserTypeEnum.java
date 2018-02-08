package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目相关人员类型
 * 
 * @author wuzj
 *
 */
public enum ProjectRelatedUserTypeEnum {
	
	BUSI_MANAGER("BUSI_MANAGER", "业务经理"),
	
	BUSI_MANAGERB("BUSI_MANAGERB", "业务经理B角"),
	
	RISK_MANAGER("RISK_MANAGER", "风险经理"),
	
	LEGAL_MANAGER("LEGAL_MANAGER", "法务经理"),
	
	COUNCIL_JUDGE("COUNCIL_JUDGE", "参会评委"),
	
	RISK_TEAM_MEMBER("RISK_TEAM_MEMBER", "风险处置小组成员"),
	
	MANUAL_GRANT("MANUAL_GRANT", "授权人员"),
	
	FLOW_RELATED("FLOW_RELATED", "流程人员"),
	
	FLOW_RELATED_SUB_SYSTEM("FLOW_RELATED_SUB_SYSTEM", "子系统流程人员"),
	
	OTHER("OTHER", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectRelatedUserTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectRelatedUserTypeEnum(String code, String message) {
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
	 * @return ProjectRelatedUserTypeEnum
	 */
	public static ProjectRelatedUserTypeEnum getByCode(String code) {
		for (ProjectRelatedUserTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectRelatedUserTypeEnum>
	 */
	public static List<ProjectRelatedUserTypeEnum> getAllEnum() {
		List<ProjectRelatedUserTypeEnum> list = new ArrayList<ProjectRelatedUserTypeEnum>();
		for (ProjectRelatedUserTypeEnum _enum : values()) {
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
		for (ProjectRelatedUserTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
