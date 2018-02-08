package com.born.fcs.crm.ws.service.enums;

/**
 * 评价标准类型
 * */
public enum StandardTypeEnums {
	YBQY("YBQY", "一般企业类"),
	CTZG("CTZG", "城市开发企业类-主观部分"),
	CTBZ("CTBZ", "城市开发企业类-标准值比较部分"),
	CTCW("CTCW", "城市开发企业类-财务部分"),
	GYWBZ("GYWBZ", "公用事业类-无标准"),
	GYYBZ("GYYBZ", "公用事业类-有标准"),
	CW_ZH("CW_ZH", "综合类"),
	CW_GY("CW_GY", "工业类"),
	CW_NY("CW_NY", "农业类"),
	CW_SM("CW_SM", "商贸类"),
	CW_JA("CW_JA", "建安类"),
	CW_JT("CW_JT", "交通运输类"),
	CW_FC("CW_FC", "房产开发类"),
	TZSX("TZSX", "调整事项"),
	ZBXY("ZBXY", "资本信用指标"), ;
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private StandardTypeEnums(String code, String message) {
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
	 * @return StandardTypeEnums
	 */
	public static StandardTypeEnums getByCode(String code) {
		for (StandardTypeEnums _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<StandardTypeEnums>
	 */
	public static java.util.List<StandardTypeEnums> getAllEnum() {
		java.util.List<StandardTypeEnums> list = new java.util.ArrayList<StandardTypeEnums>(
			values().length);
		for (StandardTypeEnums _enum : values()) {
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
		for (StandardTypeEnums _enum : values()) {
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
		StandardTypeEnums _enum = getByCode(code);
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
	public static String getCode(StandardTypeEnums _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
