package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 评分分类
 * 
 * @author lirz
 *
 * 2016-5-18 下午8:44:24
 */
public enum EvaluationTypeEnum {
	
	ENTERPRISE("ENTERPRISE", "企业综合评价"),
	PROJECT("PROJECT", "项目指标情况及项目能力评价"),
	COUNTER("COUNTER", "反担保措施");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>EvaluationTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private EvaluationTypeEnum(String code, String message) {
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
	 * @return EvaluationTypeEnum
	 */
	public static EvaluationTypeEnum getByCode(String code) {
		for (EvaluationTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<EvaluationTypeEnum>
	 */
	public static List<EvaluationTypeEnum> getAllEnum() {
		List<EvaluationTypeEnum> list = new ArrayList<EvaluationTypeEnum>();
		for (EvaluationTypeEnum _enum : values()) {
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
		for (EvaluationTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
