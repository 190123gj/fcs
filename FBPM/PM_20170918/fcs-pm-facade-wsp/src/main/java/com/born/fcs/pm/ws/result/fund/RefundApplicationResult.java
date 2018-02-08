package com.born.fcs.pm.ws.result.fund;

import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 退费申请 退费金额验证
 * @author ji
 *
 */
public class RefundApplicationResult extends FormBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5817162717049760802L;
	//其它费用
	private Money other = Money.zero();
	//担保费
	private Money guaranteeFee = Money.zero();
	//项目评审费
	private Money projectReviewFee = Money.zero();
	//追偿收入
	private Money recoveryIncome = Money.zero();
	//代偿款本金收回
	private Money compensatoryPrincipalWithdrawal = Money.zero();
	//代偿款利息收回
	private Money compensatoryInterestWithdrawal = Money.zero();
	//委贷本金收回
	private Money entrustedLoanPrincipalWithdrawal = Money.zero();
	//委贷利息收回
	private Money entrustedLoanInterestWithdrawal = Money.zero();
	//顾问费
	private Money consultFee = Money.zero();
	//承销收入
	private Money consignmentInwardIncome = Money.zero();
	//存出保证金划回
	private Money refundableDepositsDrawBack = Money.zero();
	//存入保证金
	private Money guaranteeDeposit = Money.zero();
	//资产转让
	private Money assetsTransfer = Money.zero();
	//代收费(合作机构费用、律所费用、会所费用)
	private Money proxyCharging = Money.zero();
	
	public Money getOther() {
		return other;
	}
	
	public void setOther(Money other) {
		this.other = other;
	}
	
	public Money getGuaranteeFee() {
		return guaranteeFee;
	}
	
	public void setGuaranteeFee(Money guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public Money getProjectReviewFee() {
		return projectReviewFee;
	}
	
	public void setProjectReviewFee(Money projectReviewFee) {
		this.projectReviewFee = projectReviewFee;
	}
	
	public Money getRecoveryIncome() {
		return recoveryIncome;
	}
	
	public void setRecoveryIncome(Money recoveryIncome) {
		this.recoveryIncome = recoveryIncome;
	}
	
	public Money getCompensatoryPrincipalWithdrawal() {
		return compensatoryPrincipalWithdrawal;
	}
	
	public void setCompensatoryPrincipalWithdrawal(Money compensatoryPrincipalWithdrawal) {
		this.compensatoryPrincipalWithdrawal = compensatoryPrincipalWithdrawal;
	}
	
	public Money getCompensatoryInterestWithdrawal() {
		return compensatoryInterestWithdrawal;
	}
	
	public void setCompensatoryInterestWithdrawal(Money compensatoryInterestWithdrawal) {
		this.compensatoryInterestWithdrawal = compensatoryInterestWithdrawal;
	}
	
	public Money getEntrustedLoanPrincipalWithdrawal() {
		return entrustedLoanPrincipalWithdrawal;
	}
	
	public void setEntrustedLoanPrincipalWithdrawal(Money entrustedLoanPrincipalWithdrawal) {
		this.entrustedLoanPrincipalWithdrawal = entrustedLoanPrincipalWithdrawal;
	}
	
	public Money getEntrustedLoanInterestWithdrawal() {
		return entrustedLoanInterestWithdrawal;
	}
	
	public void setEntrustedLoanInterestWithdrawal(Money entrustedLoanInterestWithdrawal) {
		this.entrustedLoanInterestWithdrawal = entrustedLoanInterestWithdrawal;
	}
	
	public Money getConsultFee() {
		return consultFee;
	}
	
	public void setConsultFee(Money consultFee) {
		this.consultFee = consultFee;
	}
	
	public Money getConsignmentInwardIncome() {
		return consignmentInwardIncome;
	}
	
	public void setConsignmentInwardIncome(Money consignmentInwardIncome) {
		this.consignmentInwardIncome = consignmentInwardIncome;
	}
	
	public Money getRefundableDepositsDrawBack() {
		return refundableDepositsDrawBack;
	}
	
	public void setRefundableDepositsDrawBack(Money refundableDepositsDrawBack) {
		this.refundableDepositsDrawBack = refundableDepositsDrawBack;
	}
	
	public Money getGuaranteeDeposit() {
		return guaranteeDeposit;
	}
	
	public void setGuaranteeDeposit(Money guaranteeDeposit) {
		this.guaranteeDeposit = guaranteeDeposit;
	}
	
	public Money getAssetsTransfer() {
		return assetsTransfer;
	}
	
	public void setAssetsTransfer(Money assetsTransfer) {
		this.assetsTransfer = assetsTransfer;
	}
	
	public Money getProxyCharging() {
		return proxyCharging;
	}
	
	public void setProxyCharging(Money proxyCharging) {
		this.proxyCharging = proxyCharging;
	}
	
}
