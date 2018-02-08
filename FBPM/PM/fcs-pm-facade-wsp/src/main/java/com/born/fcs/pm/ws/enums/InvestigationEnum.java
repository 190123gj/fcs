package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 尽调页页签
 *
 * @author lirz
 * 
 * 2017-3-24 上午11:08:42
 *
 */
public enum InvestigationEnum {
	
	DECLARE("DECLARE", "声明"),
	
	CREDIT_SCHEME("CREDIT_SCHEME", "授信方案"),
	MAINLY_REVIEW("MAINLY_REVIEW", "客户主体评价"),
	MABILITY_REVIEW("MABILITY_REVIEW", "客户管理能力评价"),
	OPABILITY_REVIEW("OPABILITY_REVIEW", "客户经营能力评价"),
	FINANCIAL_REVIEW("FINANCIAL_REVIEW", "客户财务评价"),
	MAJOR_EVENT("MAJOR_EVENT", "重大事项调查"),
	PROJECT_STATUS("PROJECT_STATUS", "项目情况调查"),
	CS_RATIONALITY_REVIEW("CS_RATIONALITY_REVIEW", "授信方案合理性评价"),
	RISK("RISK", "风险点分析和结论意见"),
	
	LITIGATION("LITIGATION", "诉讼保函"),
	
	UNDERWRITING("UNDERWRITING", "承销");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CheckPointEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private InvestigationEnum(String code, String message) {
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
	 * @return CheckPointEnum
	 */
	public static InvestigationEnum getByCode(String code) {
		for (InvestigationEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CheckPointEnum>
	 */
	public static List<InvestigationEnum> getAllEnum() {
		List<InvestigationEnum> list = new ArrayList<InvestigationEnum>();
		for (InvestigationEnum _enum : values()) {
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
		for (InvestigationEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
