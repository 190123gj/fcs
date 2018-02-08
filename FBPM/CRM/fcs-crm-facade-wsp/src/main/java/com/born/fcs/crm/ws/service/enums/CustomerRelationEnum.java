package com.born.fcs.crm.ws.service.enums;

/** 客户与我公司关系 */
public enum CustomerRelationEnum {
	DBKH("DBKH", "担保客户"),
	TZYW("TZYW", "投资业务客户"),
	WTDK("WTDK", "委托贷款客户"),
	HZJR("HZJR", "合作金融机构"),
	DBR("DBR", "担保人"),
	GLQY("GLQY", "客户重要关联企业"),
	QT("QT", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private CustomerRelationEnum(String code, String message) {
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
	 * @return CustomerRelationEnum
	 */
	public static CustomerRelationEnum getByCode(String code) {
		for (CustomerRelationEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CustomerRelationEnum>
	 */
	public static java.util.List<CustomerRelationEnum> getAllEnum() {
		java.util.List<CustomerRelationEnum> list = new java.util.ArrayList<CustomerRelationEnum>(
			values().length);
		for (CustomerRelationEnum _enum : values()) {
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
		for (CustomerRelationEnum _enum : values()) {
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
		CustomerRelationEnum _enum = getByCode(code);
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
	public static String getCode(CustomerRelationEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
