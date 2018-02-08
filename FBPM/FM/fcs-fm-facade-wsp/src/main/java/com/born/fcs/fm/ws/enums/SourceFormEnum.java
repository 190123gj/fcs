/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:49:44 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 
 * 来源单据
 * @author wuzj
 * 
 */
public enum SourceFormEnum {
	
	CHARGE_NOTIFICATION("CHARGE_NOTIFICATION", "RECEIPT", "收费通知单"),
	
	FINANCIAL_REDEEM("FINANCIAL_REDEEM", "RECEIPT", "理财产品提前赎回"),
	
	FINANCIAL_REDEEM_EXPIRE("FINANCIAL_REDEEM_EXPIRE", "RECEIPT", "理财产品到期赎回"),
	
	FINANCIAL_TRANSFER("FINANCIAL_TRANSFER", "RECEIPT", "理财产品转让"),
	
	FINANCIAL_SETTLEMENT("FINANCIAL_SETTLEMENT", "RECEIPT", "理财产品结息"),
	
	CAPITAL_APPROPRIATION("CAPITAL_APPROPRIATION", "PAYMENT", "资金划付单"),
	
	CAPITAL_APPROPRIATION_COMP("CAPITAL_APPROPRIATION_COMP", "PAYMENT", "被扣划冻结申请单"),
	
	LOAN_USE("LOAN_USE", "PAYMENT", "放用款申请单"),
	
	REFUND("REFUND", "PAYMENT", "退费申请单"),
	
	INNER_LOAN("INNER_LOAN", "PAYMENT", "内部借款单"),
	LABOUR_CAPITAL("LABOUR_CAPITAL", "PAYMENT", "内部借款单"),
	INNER_LOAN_IN("INNER_LOAN_IN", "RECEIPT", "内部借款单"), ;
	
	/** 枚举值 */
	private final String code;
	
	private final String type;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SourceFormEnum(String code, String type, String message) {
		this.code = code;
		this.type = type;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
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
	 * @return Returns the type.
	 */
	public String type() {
		return type;
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
	 * @return SourceFormEnum
	 */
	public static SourceFormEnum getByCode(String code) {
		for (SourceFormEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SourceFormEnum>
	 */
	public static java.util.List<SourceFormEnum> getAllEnum() {
		java.util.List<SourceFormEnum> list = new java.util.ArrayList<SourceFormEnum>(
			values().length);
		for (SourceFormEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (SourceFormEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	/**
	 * 通过code获取msg
	 * @param code 枚举值
	 * @return
	 */
	public static String getMsgByCode(String code) {
		if (code == null) {
			return null;
		}
		SourceFormEnum _enum = getByCode(code);
		if (_enum == null) {
			return null;
		}
		return _enum.getMessage();
	}
	
	/**
	 * 获取枚举code
	 * @param _enum
	 * @return
	 */
	public static String getCode(SourceFormEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
