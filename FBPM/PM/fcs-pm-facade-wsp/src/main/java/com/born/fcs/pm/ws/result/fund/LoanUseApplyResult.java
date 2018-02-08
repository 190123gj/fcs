package com.born.fcs.pm.ws.result.fund;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请
 * @author wuzj
 *
 */
public class LoanUseApplyResult extends FormBaseResult {
	
	private static final long serialVersionUID = 1559970536663342603L;
	//原始金额
	private Money orignalAmount = Money.zero();
	//已放款金额
	private Money loanedAmount = Money.zero();
	//已以用款金额
	private Money usedAmount = Money.zero();
	//已解保金额
	private Money releasedAmount = Money.zero();
	//已发售金额
	private Money issueAmount = Money.zero();
	//可用放款金额
	private Money balanceLoanAmount = Money.zero();
	//可用用款金额
	private Money balanceUseAmount = Money.zero();
	//申请中放款金额
	private Money applyingLoanAmount = Money.zero();
	//申请中用款金额
	private Money applyingUseAmount = Money.zero();
	//申请中放用款
	private Money applyingLoanUseAmount = Money.zero();
	//是否最高授信额
	private BooleanEnum isMaximumAmount = BooleanEnum.NO;
	
	public Money getOrignalAmount() {
		return this.orignalAmount;
	}
	
	public void setOrignalAmount(Money orignalAmount) {
		this.orignalAmount = orignalAmount;
	}
	
	public Money getLoanedAmount() {
		return this.loanedAmount;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public Money getUsedAmount() {
		return this.usedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Money getReleasedAmount() {
		return this.releasedAmount;
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	
	public Money getIssueAmount() {
		return this.issueAmount;
	}
	
	public void setIssueAmount(Money issueAmount) {
		this.issueAmount = issueAmount;
	}
	
	public Money getBalanceLoanAmount() {
		return this.balanceLoanAmount;
	}
	
	public void setBalanceLoanAmount(Money balanceLoanAmount) {
		this.balanceLoanAmount = balanceLoanAmount;
	}
	
	public Money getBalanceUseAmount() {
		return this.balanceUseAmount;
	}
	
	public void setBalanceUseAmount(Money balanceUseAmount) {
		this.balanceUseAmount = balanceUseAmount;
	}
	
	public Money getApplyingLoanAmount() {
		return this.applyingLoanAmount;
	}
	
	public void setApplyingLoanAmount(Money applyingLoanAmount) {
		this.applyingLoanAmount = applyingLoanAmount;
	}
	
	public Money getApplyingUseAmount() {
		return this.applyingUseAmount;
	}
	
	public void setApplyingUseAmount(Money applyingUseAmount) {
		this.applyingUseAmount = applyingUseAmount;
	}
	
	public BooleanEnum getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public void setIsMaximumAmount(BooleanEnum isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
	}
	
	public Money getApplyingLoanUseAmount() {
		return this.applyingLoanUseAmount;
	}
	
	public void setApplyingLoanUseAmount(Money applyingLoanUseAmount) {
		this.applyingLoanUseAmount = applyingLoanUseAmount;
	}
}
