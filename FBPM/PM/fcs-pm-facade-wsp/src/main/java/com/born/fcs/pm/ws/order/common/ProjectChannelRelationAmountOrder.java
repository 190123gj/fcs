package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目渠道关系Order
 * @author wuzj
 */
public class ProjectChannelRelationAmountOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 675795588954511568L;
	/** 渠道ID */
	private long channelId;
	/** 渠道编码 */
	private String channelCode;
	/** 项目编号 */
	private String projectCode;
	/** 渠道关系 */
	private ChannelRelationEnum channelRelation;
	/** 合同金额 */
	private Money contractAmount = new Money(0, 0);
	/** 本次放款金额 */
	private Money loanedAmount = new Money(0, 0);
	private Money loanLiquidityLoanAmount = new Money(0, 0);
	private Money loanFixedAssetsFinancingAmount = new Money(0, 0);
	private Money loanAcceptanceBillAmount = new Money(0, 0);
	private Money loanCreditLetterAmount = new Money(0, 0);
	/** 本次用款金额 */
	private Money usedAmount = new Money(0, 0);
	/** 本次代偿本金 */
	private Money compAmount = new Money(0, 0);
	private Money compLiquidityLoanAmount = new Money(0, 0);
	private Money compFixedAssetsFinancingAmount = new Money(0, 0);
	private Money compAcceptanceBillAmount = new Money(0, 0);
	private Money compCreditLetterAmount = new Money(0, 0);
	
	/** 本次或总的可解保金额 */
	private Money releasableAmount = new Money(0, 0);
	/** 是否设置总的解保金额 */
	private boolean updateReleasable = false;
	/** 本次解保金额 */
	private Money releasedAmount = new Money(0, 0);
	private Money releaseLiquidityLoanAmount = new Money(0, 0);
	private Money releaseFixedAssetsFinancingAmount = new Money(0, 0);
	private Money releaseAcceptanceBillAmount = new Money(0, 0);
	private Money releaseCreditLetterAmount = new Money(0, 0);
	/** 本次还款金额 */
	private Money repayedAmount = new Money(0, 0);
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
	}
	
	public long getChannelId() {
		return this.channelId;
	}
	
	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ChannelRelationEnum getChannelRelation() {
		return this.channelRelation;
	}
	
	public void setChannelRelation(ChannelRelationEnum channelRelation) {
		this.channelRelation = channelRelation;
	}
	
	public Money getContractAmount() {
		return this.contractAmount;
	}
	
	public void setContractAmount(Money contractAmount) {
		this.contractAmount = contractAmount;
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
	
	public Money getCompAmount() {
		return this.compAmount;
	}
	
	public void setCompAmount(Money compAmount) {
		this.compAmount = compAmount;
	}
	
	public Money getLoanLiquidityLoanAmount() {
		return this.loanLiquidityLoanAmount;
	}
	
	public void setLoanLiquidityLoanAmount(Money loanLiquidityLoanAmount) {
		this.loanLiquidityLoanAmount = loanLiquidityLoanAmount;
	}
	
	public Money getLoanFixedAssetsFinancingAmount() {
		return this.loanFixedAssetsFinancingAmount;
	}
	
	public void setLoanFixedAssetsFinancingAmount(Money loanFixedAssetsFinancingAmount) {
		this.loanFixedAssetsFinancingAmount = loanFixedAssetsFinancingAmount;
	}
	
	public Money getLoanAcceptanceBillAmount() {
		return this.loanAcceptanceBillAmount;
	}
	
	public void setLoanAcceptanceBillAmount(Money loanAcceptanceBillAmount) {
		this.loanAcceptanceBillAmount = loanAcceptanceBillAmount;
	}
	
	public Money getLoanCreditLetterAmount() {
		return this.loanCreditLetterAmount;
	}
	
	public void setLoanCreditLetterAmount(Money loanCreditLetterAmount) {
		this.loanCreditLetterAmount = loanCreditLetterAmount;
	}
	
	public Money getReleasableAmount() {
		return this.releasableAmount;
	}
	
	public void setReleasableAmount(Money releasableAmount) {
		this.releasableAmount = releasableAmount;
	}
	
	public Money getReleasedAmount() {
		return this.releasedAmount;
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	
	public boolean isUpdateReleasable() {
		return this.updateReleasable;
	}
	
	public void setUpdateReleasable(boolean updateReleasable) {
		this.updateReleasable = updateReleasable;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public Money getCompLiquidityLoanAmount() {
		return this.compLiquidityLoanAmount;
	}
	
	public void setCompLiquidityLoanAmount(Money compLiquidityLoanAmount) {
		this.compLiquidityLoanAmount = compLiquidityLoanAmount;
	}
	
	public Money getCompFixedAssetsFinancingAmount() {
		return this.compFixedAssetsFinancingAmount;
	}
	
	public void setCompFixedAssetsFinancingAmount(Money compFixedAssetsFinancingAmount) {
		this.compFixedAssetsFinancingAmount = compFixedAssetsFinancingAmount;
	}
	
	public Money getCompAcceptanceBillAmount() {
		return this.compAcceptanceBillAmount;
	}
	
	public void setCompAcceptanceBillAmount(Money compAcceptanceBillAmount) {
		this.compAcceptanceBillAmount = compAcceptanceBillAmount;
	}
	
	public Money getCompCreditLetterAmount() {
		return this.compCreditLetterAmount;
	}
	
	public void setCompCreditLetterAmount(Money compCreditLetterAmount) {
		this.compCreditLetterAmount = compCreditLetterAmount;
	}
	
	public Money getReleaseLiquidityLoanAmount() {
		return this.releaseLiquidityLoanAmount;
	}
	
	public void setReleaseLiquidityLoanAmount(Money releaseLiquidityLoanAmount) {
		this.releaseLiquidityLoanAmount = releaseLiquidityLoanAmount;
	}
	
	public Money getReleaseFixedAssetsFinancingAmount() {
		return this.releaseFixedAssetsFinancingAmount;
	}
	
	public void setReleaseFixedAssetsFinancingAmount(Money releaseFixedAssetsFinancingAmount) {
		this.releaseFixedAssetsFinancingAmount = releaseFixedAssetsFinancingAmount;
	}
	
	public Money getReleaseAcceptanceBillAmount() {
		return this.releaseAcceptanceBillAmount;
	}
	
	public void setReleaseAcceptanceBillAmount(Money releaseAcceptanceBillAmount) {
		this.releaseAcceptanceBillAmount = releaseAcceptanceBillAmount;
	}
	
	public Money getReleaseCreditLetterAmount() {
		return this.releaseCreditLetterAmount;
	}
	
	public void setReleaseCreditLetterAmount(Money releaseCreditLetterAmount) {
		this.releaseCreditLetterAmount = releaseCreditLetterAmount;
	}
	
}
