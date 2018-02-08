package com.born.fcs.pm.ws.enums;

/**
 * 其他重要事项核查
 *
 *
 * @author Fei
 *
 */
public enum OtherNoticEnum {
	OTHER_NOTIC1("OTHER_NOTIC1", "通过当地信用网站、工商网站、法院系统网站及其他网站或个人查询企业、主要控制人（或股东）是否有诉讼、纠纷、被处罚或其他负面消息",
					1),
	OTHER_NOTIC2(
					"OTHER_NOTIC2",
					"审计报告真伪查询（登录企业所在地会计师协会网站或注册会计师网等查询审计报告真伪；若第三方查询出现问题或者无可用系统时，可直接询问出具审计报告的会计师事务所）",
					2),
	OTHER_NOTIC0("OTHER_NOTIC0", "", 1000), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 显示顺序 */
	private final int sortOrder;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private OtherNoticEnum(String code, String message, int sortOrder) {
		this.code = code;
		this.message = message;
		this.sortOrder = sortOrder;
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
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public int sortOrder() {
		return sortOrder;
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
	 * @return OtherNoticEnum
	 */
	public static OtherNoticEnum getByCode(String code) {
		for (OtherNoticEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<OtherNoticEnum>
	 */
	public static java.util.List<OtherNoticEnum> getAllEnum() {
		java.util.List<OtherNoticEnum> list = new java.util.ArrayList<OtherNoticEnum>(
			values().length);
		for (OtherNoticEnum _enum : values()) {
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
		for (OtherNoticEnum _enum : values()) {
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
		OtherNoticEnum _enum = getByCode(code);
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
	public static String getCode(OtherNoticEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
