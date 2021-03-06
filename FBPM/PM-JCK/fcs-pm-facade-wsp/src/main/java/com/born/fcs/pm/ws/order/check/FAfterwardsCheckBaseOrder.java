package com.born.fcs.pm.ws.order.check;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 保后检查报告 - 基本情况
 * 
 * @author lirz
 * 
 * 2016-6-14 上午11:23:38
 */
public class FAfterwardsCheckBaseOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -6615262727207038618L;
	
	private long baseId;
	private String busiType;
	private String busiTypeName;
	private Money amount = new Money(0, 0);
	private Date startTime;
	private Date endTime;
	private String busiManagerName;
	private String riskManagerName;
	private String capitalChannelName;
	private String loanPurpose;
	private String spendWay;
	private int timeLimit;
	private String timeUnit;
	private Money loanedAmount = new Money(0, 0);
	private Money repayedAmount = new Money(0, 0);
	private Money availableAmount = new Money(0, 0);
	private Money releaseBalance = new Money(0, 0);
	private Money guaranteeAmount = new Money(0, 0);
	private Money guaranteeDeposit = new Money(0, 0);
	private int collectYear;
	private int collectMonth;
	private String collectData;
	private String feedbackOpinion;
	private String customerOpinion;
	
	private List<FAfterwardsCheckCollectionOrder> counters; //(反)担保方式
	private List<FAfterwardsCheckCollectionOrder> collections; //资料收集
	
	//========== getters and setters ==========
	
	public long getBaseId() {
		return this.baseId;
	}
	
	public void setBaseId(long baseId) {
		this.baseId = baseId;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public String getSpendWay() {
		return this.spendWay;
	}
	
	public void setSpendWay(String spendWay) {
		this.spendWay = spendWay;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Money getLoanedAmount() {
		return this.loanedAmount;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public Money getAvailableAmount() {
		return this.availableAmount;
	}
	
	public void setAvailableAmount(Money availableAmount) {
		this.availableAmount = availableAmount;
	}
	
	public Money getReleaseBalance() {
		return this.releaseBalance;
	}
	
	public void setReleaseBalance(Money releaseBalance) {
		this.releaseBalance = releaseBalance;
	}
	
	public Money getGuaranteeAmount() {
		return this.guaranteeAmount;
	}

	public void setGuaranteeAmount(Money guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public Money getGuaranteeDeposit() {
		return this.guaranteeDeposit;
	}

	public void setGuaranteeDeposit(Money guaranteeDeposit) {
		this.guaranteeDeposit = guaranteeDeposit;
	}

	public int getCollectYear() {
		return this.collectYear;
	}
	
	public void setCollectYear(int collectYear) {
		this.collectYear = collectYear;
	}
	
	public int getCollectMonth() {
		return this.collectMonth;
	}
	
	public void setCollectMonth(int collectMonth) {
		this.collectMonth = collectMonth;
	}
	
	public String getCollectData() {
		return this.collectData;
	}

	public void setCollectData(String collectData) {
		this.collectData = collectData;
	}

	public String getFeedbackOpinion() {
		return this.feedbackOpinion;
	}
	
	public void setFeedbackOpinion(String feedbackOpinion) {
		this.feedbackOpinion = feedbackOpinion;
	}
	
	public String getCustomerOpinion() {
		return this.customerOpinion;
	}
	
	public void setCustomerOpinion(String customerOpinion) {
		this.customerOpinion = customerOpinion;
	}
	
	public List<FAfterwardsCheckCollectionOrder> getCounters() {
		return this.counters;
	}
	
	public void setCounters(List<FAfterwardsCheckCollectionOrder> counters) {
		this.counters = counters;
	}
	
	public List<FAfterwardsCheckCollectionOrder> getCollections() {
		return this.collections;
	}
	
	public void setCollections(List<FAfterwardsCheckCollectionOrder> collections) {
		this.collections = collections;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
