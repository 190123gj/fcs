package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业规模计算规则 - 指标类型
 *
 * @author wuzj
 */
public enum ScaleRuleKpiTypeEnum {
	
	IN_COME("IN_COME", "万元", "营业收入"),
	
	EMPLOYEE_NUM("EMPLOYEE_NUM", "人", "从业人员"),
	
	TOTAL_ASSET("TOTAL_ASSET", "万元", "资产总额");
	
	/** 枚举值 */
	private final String code;
	
	private final String unit;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>ScaleRuleKpiTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private ScaleRuleKpiTypeEnum(String code, String unit, String message) {
		this.code = code;
		this.unit = unit;
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
	 * @return Returns the message.
	 */
	public String getUnit() {
		return unit;
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
	 * @return Returns the message.
	 */
	public String unit() {
		return unit;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return BusiTypeEnum
	 */
	public static ScaleRuleKpiTypeEnum getByCode(String code) {
		for (ScaleRuleKpiTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<BusiTypeEnum>
	 */
	public static List<ScaleRuleKpiTypeEnum> getAllEnum() {
		List<ScaleRuleKpiTypeEnum> list = new ArrayList<ScaleRuleKpiTypeEnum>();
		for (ScaleRuleKpiTypeEnum _enum : values()) {
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
		for (ScaleRuleKpiTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
