package com.born.fcs.pm.ws.info.report;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 项目基本情况(基层定义报表)
 * 
 * @author lirz
 * 
 * 2017-2-9 上午11:49:26
 * 
 */
public class ProjectInfoBaseInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1124159960916906115L;
	
	private long id;
	
	private long projectId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	private String belongToNc;
	
	private String phases;
	
	private String phasesStatus;
	
	private String status;
	
	private Date onWillDate;
	
	private String projectChannelCode;
	
	private String projectChannelName;
	
	private String projectChannelType;
	
	private String projectSubChannelName;
	
	private long capitalChannelId;
	
	private String capitalChannelName;
	
	private String channalType;
	
	private String capitalSubChannelName;
	
	private String channalCode;
	
	private List<ProjectChannelRelationInfo> capitalChannel; //资金渠道
	
	private String busiType;
	
	private String busiTypeName; //产品名称
	
	private String productType; //产品类型
	
	private String industryCode;
	
	private String industryName;
	
	public List<ProjectChannelRelationInfo> getCapitalChannel() {
		return this.capitalChannel;
	}

	public void setCapitalChannel(List<ProjectChannelRelationInfo> capitalChannel) {
		this.capitalChannel = capitalChannel;
	}

	private int timeLimit;
	
	private String timeUnit;
	
	private Date startTime;
	
	private Date endTime;
	
	private Money amount = new Money(0, 0);
	
	private Money guaranteeAmount = new Money(0, 0);
	
	private double guaranteeFeeRate;
	
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	private String riskManagerName;
	private String legalManagerName;
	
	//还款计划
	private List<ChargeRepayPlanDetailInfo> repays;
	//收费计划
	private List<ChargeRepayPlanDetailInfo> charges;
	
	//ChargeRepayPlanDetailInfo
	//	List<ChargeRepayPlanDetailDO> details = chargeRepayPlanDetailDAO.findByPlanId(info
	//		.getPlanId());
	//chargeRepayPlanService
	
	//是否本年新增项目
	private String isNew;
	//是否在保项目
	private String customName1;

	private String customName2;

	private String customName3;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getBelongToNc() {
		return this.belongToNc;
	}
	
	public void setBelongToNc(String belongToNc) {
		this.belongToNc = belongToNc;
	}
	
	public String getPhases() {
		return this.phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getOnWillDate() {
		return this.onWillDate;
	}
	
	public void setOnWillDate(Date onWillDate) {
		this.onWillDate = onWillDate;
	}
	
	public String getProjectChannelCode() {
		return this.projectChannelCode;
	}
	
	public void setProjectChannelCode(String projectChannelCode) {
		this.projectChannelCode = projectChannelCode;
	}
	
	public String getProjectChannelName() {
		return this.projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public String getProjectChannelType() {
		return this.projectChannelType;
	}
	
	public void setProjectChannelType(String projectChannelType) {
		this.projectChannelType = projectChannelType;
	}
	
	public String getProjectSubChannelName() {
		return this.projectSubChannelName;
	}
	
	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}
	
	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public String getChannalType() {
		return this.channalType;
	}
	
	public void setChannalType(String channalType) {
		this.channalType = channalType;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public String getChannalCode() {
		return this.channalCode;
	}
	
	public void setChannalCode(String channalCode) {
		this.channalCode = channalCode;
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getGuaranteeAmount() {
		return this.guaranteeAmount;
	}
	
	public void setGuaranteeAmount(Money guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	
	public double getGuaranteeFeeRate() {
		long fee = (long)(this.guaranteeFeeRate * 100);
		if (fee == 0 && this.amount.getCent() != 0) {
			return (this.guaranteeAmount.getCent() * 100.0d / this.amount.getCent()); 
		}
		return this.guaranteeFeeRate;
	}
	
	public void setGuaranteeFeeRate(double guaranteeFeeRate) {
		this.guaranteeFeeRate = guaranteeFeeRate;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
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

	public String getLegalManagerName() {
		return this.legalManagerName;
	}

	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}

	public List<ChargeRepayPlanDetailInfo> getRepays() {
		return this.repays;
	}
	
	public void setRepays(List<ChargeRepayPlanDetailInfo> repays) {
		this.repays = repays;
	}
	
	public List<ChargeRepayPlanDetailInfo> getCharges() {
		return this.charges;
	}
	
	public void setCharges(List<ChargeRepayPlanDetailInfo> charges) {
		this.charges = charges;
	}

	public String getIsNew() {
		return this.isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getCustomName1() {
		return this.customName1;
	}

	public void setCustomName1(String customName1) {
		this.customName1 = customName1;
	}

	public String getCustomName2() {
		return this.customName2;
	}

	public void setCustomName2(String customName2) {
		this.customName2 = customName2;
	}

	public String getCustomName3() {
		return this.customName3;
	}

	public void setCustomName3(String customName3) {
		this.customName3 = customName3;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
