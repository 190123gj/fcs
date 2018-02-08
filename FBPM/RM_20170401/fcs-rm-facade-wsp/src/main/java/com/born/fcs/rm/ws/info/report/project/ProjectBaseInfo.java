package com.born.fcs.rm.ws.info.report.project;

import java.util.Date;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 项目基本信息
 * 
 * @author lirz
 * 
 * 2016-8-9 下午6:08:05
 */
public class ProjectBaseInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5289581510540424899L;
	
	/** 主键 */
	private long projectBaseId;
	/** 报告ID */
	private long reportId;
	/** 报送年 */
	private int reportYear;
	/** 报送月 */
	private int reportMonth;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private String customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private String customerType;
	/** 所属部门ID */
	private long deptId;
	/** 部门编号 */
	private String deptCode;
	/** 所属部门名称 */
	private String deptName;
	/** 部门路径 */
	private String deptPath;
	/** 部门路径名称 */
	private String deptPathName;
	/** 过会时间,项目通过风险评审会时间 */
	private Date riskReviewTime;
	/** 项目渠道ID */
	private long projectChannelId;
	/** 项目渠道名称 */
	private String projectChannelName;
	/** 二级项目渠道 */
	private long projectSubChannelId;
	/** 二级项目渠道名称 */
	private String projectSubChannelName;
	/** 资金渠道ID */
	private long capitalChannelId;
	/** 资金渠道名称 */
	private String capitalChannelName;
	/** 二级资金渠道 */
	private long capitalSubChannelId;
	/** 二级资金渠道名称 */
	private String capitalSubChannelName;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 授信金额 */
	private Money amount;
	/** 期限 */
	private int timeLimit;
	/** 期限单位 */
	private String timeUnit;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 是否本年新增项目 */
	private String newAdd;
	/** 是否在保项目 */
	private String releasable;
	/** 客户经理名称 */
	private String busiManagerName;
	/** 风险经理名称 */
	private String riskManagerName;
	/** 法务经理名称 */
	private String legalManagerName;
	/** 费率 */
	private double contractRate;
	/** 收费类型 */
	private String contractType;
	/** 推进情况 */
	private String progress;
	/** 受理时间,立项通过时间 */
	private Date setupApprovalTime;
	/** 项目尽调提交时间(送审) */
	private Date investigationSubmitTime;
	/** 项目尽调审核通过时间 */
	private Date investigationApprovalTime;
	/** 上会开始时间 */
	private Date councilStartTime;
	/** 上会通过时间 */
	private Date councilApprovalTime;
	/** 担保费用 */
	private double guaranteeFee;
	/** 担保费用类型 */
	private String guaranteeType;
	/** 储备情况 */
	private String storage;
	/** 风险等级 */
	private String riskLevel;
	/** 业务类型 */
	private String busiSubType;
	/** 业务类型名称 */
	private String busiSubTypeName;
	/** 所属行业 */
	private String industryCode;
	/** 行业名称 */
	private String industryName;
	/** 项目阶段 */
	private String phases;
	/** 项目阶段状态 */
	private String phasesStatus;
	/** 项目状态(正常、暂缓、未成立、完成) */
	private String status;
	/** 创建时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getProjectBaseId() {
		return projectBaseId;
	}
	
	public void setProjectBaseId(long projectBaseId) {
		this.projectBaseId = projectBaseId;
	}
	
	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public Date getRiskReviewTime() {
		return riskReviewTime;
	}
	
	public void setRiskReviewTime(Date riskReviewTime) {
		this.riskReviewTime = riskReviewTime;
	}
	
	public long getProjectChannelId() {
		return projectChannelId;
	}
	
	public void setProjectChannelId(long projectChannelId) {
		this.projectChannelId = projectChannelId;
	}
	
	public String getProjectChannelName() {
		return projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public long getProjectSubChannelId() {
		return projectSubChannelId;
	}
	
	public void setProjectSubChannelId(long projectSubChannelId) {
		this.projectSubChannelId = projectSubChannelId;
	}
	
	public String getProjectSubChannelName() {
		return projectSubChannelName;
	}
	
	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}
	
	public long getCapitalChannelId() {
		return capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public long getCapitalSubChannelId() {
		return capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelName() {
		return capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getNewAdd() {
		return newAdd;
	}
	
	public void setNewAdd(String newAdd) {
		this.newAdd = newAdd;
	}
	
	public String getReleasable() {
		return releasable;
	}
	
	public void setReleasable(String releasable) {
		this.releasable = releasable;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getRiskManagerName() {
		return riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getLegalManagerName() {
		return legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public double getContractRate() {
		return contractRate;
	}
	
	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}
	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getProgress() {
		return progress;
	}
	
	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	public Date getSetupApprovalTime() {
		return setupApprovalTime;
	}
	
	public void setSetupApprovalTime(Date setupApprovalTime) {
		this.setupApprovalTime = setupApprovalTime;
	}
	
	public Date getInvestigationSubmitTime() {
		return investigationSubmitTime;
	}
	
	public void setInvestigationSubmitTime(Date investigationSubmitTime) {
		this.investigationSubmitTime = investigationSubmitTime;
	}
	
	public Date getInvestigationApprovalTime() {
		return investigationApprovalTime;
	}
	
	public void setInvestigationApprovalTime(Date investigationApprovalTime) {
		this.investigationApprovalTime = investigationApprovalTime;
	}
	
	public Date getCouncilStartTime() {
		return councilStartTime;
	}
	
	public void setCouncilStartTime(Date councilStartTime) {
		this.councilStartTime = councilStartTime;
	}
	
	public Date getCouncilApprovalTime() {
		return councilApprovalTime;
	}
	
	public void setCouncilApprovalTime(Date councilApprovalTime) {
		this.councilApprovalTime = councilApprovalTime;
	}
	
	public double getGuaranteeFee() {
		return guaranteeFee;
	}
	
	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public String getGuaranteeType() {
		return guaranteeType;
	}
	
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	
	public String getStorage() {
		return storage;
	}
	
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	public String getRiskLevel() {
		return riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getBusiSubType() {
		return busiSubType;
	}
	
	public void setBusiSubType(String busiSubType) {
		this.busiSubType = busiSubType;
	}
	
	public String getBusiSubTypeName() {
		return busiSubTypeName;
	}
	
	public void setBusiSubTypeName(String busiSubTypeName) {
		this.busiSubTypeName = busiSubTypeName;
	}
	
	public String getIndustryCode() {
		return industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getPhasesStatus() {
		return phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
