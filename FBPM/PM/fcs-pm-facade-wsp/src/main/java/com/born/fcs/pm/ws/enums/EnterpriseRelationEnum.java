package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 与企业的关系
 * 
 * @author lirz
 *
 * 2016-3-30 下午4:01:25
 */
public enum EnterpriseRelationEnum {
	
	STAKEHOLDER("STAKEHOLDER", "实际控股人"),
	
	STOCKHOLDER("STOCKHOLDER", "股东"),
	
	MANAGER("MANAGER", "管理人员"),
	
	GUARANTOR("GUARANTOR", "担保人员");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>EnterpriseRelationEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private EnterpriseRelationEnum(String code, String message) {
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
	 * @return EnterpriseRelationEnum
	 */
	public static EnterpriseRelationEnum getByCode(String code) {
		for (EnterpriseRelationEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EnterpriseRelationEnum>
	 */
	public static List<EnterpriseRelationEnum> getAllEnum() {
		List<EnterpriseRelationEnum> list = new ArrayList<EnterpriseRelationEnum>();
		for (EnterpriseRelationEnum _enum : values()) {
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
		for (EnterpriseRelationEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
