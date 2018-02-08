package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目渠道关系
 * @author wuzj
 */
public class ProjectChannelRelationInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6856976148442583523L;
	
	/** 主键 */
	private long id;
	/** 外部唯一 */
	private String bizNo;
	/** 阶段 */
	private ProjectPhasesEnum phases;
	/** 项目编号 */
	private String projectCode;
	/** 关系 */
	private ChannelRelationEnum channelRelation;
	/** 渠道ID */
	private long channelId;
	/** 渠道编码 */
	private String channelCode;
	/** 渠道类型 */
	private String channelType;
	/** 渠道名称 */
	private String channelName;
	/** 二级渠道ID */
	private long subChannelId;
	/** 二级渠道编码 */
	private String subChannelCode;
	/** 二级渠道类型 */
	private String subChannelType;
	/** 二级渠道名称 */
	private String subChannelName;
	/** 合同金额 */
	private Money contractAmount = new Money(0, 0);
	/** 流动资金贷款(合同金额) */
	private Money liquidityLoansAmount = new Money(0, 0);
	/** 固定资产融资(合同金额) */
	private Money financialAmount = new Money(0, 0);
	/** 承兑汇票担保(合同金额) */
	private Money acceptanceBillAmount = new Money(0, 0);
	/** 信用证担保(合同金额) */
	private Money creditAmount = new Money(0, 0);
	
	/** 已放款金额 */
	private Money loanedAmount = new Money(0, 0);
	private Money loanLiquidityLoanAmount = new Money(0, 0);
	private Money loanFixedAssetsFinancingAmount = new Money(0, 0);
	private Money loanAcceptanceBillAmount = new Money(0, 0);
	private Money loanCreditLetterAmount = new Money(0, 0);
	/** 已用款金额 */
	private Money usedAmount = new Money(0, 0);
	/** 已代偿本金 */
	private Money compAmount = new Money(0, 0);
	private Money compLiquidityLoanAmount = new Money(0, 0);
	private Money compFixedAssetsFinancingAmount = new Money(0, 0);
	private Money compAcceptanceBillAmount = new Money(0, 0);
	private Money compCreditLetterAmount = new Money(0, 0);
	/** 可解保金额 */
	private Money releasableAmount = new Money(0, 0);
	/** 已解保金额 */
	private Money releasedAmount = new Money(0, 0);
	private Money releaseLiquidityLoanAmount = new Money(0, 0);
	private Money releaseFixedAssetsFinancingAmount = new Money(0, 0);
	private Money releaseAcceptanceBillAmount = new Money(0, 0);
	private Money releaseCreditLetterAmount = new Money(0, 0);
	/** 还款金额 （委贷本金收回、解保还款） */
	private Money repayedAmount = new Money(0, 0);
	
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public Money getLiquidityLoansAmount() {
		return liquidityLoansAmount;
	}
	
	public void setLiquidityLoansAmount(Money liquidityLoansAmount) {
		this.liquidityLoansAmount = liquidityLoansAmount;
	}
	
	public Money getFinancialAmount() {
		return financialAmount;
	}
	
	public void setFinancialAmount(Money financialAmount) {
		this.financialAmount = financialAmount;
	}
	
	public Money getAcceptanceBillAmount() {
		return acceptanceBillAmount;
	}
	
	public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
		this.acceptanceBillAmount = acceptanceBillAmount;
	}
	
	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getBizNo() {
		return this.bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public ProjectPhasesEnum getPhases() {
		return this.phases;
	}
	
	public void setPhases(ProjectPhasesEnum phases) {
		this.phases = phases;
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
	
	public String getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public long getSubChannelId() {
		return this.subChannelId;
	}
	
	public void setSubChannelId(long subChannelId) {
		this.subChannelId = subChannelId;
	}
	
	public String getSubChannelCode() {
		return this.subChannelCode;
	}
	
	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}
	
	public String getSubChannelType() {
		return this.subChannelType;
	}
	
	public void setSubChannelType(String subChannelType) {
		this.subChannelType = subChannelType;
	}
	
	public String getSubChannelName() {
		return this.subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}
	
	public Money getContractAmount() {
		return contractAmount;
	}
	
	public void setContractAmount(Money contractAmount) {
		this.contractAmount = contractAmount;
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
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
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
