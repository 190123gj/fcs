package com.born.fcs.pm.ws.enums;

public enum CheckReportEditionEnums {
	V_COMMON("V_COMMON", "通用版", "AFTERWARDS_CHECK_COMMON"),
	V_REAL_ESTATE("V_REAL_ESTATE", "房地产开发版", "AFTERWARDS_CHECK_REAL_ESTATE"),
	V_CONSTRUCTION("V_CONSTRUCTION", "建筑行业版", "AFTERWARDS_CHECK_CONSTRUCTION"),
	V_MANUFACTURING("V_MANUFACTURING", "生产制造业版", "AFTERWARDS_CHECK_MANUFACTURING"),
	V_LOAN("V_LOAN", "小贷公司版", "AFTERWARDS_CHECK_LOAN"),
	V_CITY("V_CITY", "城市开发版", "AFTERWARDS_CHECK_CITY"),
//	V_FIXED_ASSETS("V_FIXED_ASSETS", "固定资产授信版", "AFTERWARDS_CHECK_FIXED_ASSETS"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 对应表单类型 */
	private final String formCode;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private CheckReportEditionEnums(String code, String message, String formCode) {
		this.code = code;
		this.message = message;
		this.formCode = formCode;
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
	
	public String getFormCode() {
		return formCode;
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
	
	public String formCode() {
		return formCode;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return CheckReportEditionEnums
	 */
	public static CheckReportEditionEnums getByCode(String code) {
		for (CheckReportEditionEnums _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CheckReportEditionEnums>
	 */
	public static java.util.List<CheckReportEditionEnums> getAllEnum() {
		java.util.List<CheckReportEditionEnums> list = new java.util.ArrayList<CheckReportEditionEnums>(
			values().length);
		for (CheckReportEditionEnums _enum : values()) {
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
		for (CheckReportEditionEnums _enum : values()) {
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
		CheckReportEditionEnums _enum = getByCode(code);
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
	public static String getCode(CheckReportEditionEnums _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
