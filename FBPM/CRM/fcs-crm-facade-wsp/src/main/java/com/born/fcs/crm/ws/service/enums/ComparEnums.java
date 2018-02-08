package com.born.fcs.crm.ws.service.enums;

/**
 * 比较枚举
 * */
public enum ComparEnums {
	LESS_THAN("<", "<", "小于"),
	LESS_THAN_EQUAL("<=", "≤", "小于等于"),
	EQUAL("==", "=", "等于"),
	GREATER_THAN_EQUAL(">=", "≥", "大于等于"),
	GREATER_THAN(">", ">", "大于");
	
	/** 枚举值 */
	private final String code;
	/** 展示样子 */
	private String showStr;
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private ComparEnums(String code, String showStr, String message) {
		this.code = code;
		this.showStr = showStr;
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
	
	public String showStr() {
		return showStr;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ComparEnums
	 */
	public static ComparEnums getByCode(String code) {
		for (ComparEnums _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ComparEnums>
	 */
	public static java.util.List<ComparEnums> getAllEnum() {
		java.util.List<ComparEnums> list = new java.util.ArrayList<ComparEnums>(values().length);
		for (ComparEnums _enum : values()) {
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
		for (ComparEnums _enum : values()) {
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
		ComparEnums _enum = getByCode(code);
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
	public static String getCode(ComparEnums _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
