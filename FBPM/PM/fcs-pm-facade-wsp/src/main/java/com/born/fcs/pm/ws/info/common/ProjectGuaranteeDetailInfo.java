package com.born.fcs.pm.ws.info.common;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 担保项目详情
 * @author wuzj
 */
public class ProjectGuaranteeDetailInfo extends ProjectInfo {
	
	private static final long serialVersionUID = -3019097180772490658L;
	
	/** 资金渠道ID */
	private long capitalChannelId;
	/** 资金渠道名称 */
	private String capitalChannelName;
	/** 二级资金渠道 */
	private long capitalSubChannelId;
	/** 二级资金渠道名称 */
	private String capitalSubChannelName;
	/** 利率 */
	private double interestRate;
	/** 利率浮动(大于/小于等) */
	private CompareEnum interestRateFloat;
	/** 担保费 */
	private double guaranteeFee;
	/** 担保费类型 */
	private ChargeTypeEnum guaranteeFeeType;
	/** 总成本 */
	private double totalCost;
	/** 总成本类型 元/% */
	private ChargeTypeEnum totalCostType;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 */
	private ChargeTypeEnum otherFeeType;
	
	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public CompareEnum getInterestRateFloat() {
		return this.interestRateFloat;
	}
	
	public String getInterestRateFloatMessage() {
		return this.interestRateFloat == null ? "" : interestRateFloat.message();
	}
	
	public void setInterestRateFloat(CompareEnum interestRateFloat) {
		this.interestRateFloat = interestRateFloat;
	}
	
	public double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public String getGuaranteeFeeCNString() {
		//		Money money = guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.AMOUNT ? new Money(
		//			guaranteeFee) : Money.zero();
		Money money = (guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(guaranteeFee).divide(100L) : new Money(guaranteeFee);
		return money.toCNString();
	}
	
	public String getGuaranteeFeeStandardString() {
		//		Money money = guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.AMOUNT ? new Money(
		//			guaranteeFee) : Money.zero();
		Money money = (guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(guaranteeFee).divide(100L) : new Money(guaranteeFee);
		
		return money.toStandardString();
	}
	
	public String getGuaranteeFeeTenThousandString() {
		//		Money money = guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.AMOUNT ? new Money(
		//			guaranteeFee) : Money.zero();
		Money money = (guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(guaranteeFee).divide(100L) : new Money(guaranteeFee);
		
		return getMoneyByw2(money);
	}
	
	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public ChargeTypeEnum getGuaranteeFeeType() {
		return this.guaranteeFeeType;
	}
	
	public String getGuaranteeFeeTypeMessage() {
		return this.guaranteeFeeType == null ? "" : guaranteeFeeType.message();
	}
	
	public void setGuaranteeFeeType(ChargeTypeEnum guaranteeFeeType) {
		this.guaranteeFeeType = guaranteeFeeType;
	}
	
	public double getTotalCost() {
		return this.totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public ChargeTypeEnum getTotalCostType() {
		return this.totalCostType;
	}
	
	public String getTotalCostTypeMessage() {
		return this.totalCostType == null ? "" : totalCostType.message();
	}
	
	public void setTotalCostType(ChargeTypeEnum totalCostType) {
		this.totalCostType = totalCostType;
	}
	
	public double getOtherFee() {
		return this.otherFee;
	}
	
	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}
	
	public ChargeTypeEnum getOtherFeeType() {
		return this.otherFeeType;
	}
	
	public String getOtherFeeTypeMessage() {
		return this.otherFeeType == null ? "" : otherFeeType.message();
	}
	
	public void setOtherFeeType(ChargeTypeEnum otherFeeType) {
		this.otherFeeType = otherFeeType;
	}
	
}
