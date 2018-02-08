package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目移交类型
 *
 * @author wuzj
 */
public enum ProjectTransferTypeEnum {
	
	BUSI_MANAGER("BUSI_MANAGER", "客户经理"),
	
	RISK_MANAGER("RISK_MANAGER", "风险经理"),
	
	LEGAL_MANAGER("LEGAL_MANAGER", "法务经理"),
	
	LEGAL_DEPT("LEGAL_DEPT", "移交至法务部"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ProjectTransferTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ProjectTransferTypeEnum(String code, String message) {
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
	 * @return ProjectTransferTypeEnum
	 */
	public static ProjectTransferTypeEnum getByCode(String code) {
		for (ProjectTransferTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ProjectTransferTypeEnum>
	 */
	public static List<ProjectTransferTypeEnum> getAllEnum() {
		List<ProjectTransferTypeEnum> list = new ArrayList<ProjectTransferTypeEnum>();
		for (ProjectTransferTypeEnum _enum : values()) {
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
		for (ProjectTransferTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
