package com.born.fcs.pm.ws.info.check;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 保后检查报告 - 基本情况
 * 
 * @author lirz
 * 
 * 2016-6-14 上午11:23:38
 */
public class FAfterwardsCheckBaseInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5343242714437067633L;
	
	private long baseId;
	private long formId;
	private String projectCode;
	private String projectName;
	private long customerId;
	private String customerName;
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
	private TimeUnitEnum timeUnit;
	private Money loanedAmount = new Money(0, 0);
	private Money repayedAmount = new Money(0, 0);
	private Money availableAmount = new Money(0, 0);
	private Money releaseBalance = new Money(0, 0);
	private Money guaranteeAmount = new Money(0, 0); //担保费
	private Money guaranteeDeposit = new Money(0, 0); //存入保证金
	private int collectYear;
	private int collectMonth;
	private String collectData;
	private String feedbackOpinion;
	private String customerOpinion;
	private String remark1;
	private String remark2;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private List<FAfterwardsCheckCollectionInfo> counters; //(反)担保方式
	private List<FAfterwardsCheckCollectionInfo> collections; //资料收集
	private List<FAfterwardsCreditConditionInfo> conditions; //授信落实条件
	
	public long getBaseId() {
		return this.baseId;
	}
	
	public void setBaseId(long baseId) {
		this.baseId = baseId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public TimeUnitEnum getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
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
	
	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
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
	
	public List<FAfterwardsCheckCollectionInfo> getCounters() {
		return this.counters;
	}
	
	public void setCounters(List<FAfterwardsCheckCollectionInfo> counters) {
		this.counters = counters;
	}
	
	public List<FAfterwardsCheckCollectionInfo> getCollections() {
		return this.collections;
	}
	
	public void setCollections(List<FAfterwardsCheckCollectionInfo> collections) {
		this.collections = collections;
	}

	public List<FAfterwardsCreditConditionInfo> getConditions() {
		return this.conditions;
	}

	public void setConditions(List<FAfterwardsCreditConditionInfo> conditions) {
		this.conditions = conditions;
	}
	
}
