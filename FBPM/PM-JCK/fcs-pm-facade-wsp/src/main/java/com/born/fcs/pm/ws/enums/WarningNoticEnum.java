package com.born.fcs.pm.ws.enums;

/**
 * 预警信号或关注事项
 *
 *
 * @author Fei
 *
 */
public enum WarningNoticEnum {
	WARNING_NOTIC1("WARNING_NOTIC1", "财务报表是否明显不实", 1),
	WARNING_NOTIC2("WARNING_NOTIC2", "应收账款是否大幅增加，收回拖延", 2),
	WARNING_NOTIC3("WARNING_NOTIC3", "存货是否较大，有无突然增加", 3),
	WARNING_NOTIC4("WARNING_NOTIC4", "银行债务或者或有负债是否大量增加", 4),
	WARNING_NOTIC5("WARNING_NOTIC5", "资产负债结构是否有重大变化", 5),
	WARNING_NOTIC6("WARNING_NOTIC6", "流动比是否远低于正常值", 6),
	WARNING_NOTIC7("WARNING_NOTIC7", "短借长用情况是否显著", 7),
	WARNING_NOTIC8("WARNING_NOTIC8", "货币基金余额是否不断下降", 8),
	WARNING_NOTIC9("WARNING_NOTIC9", "是否拖延支付利息或费用", 9),
	WARNING_NOTIC10("WARNING_NOTIC10", "是否有民间融资", 10),
	WARNING_NOTIC0("WARNING_NOTIC0", "", 1000),
	
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
	private WarningNoticEnum(String code, String message, int sortOrder) {
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
	 * @return WarningNoticEnum
	 */
	public static WarningNoticEnum getByCode(String code) {
		for (WarningNoticEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<WarningNoticEnum>
	 */
	public static java.util.List<WarningNoticEnum> getAllEnum() {
		java.util.List<WarningNoticEnum> list = new java.util.ArrayList<WarningNoticEnum>(
			values().length);
		for (WarningNoticEnum _enum : values()) {
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
		for (WarningNoticEnum _enum : values()) {
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
		WarningNoticEnum _enum = getByCode(code);
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
	public static String getCode(WarningNoticEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
