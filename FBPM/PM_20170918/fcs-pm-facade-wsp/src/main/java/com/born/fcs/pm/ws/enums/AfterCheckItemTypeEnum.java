package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 保后检查报告 科目类型
 * 
 * @author lirz
 *
 * 2016-6-4 下午2:58:30
 */
public enum AfterCheckItemTypeEnum {
	
	INCOME("INCOME", "企业收入核实情况工作底稿"),
	
	COST("COST", "企业成本核实情况工作底稿"), 
	
	COUNTER("COUNTER", "（反）担保措施检查"), 
	
	OTHER("OTHER", "其他重要事项核查"), 
	
	WARNING("WARNING", "预警信号或关注事项"), 
	
	COST_CONSIST_1("COST_CONSIST_1", "成本构成1"),
	
	COST_CONSIST_2("COST_CONSIST_2", "成本构成2"),
	
	COST_CONSIST_3("COST_CONSIST_3", "成本构成3"),

	INVENTORY_1("INVENTORY_1", "存货1"),
	
	INVENTORY_2("INVENTORY_2", "存货2"),
	
	INVENTORY_3("INVENTORY_3", "存货3")

	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>AfterCheckItemTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private AfterCheckItemTypeEnum(String code, String message) {
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
	 * @return AfterCheckItemTypeEnum
	 */
	public static AfterCheckItemTypeEnum getByCode(String code) {
		for (AfterCheckItemTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<AfterCheckItemTypeEnum>
	 */
	public static List<AfterCheckItemTypeEnum> getAllEnum() {
		List<AfterCheckItemTypeEnum> list = new ArrayList<AfterCheckItemTypeEnum>();
		for (AfterCheckItemTypeEnum _enum : values()) {
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
		for (AfterCheckItemTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
