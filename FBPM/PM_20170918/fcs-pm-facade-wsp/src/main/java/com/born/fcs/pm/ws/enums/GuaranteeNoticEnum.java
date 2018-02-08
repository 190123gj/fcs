package com.born.fcs.pm.ws.enums;

/**
 * 
 * 保后检查-担保措施检查-关注事项
 *
 * @author Fei
 *
 */
public enum GuaranteeNoticEnum {
	GUARANTEE_NOTIC1("GUARANTEE_NOTIC1", "抵押物是否出租、转租、赠予，形态是否完整，价值、权属是否有变动等", 1),
	GUARANTEE_NOTIC2("GUARANTEE_NOTIC2", "（反）担保企业资产、信用、财务状况是否恶化，反保能力是否下降", 2),
	GUARANTEE_NOTIC3("GUARANTEE_NOTIC3", "（反）担保个人是否有经济、诉讼案件，大额财产是否有变化", 3),
	GUARANTEE_NOTIC4("GUARANTEE_NOTIC4", "（反）担保股权权属和比重是否发生变动", 4),
	GUARANTEE_NOTIC5("GUARANTEE_NOTIC5", "保证金是否挪用、是否足额", 5),
	GUARANTEE_NOTIC0("GUARANTEE_NOTIC0", "", 1000),
	
	;
	
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
	private GuaranteeNoticEnum(String code, String message, int sortOrder) {
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
	 * @return GuaranteeNoticEnum
	 */
	public static GuaranteeNoticEnum getByCode(String code) {
		for (GuaranteeNoticEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<GuaranteeNoticEnum>
	 */
	public static java.util.List<GuaranteeNoticEnum> getAllEnum() {
		java.util.List<GuaranteeNoticEnum> list = new java.util.ArrayList<GuaranteeNoticEnum>(
			values().length);
		for (GuaranteeNoticEnum _enum : values()) {
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
		for (GuaranteeNoticEnum _enum : values()) {
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
		GuaranteeNoticEnum _enum = getByCode(code);
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
	public static String getCode(GuaranteeNoticEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
