package com.born.fcs.pm.ws.service.report.field;

public enum FcsFunctionEnum {
	SUM("SUM", "合计", "sum"),
	AVG("AVG", "平均值", "avg"),
	COUNT("COUNT", "计数", "count"),
	MIN("MIN", "最小", "min"),
	MAX("MAX", "最大", "max"),
	NO_GROUP_BY("NO_GROUP_BY", "不添加到group by 里面去", ""),
	COUNT_DISTINCT("COUNT_DISTINCT", "去重复计数", "count distinct");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	/** 函数描述 */
	private final String function;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private FcsFunctionEnum(String code, String message, String function) {
		this.code = code;
		this.message = message;
		this.function = function;
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
	
	public String getFunction() {
		return this.function;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FcsFunctionEnum
	 */
	public static FcsFunctionEnum getByCode(String code) {
		for (FcsFunctionEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FcsFunctionEnum>
	 */
	public static java.util.List<FcsFunctionEnum> getAllEnum() {
		java.util.List<FcsFunctionEnum> list = new java.util.ArrayList<FcsFunctionEnum>(
			values().length);
		for (FcsFunctionEnum _enum : values()) {
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
		for (FcsFunctionEnum _enum : values()) {
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
		FcsFunctionEnum _enum = getByCode(code);
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
	public static String getCode(FcsFunctionEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
