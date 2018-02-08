/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:43:51 创建
 */
package com.born.fcs.fm.ws.enums;

/**
 * 
 * 预测类型费用类型
 * @author wuzj
 * 
 */
public enum ForecastFeeTypeEnum {
	
	/**
	 * 流出
	 */
	DEPOSIT_PAY("DEPOSIT_PAY", "保证金划出", "OUT", "DEPOSIT_PAID", "存出保证金"),
	WD_LOAN("WD_LOAN", "委贷放款", "OUT", "COMMISSION_LOAN", "委贷放款"),
	BUY("BUY", "理财产品购买", "OUT", "FINANCIAL_PRODUCT", "理财产品购买"),
	BUY_BACK("BUY_BACK", "理财产品回购", "OUT", "FINANCIAL_PRODUCT_BUY_BACK", "理财产品回购"),
	EXPENSE("EXPENSE", "费用支付", "OUT", "", ""),
	LABOUR_EXPENSE("LABOUR_EXPENSE", "劳资及税金", "OUT", "", ""),
	TRAVEL_EXPENSE("TRAVEL_EXPENSE", "差旅费报销", "OUT", "", ""),
	ASSET_TRANSFERQS("ASSET_TRANSFERQS", "资产转让清收", "OUT", "OTHER_ASSET_SWAP", "资产受让"),
	COMP("COMP", "代偿款划出", "OUT", "COMPENSATORY_PRINCIPAL", "代偿本金"),
	REFOUND_DEPOSIT("REFOUND_DEPOSIT", "退还客户保证金", "OUT", "CUSTOMER_DEPOSIT_REFUND", "退还客户保证金"),
	
	/**
	 * 流入
	 */
	CXFEE("CXFEE", "收取承销费", "IN", "CONSIGNMENT_INWARD_INCOME", "承销收入"),
	REPLAYPLAN("REPLAYPLAN", "委贷本金收回", "IN", "ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL", "委贷本金收回"),
	CHARGEPLAN("CHARGEPLAN", "委贷利息收回", "IN", "ENTRUSTED_LOAN_INTEREST_WITHDRAWAL", "委贷利息收回"),
	DEPOSIT_CHARGE("DEPOSIT_CHARGE", "收取客户保证金", "IN", "GUARANTEE_DEPOSIT", "存入保证金"),
	GUARANTEE_FEE("GUARANTEE_FEE", "收取担保费", "IN", "GUARANTEE_FEE", "担保费"),
	REDEEM("REDEEM", "理财产品赎回", "IN", "", ""),
	TRANSFER("TRANSFER", "理财产品转让", "IN", "", ""),
	IN_INNER_LOAN("IN_INNER_LOAN", "内部借款还款", "IN", "", ""),
	ASSET_TANSFER("ASSET_TANSFER", "资产转让", "IN", "ASSETS_TRANSFER", "资产转让"),
	COMPBACK(
				"COMPBACK",
				"代偿款收回",
				"IN",
				"COMPENSATORY_PRINCIPAL_WITHDRAWAL,COMPENSATORY_INTEREST_WITHDRAWAL,COMPENSATORY_DEDIT_WITHDRAWAL,COMPENSATORY_PENALTY_INTEREST_WITHDRAWAL,COMPENSATORY_OTHER_WITHDRAWAL",
				"代偿款本金收回,代偿款利息收回,代偿违约金收回,代偿罚息收回,代偿其他收回"),
				
	DEPOSITS_DRAW_BACK("DEPOSITS_DRAW_BACK", "存出保证金划回", "IN", "REFUNDABLE_DEPOSITS_DRAW_BACK",
						"存出保证金划回"),
	;
	
	/** 枚举值 */
	private final String code;
	
	/** 枚举描述 */
	private final String message;
	
	/** 费用方向 */
	private final String direction;
	
	/**
	 * 费用明细
	 * @see com.born.fcs.pm.ws.enums.FeeTypeEnum direction = IN
	 * @see com.born.fcs.pm.ws.enums.PaymentMenthodEnum direction = OUT
	 */
	private final String fees;
	
	private final String feeNames;
	
	/**
	 * 
	 * @param code 枚举值
	 * @param message 枚举描述
	 * @param direction 费用方向
	 * @param fees 对应费用类型（资金划付、收款通知）
	 * @param feeNames 对应费用类型（资金划付、收款通知）
	 */
	private ForecastFeeTypeEnum(String code, String message, String direction, String fees,
								String feeNames) {
		this.code = code;
		this.message = message;
		this.direction = direction;
		this.fees = fees;
		this.feeNames = feeNames;
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
	 * @return Returns the direction.
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * @return Returns the fees.
	 */
	public String getFees() {
		return fees;
	}
	
	/**
	 * @return Returns the feeNames.
	 */
	public String getFeeNames() {
		return feeNames;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return ForecastTypeEnum
	 */
	public static ForecastFeeTypeEnum getByCode(String code) {
		for (ForecastFeeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 根据收费类型获取预测费用类型，没获取到表示不存在预测
	 * @param feeType
	 * @return
	 */
	public static ForecastFeeTypeEnum getByInFees(String feeType) {
		for (ForecastFeeTypeEnum _enum : values()) {
			if ("IN".equals(_enum.getDirection()) && _enum.getFees() != null
				&& !"".equals(_enum.getFees())) {
				String[] fees = _enum.getFees().split(",");
				for (String fee : fees) {
					if (feeType.equals(fee))
						return _enum;
				}
			}
		}
		return null;
	}
	
	public static ForecastFeeTypeEnum getByOutFees(String feeType) {
		for (ForecastFeeTypeEnum _enum : values()) {
			if ("OUT".equals(_enum.getDirection()) && _enum.getFees() != null
				&& !"".equals(_enum.getFees())) {
				String[] fees = _enum.getFees().split(",");
				for (String fee : fees) {
					if (feeType.equals(fee))
						return _enum;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<ForecastTypeEnum>
	 */
	public static java.util.List<ForecastFeeTypeEnum> getAllEnum() {
		java.util.List<ForecastFeeTypeEnum> list = new java.util.ArrayList<ForecastFeeTypeEnum>(
			values().length);
		for (ForecastFeeTypeEnum _enum : values()) {
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
		for (ForecastFeeTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}
