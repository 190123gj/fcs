/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:29:09 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 
 * 公司和公司deptcode
 * @author hjiajie
 * 
 */
public enum ReportCompanyEnum {
	
	/** 重庆进出口信用担保有限公司 */
	MOTHER("MOTHER", "CQZB", "重庆进出口信用担保有限公司"),
	
	/** 重庆信惠投资有限公司 */
	XINHUI("XINHUI", "XHGS", "重庆信惠投资有限公司"),
	
	/** SPC公司 */
	SPC("SPC", "XHTZ_CB,XHTZ_CX,XHTZ_CR,XHTZ_CZ,XHTZ_CY", "SPC公司"),
	/** 诚本公司 */
	XHTZ_CB("XHTZ_CB", "XHTZ_CB", "诚本公司"),
	/** 诚鑫公司 */
	XHTZ_CX("XHTZ_CX", "XHTZ_CX", "诚鑫公司"),
	/** 诚融公司 */
	XHTZ_CR("XHTZ_CR", "XHTZ_CR", "诚融公司"),
	/** 诚正公司 */
	XHTZ_CZ("XHTZ_CZ", "XHTZ_CZ", "诚正公司"),
	/** 诚远公司 */
	XHTZ_CY("XHTZ_CY", "XHTZ_CY", "诚远公司"),
	
	/** 重庆进出口信用担保有限公司北京分公司 */
	BEIJING("BEIJING", "0400", "重庆进出口信用担保有限公司北京分公司"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	private final String deptCode;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ReportCompanyEnum(String code, String deptCode, String message) {
		this.code = code;
		this.deptCode = deptCode;
		this.message = message;
	}
	
	public String getDeptCode() {
		return this.deptCode;
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
	 * @return ReportCompanyEnum
	 */
	public static ReportCompanyEnum getByCode(String code) {
		for (ReportCompanyEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ReportCompanyEnum>
	 */
	public static java.util.List<ReportCompanyEnum> getAllEnum() {
		java.util.List<ReportCompanyEnum> list = new java.util.ArrayList<ReportCompanyEnum>(
			values().length);
		for (ReportCompanyEnum _enum : values()) {
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
		for (ReportCompanyEnum _enum : values()) {
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
		ReportCompanyEnum _enum = getByCode(code);
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
	public static String getCode(ReportCompanyEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
