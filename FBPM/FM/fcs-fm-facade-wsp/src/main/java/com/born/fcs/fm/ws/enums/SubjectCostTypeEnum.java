/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:49:02 创建
 */
package com.born.fcs.fm.ws.enums;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;

/**
 * 
 * 收费种类
 * @author hjiajie
 * 
 */
public enum SubjectCostTypeEnum {
	
	/// 收款
	/** 担保费 */
	GUARANTEE_FEE("GUARANTEE_FEE", "RECEIPT", "担保费"),
	/** 项目评审费 */
	PROJECT_REVIEW_FEE("PROJECT_REVIEW_FEE", "RECEIPT", "项目评审费"),
	/** 委贷本金收回 */
	ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL("ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL", "RECEIPT", "委贷本金收回"),
	/** 委贷利息收回 */
	ENTRUSTED_LOAN_INTEREST_WITHDRAWAL("ENTRUSTED_LOAN_INTEREST_WITHDRAWAL", "RECEIPT", "委贷利息收回"),
	ENTRUSTED_LOAN_POUNDAGE("ENTRUSTED_LOAN_POUNDAGE", "RECEIPT", "委贷手续费"),
	ENTRUSTED_LOAN_IQUIDATED_DAMAGES("ENTRUSTED_LOAN_IQUIDATED_DAMAGES", "RECEIPT", "委贷违约金"),
	
	/** 顾问费 */
	CONSULT_FEE("CONSULT_FEE", "RECEIPT", "顾问费"),
	/** 理财产品过户转让 */
	FINANCING_PRODUCT_TRANSFER("FINANCING_PRODUCT_TRANSFER", "RECEIPT", "理财产品过户转让"),
	/** 理财产品未过户转让 */
	FINANCING_PRODUCT_TRANSFER_NOT("FINANCING_PRODUCT_TRANSFER_NOT", "RECEIPT", "理财产品未过户转让"),
	/** 理财产品赎回 */
	FINANCING_PRODUCT_REDEMPTION("FINANCING_PRODUCT_REDEMPTION", "RECEIPT", "理财产品赎回（本金）"),
	/** 理财投资收益 */
	INANCING_INVEST_INCOME("INANCING_INVEST_INCOME", "RECEIPT", "理财投资收益"),
	/** 承销收入 */
	CONSIGNMENT_INWARD_INCOME("CONSIGNMENT_INWARD_INCOME", "RECEIPT", "承销收入"),
	/** 经纪收入 */
	SOLD_INCOME("SOLD_INCOME", "RECEIPT", "经纪收入"),
	
	/** 追偿收入 */
	RECOVERY_INCOME("RECOVERY_INCOME", "RECEIPT", "追偿收入"),
	/** 存入保证金 */
	GUARANTEE_DEPOSIT("GUARANTEE_DEPOSIT", "RECEIPT", "存入保证金"),
	/** 存出保证金划回 */
	REFUNDABLE_DEPOSITS_DRAW_BACK("REFUNDABLE_DEPOSITS_DRAW_BACK", "RECEIPT", "存出保证金划回"),
	/** 代偿款本金收回 */
	COMPENSATORY_PRINCIPAL_WITHDRAWAL("COMPENSATORY_PRINCIPAL_WITHDRAWAL", "RECEIPT", "代偿款本金收回"),
	/** 代偿利息收回 */
	COMPENSATORY_INTEREST_WITHDRAWAL("COMPENSATORY_INTEREST_WITHDRAWAL", "RECEIPT", "代偿款利息收回"),
	/** 代偿违约金收回 */
	COMPENSATORY_DEDIT_WITHDRAWAL("COMPENSATORY_DEDIT_WITHDRAWAL", "RECEIPT", "代偿违约金收回"),
	/** 代偿罚息收回 */
	COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL("COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL", "RECEIPT",
												"代偿罚息收回"),
	/** 代偿其他收回 */
	COMPENSATORY_OTHER_WITHDRAWAL("COMPENSATORY_OTHER_WITHDRAWAL", "RECEIPT", "代偿其他收回"),
	/** 内部资金拆借 */
	INNER_ACCOUNT_LENDING("INNER_ACCOUNT_LENDING", "RECEIPT", "内部资金拆借"),
	/** 股权投资收益 */
	EQUITY_INVESTMENT_INCOME("EQUITY_INVESTMENT_INCOME", "RECEIPT", "股权投资收益"),
	/** 股权投资本金收回 */
	EQUITY_INVESTMENT_CAPITAL_BACK("EQUITY_INVESTMENT_CAPITAL_BACK", "RECEIPT", "股权投资本金收回"),
	/** 利息收入 */
	INTEREST_INCOME("INTEREST_INCOME", "RECEIPT", "利息收入"),
	/** 资产转让 */
	ASSETS_TRANSFER("ASSETS_TRANSFER", "RECEIPT", "资产转让"),
	
	/** 对外融资 */
	OUTSIDE_FINANCING("OUTSIDE_FINANCING", "RECEIPT", "对外融资"),
	
	/** 代收费用 */
	PROXY_CHARGING("PROXY_CHARGING", "RECEIPT", "代收费用"),
	
	/** 其他 */
	RECEIPT_OTHER("RECEIPT_OTHER", "RECEIPT", "其他"),
	
	// 付款 
	/** 委贷放款 */
	COMMISSION_LOAN("COMMISSION_LOAN", "PAYMENT", "委贷放款"),
	
	/** 理财产品购买 */
	FINANCIAL_PRODUCT("FINANCIAL_PRODUCT", "PAYMENT", "理财产品购买"),
	
	/** 理财产品回购 */
	FINANCIAL_PRODUCT_BUY_BACK("FINANCIAL_PRODUCT_BUY_BACK", "PAYMENT", "理财产品回购"),
	
	/** 存出保证金 */
	DEPOSIT_PAID("DEPOSIT_PAID", "PAYMENT", "存出保证金"),
	
	/** 退还客户保证金 */
	CUSTOMER_DEPOSIT_REFUND("CUSTOMER_DEPOSIT_REFUND", "PAYMENT", "退还客户保证金"),
	
	/** 代偿本金 */
	COMPENSATORY_PRINCIPAL("COMPENSATORY_PRINCIPAL", "PAYMENT", "代偿本金"),
	
	/** 代偿利息 */
	COMPENSATORY_INTEREST("COMPENSATORY_INTEREST", "PAYMENT", "代偿利息"),
	/** 代偿罚息 */
	COMPENSATORY_PENALTY("COMPENSATORY_PENALTY", "PAYMENT", "代偿罚息"),
	/** 代偿违约金 */
	COMPENSATORY_LIQUIDATED_DAMAGES("COMPENSATORY_LIQUIDATED_DAMAGES", "PAYMENT", "代偿违约金"),
	/** 代偿--其他 */
	COMPENSATORY_OTHER("COMPENSATORY_OTHER", "PAYMENT", "代偿-其他"),
	/** 代收费用划出 */
	PROXY_CHARGING_OUT("PROXY_CHARGING_OUT", "PAYMENT", "代收费用划出"),
	/** 退费 */
	REFUND("REFUND", "PAYMENT", "退费"),
	
	/** 内部资金拆解借 */
	INTERNAL_FUND_LENDING("INTERNAL_FUND_LENDING", "PAYMENT", "内部资金拆借"),
	
	/** 劳资及税金申请单 */
	LABOUR_CAPITAL("LABOUR_CAPITAL", "PAYMENT", "劳资及税金申请单"),
	
	/** 融资到期本金还款 */
	FINANCING_PRINCIPAL_REPAYMENT("FINANCING_PRINCIPAL_REPAYMENT", "PAYMENT", "融资到期本金还款"),
	
	/** 融资到期利息还款 */
	INTEREST_PAYMENTS__FINANCING_MATURITY("INTEREST_PAYMENTS__FINANCING_MATURITY", "PAYMENT",
											"融资到期利息还款"),
	
	/** 长期股权投资支出 */
	LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE("LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE", "PAYMENT",
											"长期股权投资支出"),
	
	/** 委贷手续费 **/
	COMMISSION_LOAN_FEE("COMMISSION_LOAN_FEE", "PAYMENT", "委贷手续费"),
	
	/** 资产受让 */
	OTHER_ASSET_SWAP("OTHER_ASSET_SWAP", "PAYMENT", "资产受让"),
	
	/** 利息支出 */
	INTEREST_OUT("INTEREST_OUT", "PAYMENT", "利息支出"),
	
	/** 案件受理费 */
	CASE_ACCEPTANCE_FEE("CASE_ACCEPTANCE_FEE", "PAYMENT", "案件受理费"),
	/** 保全费 */
	PRESERVATION_FEE("PRESERVATION_FEE", "PAYMENT", "保全费"),
	/** 公告费 */
	ANNOUNCEMENT_FEE("ANNOUNCEMENT_FEE", "PAYMENT", "公告费"),
	/** 鉴定费 */
	APPRAISAL_FEE("APPRAISAL_FEE", "PAYMENT", "鉴定费"),
	/** 专家证人出庭费 */
	EXPERT_WITNESS_FEE("EXPERT_WITNESS_FEE", "PAYMENT", "专家证人出庭费"),
	/** 律师费 */
	LAWYER_FEE("LAWYER_FEE", "PAYMENT", "律师费"),
	/** 评估费 */
	ASSESSMENT_FEE("ASSESSMENT_FEE", "PAYMENT", "评估费"),
	/** 拍卖费 */
	AUCTION_FEE("AUCTION_FEE", "PAYMENT", "拍卖费"),
	
	/** 付款种类其他 */
	PAYMENT_OTHER("PAYMENT_OTHER", "PAYMENT", "其他"),
	
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举值 */
	private final String type;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 收费类型对应的费用种类
	 * @param feeType
	 * @return
	 */
	public static SubjectCostTypeEnum getByChargeFeeType(FeeTypeEnum feeType) {
		if (feeType != null) {
			if (feeType == FeeTypeEnum.OTHER)
				return SubjectCostTypeEnum.RECEIPT_OTHER;
			
			for (SubjectCostTypeEnum _enum : values()) {
				if ("RECEIPT".equals(_enum.getType()) && _enum.getCode().equals(feeType.getCode())) {
					return _enum;
				}
			}
		}
		return null;
	}
	
	/**
	 * 付款类型对应的费用种类
	 * @param feeType
	 * @return
	 */
	public static SubjectCostTypeEnum getByPayFeeType(PaymentMenthodEnum feeType) {
		if (feeType != null) {
			if (feeType == PaymentMenthodEnum.OTHER)
				return SubjectCostTypeEnum.PAYMENT_OTHER;
			
			for (SubjectCostTypeEnum _enum : values()) {
				if ("PAYMENT".equals(_enum.getType()) && _enum.getCode().equals(feeType.getCode())) {
					return _enum;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 */
	private SubjectCostTypeEnum(String code, String type, String message) {
		this.code = code;
		this.type = type;
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
	
	public String getType() {
		return this.type;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return SubjectCostType
	 */
	public static SubjectCostTypeEnum getByCode(String code) {
		for (SubjectCostTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 通过枚举<type>type</type>获得枚举
	 * 
	 * @param type
	 * @return SubjectCostType
	 */
	public static SubjectCostTypeEnum getByType(String type) {
		for (SubjectCostTypeEnum _enum : values()) {
			if (_enum.getType().equals(type)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取所有收款单枚举
	 * 
	 * @param type
	 * @return SubjectCostType
	 */
	public static SubjectCostTypeEnum getPaymentEnum() {
		
		return getByType("RECEIPT");
	}
	
	/**
	 * 获取所有付款单枚举
	 * 
	 * @param type
	 * @return SubjectCostType
	 */
	public static SubjectCostTypeEnum getReceiptEnum(String type) {
		
		return getByType("PAYMENT");
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<SubjectCostType>
	 */
	public static java.util.List<SubjectCostTypeEnum> getAllEnum() {
		java.util.List<SubjectCostTypeEnum> list = new java.util.ArrayList<SubjectCostTypeEnum>(
			values().length);
		for (SubjectCostTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取收款枚举
	 * 
	 * @return List<SubjectCostType>
	 */
	public static java.util.List<SubjectCostTypeEnum> getReceiptEnums() {
		java.util.List<SubjectCostTypeEnum> list = new java.util.ArrayList<SubjectCostTypeEnum>(
			values().length);
		for (SubjectCostTypeEnum _enum : values()) {
			if ("RECEIPT".equals(_enum.getType()))
				list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取收款枚举
	 * 
	 * @return List<SubjectCostType>
	 */
	public static java.util.List<SubjectCostTypeEnum> getPaymentEnums() {
		java.util.List<SubjectCostTypeEnum> list = new java.util.ArrayList<SubjectCostTypeEnum>(
			values().length);
		for (SubjectCostTypeEnum _enum : values()) {
			// 利息支出是选择付款来源为其他的时候特有的
			if ("PAYMENT".equals(_enum.getType()) && INTEREST_OUT != _enum)
				list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取收款枚举
	 * 
	 * @return List<SubjectCostType>
	 */
	public static java.util.List<SubjectCostTypeEnum> getPaymentOtherEnums() {
		java.util.List<SubjectCostTypeEnum> list = new java.util.ArrayList<SubjectCostTypeEnum>(
			values().length);
		list.add(LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE);
		list.add(FINANCING_PRINCIPAL_REPAYMENT);
		list.add(INTEREST_PAYMENTS__FINANCING_MATURITY);
		list.add(INTEREST_OUT);
		list.add(PAYMENT_OTHER);
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (SubjectCostTypeEnum _enum : values()) {
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
		SubjectCostTypeEnum _enum = getByCode(code);
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
	public static String getCode(SubjectCostTypeEnum _enum) {
		if (_enum == null) {
			return null;
		}
		return _enum.getCode();
	}
}
