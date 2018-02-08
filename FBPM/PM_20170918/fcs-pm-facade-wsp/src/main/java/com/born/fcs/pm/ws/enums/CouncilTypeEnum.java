package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议类型
 * 
 * @author wuzj
 *
 */
public enum CouncilTypeEnum {
	
	PROJECT_REVIEW("PROJECT_REVIEW", "风控委纪要", "项目评审会"),
	
	RISK_HANDLE("RISK_HANDLE", "风控委风险处置专题会纪要", "风险处置会"),
	
	GM_WORKING("GM_WORKING", null, "总经理办公会");
	
	/** 枚举值 */
	private final String code;
	
	/** 会议纪要编号对应的前缀 */
	private final String prefix;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CouncilTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CouncilTypeEnum(String code, String prefix, String message) {
		this.code = code;
		this.prefix = prefix;
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
	 * @return Returns the prefix.
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * @return Returns the prefix.
	 */
	public String prefix() {
		return prefix;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return CouncilTypeEnum
	 */
	public static CouncilTypeEnum getByCode(String code) {
		for (CouncilTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CouncilTypeEnum>
	 */
	public static List<CouncilTypeEnum> getAllEnum() {
		List<CouncilTypeEnum> list = new ArrayList<CouncilTypeEnum>();
		for (CouncilTypeEnum _enum : values()) {
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
		for (CouncilTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
