package com.born.fcs.pm.ws.enums;

public enum SysDateSeqNameEnum {
	
	PROJECT_CODE_SEQ("PROJECT_CODE_SEQ", "项目编号"),
	
	COUNCIL_CODE_SEQ("COUNCIL_CODE_SEQ", "会议编号"),
	
	AFTERWARDS_CHECK_SEQ("AFTERWARDS_CHECK_SEQ", "保后检查编号"),
	
	COUNCIL_SUMMARY_CODE_SEQ("COUNCIL_SUMMARY_CODE_SEQ", "会议纪要编号"),
	
	COUNCIL_SP_CODE_SEQ("COUNCIL_SP_CODE_SEQ", "会议纪要项目批复编号"),
	
	STAMP_APPLY_CODE_SEQ("STAMP_APPLY_CODE_SEQ", "用印申请单编号"),
	
	PROJECT_CONTRACT_CODE_SEQ("PROJECT_CONTRACT_CODE_SEQ", "合同编号"),

	PROJECT_WRIT_CODE_SEQ("PROJECT_WRIT_CODE_SEQ", "文书编号"),

	PROJECT_LETTER_CODE_SEQ("PROJECT_LETTER_CODE_SEQ", "函/通知书编号"),
	
	CHARGE_NOTIFICATION_CODE_SEQ("CHARGE_NOTIFICATION_CODE_SEQ", "收费通知单号"),
	
	FILE_CODE_SEQ("FILE_CODE_SEQ", "档案编号"),
	
	EXPIRE_PROJECT_NOTICE_TEMPLATE_SEQ("EXPIRE_PROJECT_NOTICE_TEMPLATE_SEQ", "到期项目通知模板序号"),
	
	UNDERWRITING_PROJECT_NOTICE_TEMPLATE_SEQ("UNDERWRITING_PROJECT_NOTICE_TEMPLATE_SEQ",
												"承销项目到期项目通知模板序号"),
	
	FORM_CHANGE_APPLY_CODE("FORM_CHANGE_APPLY_CODE", "签报编号"),
	
	BROKER_BUSINESS_CODE("BROKER_BUSINESS_CODE", "经纪业务编号"),
	
	INTERNAL_OPINION_EXCHANGE_CODE("INTERNAL_OPINION_EXCHANGE_CODE", "内控合规检查编号"),

	COUNCIL_RISK_CODE_SEQ("COUNCIL_RISK_CODE_SEQ", "风险管控小组会议"),
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SysDateSeqNameEnum(String code, String message) {
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
	 * @return SysDateSeqNameEnum
	 */
	public static SysDateSeqNameEnum getByCode(String code) {
		for (SysDateSeqNameEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SysDateSeqNameEnum>
	 */
	public static java.util.List<SysDateSeqNameEnum> getAllEnum() {
		java.util.List<SysDateSeqNameEnum> list = new java.util.ArrayList<SysDateSeqNameEnum>(
			values().length);
		for (SysDateSeqNameEnum _enum : values()) {
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
		for (SysDateSeqNameEnum _enum : values()) {
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
		SysDateSeqNameEnum _enum = getByCode(code);
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
	public static String getCode(SysDateSeqNameEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
