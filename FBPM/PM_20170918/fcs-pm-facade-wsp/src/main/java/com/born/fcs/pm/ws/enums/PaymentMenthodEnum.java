package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 资金划付 付款种类
 * 
 * @author Ji
 */
public enum PaymentMenthodEnum {
	
	COMMISSION_LOAN("COMMISSION_LOAN", "委贷放款"),
	COMMISSION_LOAN_FEE("COMMISSION_LOAN_FEE", "委贷手续费"),
	FINANCIAL_PRODUCT("FINANCIAL_PRODUCT", "理财产品购买"),
	FINANCIAL_PRODUCT_BUY_BACK("FINANCIAL_PRODUCT_BUY_BACK", "理财产品回购"),
	DEPOSIT_PAID("DEPOSIT_PAID", "存出保证金"),
	CUSTOMER_DEPOSIT_REFUND("CUSTOMER_DEPOSIT_REFUND", "退还客户保证金"),
	COMPENSATORY_PRINCIPAL("COMPENSATORY_PRINCIPAL", "代偿本金"),
	COMPENSATORY_INTEREST("COMPENSATORY_INTEREST", "代偿利息"),
	COMPENSATORY_PENALTY("COMPENSATORY_PENALTY", "代偿罚息"),
	COMPENSATORY_LIQUIDATED_DAMAGES("COMPENSATORY_LIQUIDATED_DAMAGES", "代偿违约金"),
	COMPENSATORY_OTHER("COMPENSATORY_OTHER", "代偿-其他"),
	REFUND("REFUND", "退费"),
	INTERNAL_FUND_LENDING("INTERNAL_FUND_LENDING", "内部借款"),
	FINANCING_PRINCIPAL_REPAYMENT("FINANCING_PRINCIPAL_REPAYMENT", "融资到期本金还款"),
	INTEREST_PAYMENTS__FINANCING_MATURITY("INTEREST_PAYMENTS__FINANCING_MATURITY", "融资到期利息还款"),
	LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE("LONG_TERM_EQUITY_INVESTMENT_EXPENDITURE", "长期股权投资支出"),
	OTHER_ASSET_SWAP("OTHER_ASSET_SWAP", "资产受让"),
	/** 代收费用划出 */
	PROXY_CHARGING_OUT("PROXY_CHARGING_OUT", "代收费用划出"),
	/** 所有业务都可发起，不控制阶段和发起的金额 */
	CASE_ACCEPTANCE_FEE("CASE_ACCEPTANCE_FEE", "案件受理费"),
	PRESERVATION_FEE("PRESERVATION_FEE", "保全费"),
	ANNOUNCEMENT_FEE("ANNOUNCEMENT_FEE", "公告费"),
	APPRAISAL_FEE("APPRAISAL_FEE", "鉴定费"),
	EXPERT_WITNESS_FEE("EXPERT_WITNESS_FEE", "专家证人出庭费"),
	LAWYER_FEE("LAWYER_FEE", "律师费"),
	ASSESSMENT_FEE("ASSESSMENT_FEE", "评估费"),
	AUCTION_FEE("AUCTION_FEE", "拍卖费"),
	OTHER("OTHER", "其他");
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>FinancialProductInterestTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private PaymentMenthodEnum(String code, String message) {
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
	 * @return FinancialProductInterestTypeEnum
	 */
	public static PaymentMenthodEnum getByCode(String code) {
		for (PaymentMenthodEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FinancialProductInterestTypeEnum>
	 */
	public static List<PaymentMenthodEnum> getAllEnum() {
		List<PaymentMenthodEnum> list = new ArrayList<PaymentMenthodEnum>();
		for (PaymentMenthodEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (PaymentMenthodEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
