package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FAfterwardsCheckLoanInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2865537194049426267L;
	
	private long id;
	
	private long formId;
	
	/** 融资机构 */
	private String loanInstitution;
	
	/** 授信品种 */
	private String loanType;
	/** 授信金额 */
	private Money loanAmount = new Money(0, 0);
	/** 用信金额 */
	private Money loanBalance = new Money(0, 0);
	/** 额度期限 */
	private String loanTimeLimit;
	
	private double interestRate;
	
	private double cashDepositRate;
	private String cashDeposit;
	/** 到期日 */
	private Date loanExpireDate;
	
	private String guaranteeWay;
	
	private String remark;
	
	private BooleanEnum delAble;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getLoanInstitution() {
		return this.loanInstitution;
	}
	
	public void setLoanInstitution(String loanInstitution) {
		this.loanInstitution = loanInstitution;
	}
	
	public String getLoanType() {
		return this.loanType;
	}
	
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public Money getLoanAmount() {
		return this.loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public Money getLoanBalance() {
		return this.loanBalance;
	}
	
	public void setLoanBalance(Money loanBalance) {
		this.loanBalance = loanBalance;
	}
	
	public String getLoanTimeLimit() {
		return this.loanTimeLimit;
	}
	
	public void setLoanTimeLimit(String loanTimeLimit) {
		this.loanTimeLimit = loanTimeLimit;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public double getCashDepositRate() {
		return this.cashDepositRate;
	}
	
	public void setCashDepositRate(double cashDepositRate) {
		this.cashDepositRate = cashDepositRate;
	}
	
	public String getCashDeposit() {
		return this.cashDeposit;
	}

	public void setCashDeposit(String cashDeposit) {
		this.cashDeposit = cashDeposit;
	}

	public Date getLoanExpireDate() {
		return this.loanExpireDate;
	}
	
	public void setLoanExpireDate(Date loanExpireDate) {
		this.loanExpireDate = loanExpireDate;
	}
	
	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}
	
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public BooleanEnum getDelAble() {
		return delAble;
	}

	public void setDelAble(BooleanEnum delAble) {
		this.delAble = delAble;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
