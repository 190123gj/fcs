package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.math.NumberUtils;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

public class FAfterwardsCheckLoanOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5677651967838710229L;
	
	private long id;
	private long formId;
	
	/** 融资机构 */
	private String loanInstitution;
	
	/** 授信品种 */
	private String loanType;
	/** 授信金额 */
	private Money loanAmount = new Money(0, 0);
	/** 用信余额 */
	private Money loanBalance = new Money(0, 0);
	/** 额度期限 */
	private String loanTimeLimit;
	/** 利率 */
	private double interestRate;
	/** 保证金比例 */
	private double cashDepositRate;
	/** 到期日 yyyy-MM-dd */
	private String loanExpireDate;
	/** 担保方式 */
	private String guaranteeWay;
	
	private String delAble;
	
	private String remark;
	
	private int sortOrder;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
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
		if (loanAmount == null) {
			this.loanAmount = new Money(0, 0);
		} else {
			this.loanAmount = loanAmount;
		}
	}
	
	public void setLoanAmountStr(String loanAmountStr) {
		if (!isNull(loanAmountStr)) {
			this.loanAmount = Money.amout(loanAmountStr);
		}
	}
	
	public Money getLoanBalance() {
		return this.loanBalance;
	}
	
	public void setLoanBalance(Money loanBalance) {
		this.loanBalance = loanBalance;
	}
	
	public void setLoanBalanceStr(String loanBalanceStr) {
		if (!isNull(loanBalanceStr)) {
			this.loanBalance = Money.amout(loanBalanceStr);
		}
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
	
	public void setInterestRateStr(String interestRateStr) {
		if (!isNull(interestRateStr)) {
			this.interestRate = NumberUtils.toDouble(interestRateStr);
		}
	}
	
	public double getCashDepositRate() {
		return this.cashDepositRate;
	}
	
	public void setCashDepositRate(double cashDepositRate) {
		this.cashDepositRate = cashDepositRate;
	}
	
	public void setCashDepositRateStr(String cashDepositRateStr) {
		if (!isNull(cashDepositRateStr)) {
			this.cashDepositRate = NumberUtils.toDouble(cashDepositRateStr);
		}
	}
	
	public String getLoanExpireDate() {
		return this.loanExpireDate;
	}
	
	public void setLoanExpireDate(String loanExpireDate) {
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
	
	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
