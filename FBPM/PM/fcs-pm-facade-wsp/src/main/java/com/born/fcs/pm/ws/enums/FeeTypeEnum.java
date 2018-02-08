package com.born.fcs.pm.ws.enums;

/**
 * 费用类型
 *
 *
 * @author Fei
 *
 */
public enum FeeTypeEnum {
	OTHER("OTHER", "其他费用"),
	GUARANTEE_FEE("GUARANTEE_FEE", "担保费"),
	PROJECT_REVIEW_FEE("PROJECT_REVIEW_FEE", "项目评审费"),
	RECOVERY_INCOME("RECOVERY_INCOME", "追偿收入"),
	COMPENSATORY_PRINCIPAL_WITHDRAWAL("COMPENSATORY_PRINCIPAL_WITHDRAWAL", "代偿款本金收回"),
	COMPENSATORY_INTEREST_WITHDRAWAL("COMPENSATORY_INTEREST_WITHDRAWAL", "代偿款利息收回"),
	COMPENSATORY_DEDIT_WITHDRAWAL("COMPENSATORY_DEDIT_WITHDRAWAL", "代偿违约金收回"),
	COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL("COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL", "代偿罚息收回"),
	COMPENSATORY_OTHER_WITHDRAWAL("COMPENSATORY_OTHER_WITHDRAWAL", "代偿其他收回"),
	ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL("ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL", "委贷本金收回"),
	ENTRUSTED_LOAN_INTEREST_WITHDRAWAL("ENTRUSTED_LOAN_INTEREST_WITHDRAWAL", "委贷利息收回"),
	ENTRUSTED_LOAN_POUNDAGE("ENTRUSTED_LOAN_POUNDAGE", "委贷手续费"),
	ENTRUSTED_LOAN_IQUIDATED_DAMAGES("ENTRUSTED_LOAN_IQUIDATED_DAMAGES", "委贷违约金"),
	CONSULT_FEE("CONSULT_FEE", "顾问费"),
	CONSIGNMENT_INWARD_INCOME("CONSIGNMENT_INWARD_INCOME", "承销收入"),
	SOLD_INCOME("SOLD_INCOME", "经纪收入"),
	REFUNDABLE_DEPOSITS_DRAW_BACK("REFUNDABLE_DEPOSITS_DRAW_BACK", "存出保证金划回"),
	GUARANTEE_DEPOSIT("GUARANTEE_DEPOSIT", "存入保证金"),
	ASSETS_TRANSFER("ASSETS_TRANSFER", "资产转让"),
	PROXY_CHARGING("PROXY_CHARGING", "代收费(合作机构费用、律所费用、会所费用)"), ;
		//	DEPOSIT("DEPOSIT", "代收费(合作机构用费、律师费用、会所费用)"),
	//	DEPOSIT("DEPOSIT", "保证金"),
	//	FINANCE_CONSULT_FEE("FINANCE_CONSULT_FEE", "财务顾问费"),
	//	LEGAL_COUNSEL_FEE("LEGAL_COUNSEL_FEE", "法律咨询费"),
	//	UNDERWRITING_FEE("UNDERWRITING_FEE", "承销费"),
	//	SERVICE_FEE("SERVICE_FEE ", "服务费"), ;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 *
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private FeeTypeEnum(String code, String message) {
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
	 * @return FeeTypeEnum
	 */
	public static FeeTypeEnum getByCode(String code) {
		for (FeeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FeeTypeEnum>
	 */
	public static java.util.List<FeeTypeEnum> getAllEnum() {
		java.util.List<FeeTypeEnum> list = new java.util.ArrayList<FeeTypeEnum>(values().length);
		for (FeeTypeEnum _enum : values()) {
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
		for (FeeTypeEnum _enum : values()) {
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
		FeeTypeEnum _enum = getByCode(code);
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
	public static String getCode(FeeTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
