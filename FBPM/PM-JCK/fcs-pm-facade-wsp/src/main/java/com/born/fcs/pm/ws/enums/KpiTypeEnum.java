package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户财务评价分类
 * 
 * @author lirz
 *
 * 2016-3-11 上午10:28:42
 */
public enum KpiTypeEnum {
	
	AUDIT_INFO("AUDIT_INFO", "审计信息"),
	FINANCIAL_TABLE("FINANCIAL_TABLE", "财务报表(资产负债)"),
	PROFIT_TABLE("PROFIT_TABLE", "财务报表(利润)"),
	FLOW_TABLE("FLOW_TABLE", "财务报表(现金流量)"),
	
	FINANCIAL_DATA("FINANCIAL_DATA", "财务数据"),
	
	PAY_ABILITY("PAY_ABILITY", "偿债能力"), 
	
	OPERATE_ABILITY("OPERATE_ABILITY", "运营能力"), 
	
	BENIFIT_ABILITY("BENIFIT_ABILITY", "盈利能力"), 
	
	CASH_FLOW("CASH_FLOW", "现金流"),
	
	CONTRACT_PROJECT("CONTRACT_PROJECT", "承包项目"),
	
	HOLDER_BACKGROUND("HOLDER_BACKGROUND", "股东背景"),
	
	LOAN_INDUSTY("LOAN_INDUSTY", "贷款行业"),
	
	CREDIT_STRUCTURE("CREDIT_STRUCTURE", "贷款信用结构"),
	
	LOAN_TIME_LIMIT("LOAN_TIME_LIMIT", "贷款期限集中度"),
	
	LOAN_AMOUNT("LOAN_AMOUNT", "贷款金额集中度"),
	
	AVERAGE_CAPITAL("AVERAGE_CAPITAL", "平均资产情况调查"),
	
	LOAN_QUALITY_LEVEL("LOAN_QUALITY_LEVEL", "贷款质量调查-五级分类情况"),
	
	BAD_LOAN("BAD_LOAN", "贷款质量调查-不良贷款情况"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>KpiTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private KpiTypeEnum(String code, String message) {
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
	 * @return KpiTypeEnum
	 */
	public static KpiTypeEnum getByCode(String code) {
		for (KpiTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<KpiTypeEnum>
	 */
	public static List<KpiTypeEnum> getAllEnum() {
		List<KpiTypeEnum> list = new ArrayList<KpiTypeEnum>();
		for (KpiTypeEnum _enum : values()) {
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
		for (KpiTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
