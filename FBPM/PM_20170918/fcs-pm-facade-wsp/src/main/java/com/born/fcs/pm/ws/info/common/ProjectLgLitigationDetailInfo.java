package com.born.fcs.pm.ws.info.common;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 诉讼担保项目详情
 * @author wuzj
 */
public class ProjectLgLitigationDetailInfo extends ProjectInfo {
	
	private static final long serialVersionUID = 8394559181235687363L;
	
	/** 合作机构ID */
	private long coInstitutionId;
	/** 合作机构名称 */
	private String coInstitutionName;
	/** 合作机构服务费 */
	private double coInstitutionCharge;
	/** 合作机构服务费类型 元/% */
	private ChargeTypeEnum coInstitutionChargeType;
	/** 担保费 */
	private double guaranteeFee;
	/** 担保费类型 */
	private ChargeTypeEnum guaranteeFeeType;
	/** 保证金 */
	private double deposit;
	/** 保证金类型 元/% */
	private ChargeTypeEnum depositType;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 元/% */
	private ChargeTypeEnum otherFeeType;
	/** 法院 */
	private String court;
	/** 本次申请保全标的 */
	private String assureObject;
	
	public long getCoInstitutionId() {
		return this.coInstitutionId;
	}
	
	public void setCoInstitutionId(long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return this.coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
	public double getCoInstitutionCharge() {
		return this.coInstitutionCharge;
	}
	
	public String getCoInstitutionChargeCNString() {
		Money money = (coInstitutionCharge > 0 && coInstitutionChargeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(coInstitutionCharge).divide(100L)
			: new Money(coInstitutionCharge);
		return money.toCNString();
	}
	
	public String getCoInstitutionChargeStandardString() {
		Money money = (coInstitutionCharge > 0 && coInstitutionChargeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(coInstitutionCharge).divide(100L)
			: new Money(coInstitutionCharge);
		return money.toStandardString();
	}
	
	public String getCoInstitutionChargeTenThousandString() {
		Money money = (coInstitutionCharge > 0 && coInstitutionChargeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(coInstitutionCharge).divide(100L)
			: new Money(coInstitutionCharge);
		return getMoneyByw2(money);
	}
	
	public void setCoInstitutionCharge(double coInstitutionCharge) {
		this.coInstitutionCharge = coInstitutionCharge;
	}
	
	public ChargeTypeEnum getCoInstitutionChargeType() {
		return this.coInstitutionChargeType;
	}
	
	public String getCoInstitutionChargeTypeMessage() {
		return this.coInstitutionChargeType == null ? "" : coInstitutionChargeType.message();
	}
	
	public void setCoInstitutionChargeType(ChargeTypeEnum coInstitutionChargeType) {
		this.coInstitutionChargeType = coInstitutionChargeType;
	}
	
	public double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public String getGuaranteeFeeCNString() {
		Money money = (guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(guaranteeFee).divide(100L) : new Money(guaranteeFee);
		return money.toCNString();
	}
	
	public String getGuaranteeFeeStandardString() {
		Money money = (guaranteeFee > 0 && guaranteeFeeType == ChargeTypeEnum.PERCENT) ? super
			.getAmount().multiply(guaranteeFee).divide(100L) : new Money(guaranteeFee);
		return money.toStandardString();
	}
	
	public String getGuaranteeFeeTenThousandString() {
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
	
	public double getDeposit() {
		return this.deposit;
	}
	
	public String getDepositCNString() {
		Money money = (deposit > 0 && depositType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(deposit).divide(100L) : new Money(deposit);
		return money.toCNString();
	}
	
	public String getDepositStandardString() {
		Money money = (deposit > 0 && depositType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(deposit).divide(100L) : new Money(deposit);
		return money.toStandardString();
	}
	
	public String getDepositTenThousandString() {
		Money money = (deposit > 0 && depositType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(deposit).divide(100L) : new Money(deposit);
		return getMoneyByw2(money);
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public ChargeTypeEnum getDepositType() {
		return this.depositType;
	}
	
	public String getDepositTypeMessage() {
		return this.depositType == null ? "" : depositType.message();
	}
	
	public void setDepositType(ChargeTypeEnum depositType) {
		this.depositType = depositType;
	}
	
	public double getOtherFee() {
		return this.otherFee;
	}
	
	public String getOtherFeeCNString() {
		Money money = (otherFee > 0 && otherFeeType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(otherFee).divide(100L) : new Money(otherFee);
		return money.toCNString();
	}
	
	public String getOtherFeeStandardString() {
		Money money = (otherFee > 0 && otherFeeType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(otherFee).divide(100L) : new Money(otherFee);
		return money.toStandardString();
	}
	
	public String getOtherFeeTenThousandString() {
		Money money = (otherFee > 0 && otherFeeType == ChargeTypeEnum.PERCENT) ? super.getAmount()
			.multiply(otherFee).divide(100L) : new Money(otherFee);
		return getMoneyByw2(money);
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
	
	public String getCourt() {
		return this.court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public String getAssureObject() {
		return this.assureObject;
	}
	
	public void setAssureObject(String assureObject) {
		this.assureObject = assureObject;
	}
	
}
