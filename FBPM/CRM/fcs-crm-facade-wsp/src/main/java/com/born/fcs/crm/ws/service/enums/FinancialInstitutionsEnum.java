package com.born.fcs.crm.ws.service.enums;

/** 金融机构属性 */
public enum FinancialInstitutionsEnum {
	ZCXYH("ZCXYH", "政策性银行及邮储银行"),
	GYSY("GYSY", "国有商业银行"),
	GFYH("GFYH", "股份制商业银行"),
	CSSY("CSSY", "城市商业银行"),
	NCJR("NCJR", "农村金融机构"),
	QTJR("QTJR", "其他金融机构");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private FinancialInstitutionsEnum(String code, String message) {
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
	 * @return FinancialInstitutionsEnum
	 */
	public static FinancialInstitutionsEnum getByCode(String code) {
		for (FinancialInstitutionsEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialInstitutionsEnum>
	 */
	public static java.util.List<FinancialInstitutionsEnum> getAllEnum() {
		java.util.List<FinancialInstitutionsEnum> list = new java.util.ArrayList<FinancialInstitutionsEnum>(
			values().length);
		for (FinancialInstitutionsEnum _enum : values()) {
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
		for (FinancialInstitutionsEnum _enum : values()) {
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
		FinancialInstitutionsEnum _enum = getByCode(code);
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
	public static String getCode(FinancialInstitutionsEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
