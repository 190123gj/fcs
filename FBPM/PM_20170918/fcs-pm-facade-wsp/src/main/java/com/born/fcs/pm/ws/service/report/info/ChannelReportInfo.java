package com.born.fcs.pm.ws.service.report.info;

import java.io.Serializable;
import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * 渠道报表数据
 * */
public class ChannelReportInfo implements Serializable {
	
	private static final long serialVersionUID = 4661021567868641825L;
	
	private Date projectDate;
	/** 银行或非银行渠道查询时做统计用：渠道关联的项目总数 */
	private long projectId;
	/** 在保笔数 */
	private String inProject;
	
	private String projectCode;
	
	private String projectName;
	/** 在保 非在保 */
	private String customName1;
	
	private String phasesStatus;
	
	private String phases;
	
	private String customerName;
	
	private String enterpriseType;
	
	private String customerLevel;
	
	private String scale;
	
	private String provinceCode;
	
	private String provinceName;
	
	private String cityCode;
	
	private String cityName;
	
	private String industryCode;
	
	private String industryName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String startTime;
	
	private String endTime;
	/** 合同金额 同一个项目关联多个资金渠道，contractAmount=原金额/渠道个数 */
	private Money contractAmount = new Money(0, 0);
	/** 在保余额 同一个项目关联多个资金渠道， blance= 原金额/渠道个数 */
	private Money blance = new Money(0, 0);
	
	private Money accumulatedIssueAmount = new Money(0, 0);
	
	private Money loanedAmount = new Money(0, 0);
	
	private Money usedAmount = new Money(0, 0);
	
	private Money repayedAmount = new Money(0, 0);
	
	private Money chargedAmount = new Money(0, 0);
	
	private Money refundAmount = new Money(0, 0);
	
	private Money releasedAmount = new Money(0, 0);
	
	private String fanGuaranteeMethord;
	
	private long channelId;
	
	private String channelCode;
	
	private String channelName;
	
	private String channelType;
	
	private String subChannelName;
	/** 渠道关联类型：项目或资金 */
	private String channelRelation;
	/** 项目立项时间 */
	private Date setupDate;
	/** 客户入库时间 */
	private Date customerAddTime;
	
	private Date rawUpdateTime;
	/** 项目关联的同类渠道数 */
	private long num;
	//********* 银行渠道信息****************
	/** 授信起始日 */
	private Date creditStartDate;
	/** 授信截止日 */
	private Date creditEndDate;
	/** 风险分摊比例 */
	private double lossAllocationRate;
	/** 单笔限额 */
	private String singleLimitString;
	/** 代偿期限 */
	private String compensatoryString;
	/** 授信额度 */
	private String creditAmountString;
	/** 保证金比例 */
	private double bondRate;
	
	public Date getCreditStartDate() {
		return this.creditStartDate;
	}
	
	public void setCreditStartDate(Date creditStartDate) {
		this.creditStartDate = creditStartDate;
	}
	
	public Date getCreditEndDate() {
		return this.creditEndDate;
	}
	
	public void setCreditEndDate(Date creditEndDate) {
		this.creditEndDate = creditEndDate;
	}
	
	public double getLossAllocationRate() {
		return this.lossAllocationRate;
	}
	
	public void setLossAllocationRate(double lossAllocationRate) {
		this.lossAllocationRate = lossAllocationRate;
	}
	
	public String getSingleLimitString() {
		return this.singleLimitString;
	}
	
	public void setSingleLimitString(String singleLimitString) {
		this.singleLimitString = singleLimitString;
	}
	
	public String getCompensatoryString() {
		return this.compensatoryString;
	}
	
	public void setCompensatoryString(String compensatoryString) {
		this.compensatoryString = compensatoryString;
	}
	
	public String getCreditAmountString() {
		return this.creditAmountString;
	}
	
	public void setCreditAmountString(String creditAmountString) {
		this.creditAmountString = creditAmountString;
	}
	
	public double getBondRate() {
		return this.bondRate;
	}
	
	public void setBondRate(double bondRate) {
		this.bondRate = bondRate;
	}
	
	public void setBlance(Money blance) {
		this.blance = blance;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		this.chargedAmount = chargedAmount;
	}
	
	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
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
	
	public String getCustomName1() {
		return this.customName1;
	}
	
	public void setCustomName1(String customName1) {
		this.customName1 = customName1;
	}
	
	public String getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public String getPhases() {
		return this.phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getEnterpriseType() {
		return this.enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getCustomerLevel() {
		return this.customerLevel;
	}
	
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}
	
	public String getScale() {
		return this.scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public String getProvinceCode() {
		return this.provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityCode() {
		return this.cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
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
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public Date getProjectDate() {
		return this.projectDate;
	}
	
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}
	
	public Money getContractAmount() {
		return this.contractAmount;
	}
	
	public void setContractAmount(Money contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	public Money getAccumulatedIssueAmount() {
		return this.accumulatedIssueAmount;
	}
	
	public void setAccumulatedIssueAmount(Money accumulatedIssueAmount) {
		this.accumulatedIssueAmount = accumulatedIssueAmount;
	}
	
	public String getFanGuaranteeMethord() {
		return this.fanGuaranteeMethord;
	}
	
	public void setFanGuaranteeMethord(String fanGuaranteeMethord) {
		this.fanGuaranteeMethord = fanGuaranteeMethord;
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
	
	public String getChannelName() {
		return this.channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	public String getSubChannelName() {
		return this.subChannelName;
	}
	
	public void setSubChannelName(String subChannelName) {
		this.subChannelName = subChannelName;
	}
	
	public String getChannelRelation() {
		return this.channelRelation;
	}
	
	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
	}
	
	public Date getSetupDate() {
		return this.setupDate;
	}
	
	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}
	
	public Date getCustomerAddTime() {
		return this.customerAddTime;
	}
	
	public void setCustomerAddTime(Date customerAddTime) {
		this.customerAddTime = customerAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public long getNum() {
		return this.num;
	}
	
	public void setNum(long num) {
		this.num = num;
	}
	
	public Money getBlance() {
		return this.blance;
	}
	
	public Money getLoanedAmount() {
		return this.loanedAmount;
	}
	
	public Money getUsedAmount() {
		return this.usedAmount;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public Money getChargedAmount() {
		return this.chargedAmount;
	}
	
	public Money getRefundAmount() {
		return this.refundAmount;
	}
	
	public Money getReleasedAmount() {
		return this.releasedAmount;
	}
	
	public String getInProject() {
		return this.inProject;
	}
	
	public void setInProject(String inProject) {
		this.inProject = inProject;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannelReportInfo [projectDate=");
		builder.append(projectDate);
		builder.append(", projectId=");
		builder.append(projectId);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", customName1=");
		builder.append(customName1);
		builder.append(", phasesStatus=");
		builder.append(phasesStatus);
		builder.append(", phases=");
		builder.append(phases);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", enterpriseType=");
		builder.append(enterpriseType);
		builder.append(", customerLevel=");
		builder.append(customerLevel);
		builder.append(", scale=");
		builder.append(scale);
		builder.append(", provinceCode=");
		builder.append(provinceCode);
		builder.append(", provinceName=");
		builder.append(provinceName);
		builder.append(", cityCode=");
		builder.append(cityCode);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", industryCode=");
		builder.append(industryCode);
		builder.append(", industryName=");
		builder.append(industryName);
		builder.append(", busiType=");
		builder.append(busiType);
		builder.append(", busiTypeName=");
		builder.append(busiTypeName);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", contractAmount=");
		builder.append(contractAmount);
		builder.append(", blance=");
		builder.append(blance);
		builder.append(", accumulatedIssueAmount=");
		builder.append(accumulatedIssueAmount);
		builder.append(", loanedAmount=");
		builder.append(loanedAmount);
		builder.append(", usedAmount=");
		builder.append(usedAmount);
		builder.append(", repayedAmount=");
		builder.append(repayedAmount);
		builder.append(", chargedAmount=");
		builder.append(chargedAmount);
		builder.append(", refundAmount=");
		builder.append(refundAmount);
		builder.append(", releasedAmount=");
		builder.append(releasedAmount);
		builder.append(", fanGuaranteeMethord=");
		builder.append(fanGuaranteeMethord);
		builder.append(", channelId=");
		builder.append(channelId);
		builder.append(", channelCode=");
		builder.append(channelCode);
		builder.append(", channelName=");
		builder.append(channelName);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", subChannelName=");
		builder.append(subChannelName);
		builder.append(", channelRelation=");
		builder.append(channelRelation);
		builder.append(", setupDate=");
		builder.append(setupDate);
		builder.append(", customerAddTime=");
		builder.append(customerAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", num=");
		builder.append(num);
		builder.append(", creditStartDate=");
		builder.append(creditStartDate);
		builder.append(", creditEndDate=");
		builder.append(creditEndDate);
		builder.append(", lossAllocationRate=");
		builder.append(lossAllocationRate);
		builder.append(", singleLimitString=");
		builder.append(singleLimitString);
		builder.append(", compensatoryString=");
		builder.append(compensatoryString);
		builder.append(", creditAmountString=");
		builder.append(creditAmountString);
		builder.append(", bondRate=");
		builder.append(bondRate);
		builder.append("]");
		return builder.toString();
	}
	
}
