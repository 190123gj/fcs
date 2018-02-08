/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

// auto generated imports
import com.yjf.common.lang.util.money.Money;

/**
 * 保后项目汇总
 * @author Ji
 *
 */
public class AfterwardsProjectSummaryFormDO extends SimpleFormDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1217522702835909313L;
	
	//保后项目汇总信息
	private long summaryId;
	
	private long formId;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String reportPeriod;
	
	private long submitManId;
	
	private String submitManName;
	
	private String submitManAccount;
	
	private int guaranteeHouseholds;
	
	private Money guaranteeReleasingAmount = new Money(0, 0);
	
	private int loanHouseholds;
	
	private Money loanReleasingAmount = new Money(0, 0);
	
	private String creditRisk;
	
	private String creditAfterCheck;
	
	private String creditAfterCheckProb;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public long getSummaryId() {
		return summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public String getReportPeriod() {
		return reportPeriod;
	}
	
	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}
	
	public long getSubmitManId() {
		return submitManId;
	}
	
	public void setSubmitManId(long submitManId) {
		this.submitManId = submitManId;
	}
	
	public String getSubmitManName() {
		return submitManName;
	}
	
	public void setSubmitManName(String submitManName) {
		this.submitManName = submitManName;
	}
	
	public String getSubmitManAccount() {
		return submitManAccount;
	}
	
	public void setSubmitManAccount(String submitManAccount) {
		this.submitManAccount = submitManAccount;
	}
	
	public int getGuaranteeHouseholds() {
		return guaranteeHouseholds;
	}
	
	public void setGuaranteeHouseholds(int guaranteeHouseholds) {
		this.guaranteeHouseholds = guaranteeHouseholds;
	}
	
	public Money getGuaranteeReleasingAmount() {
		return guaranteeReleasingAmount;
	}
	
	public void setGuaranteeReleasingAmount(Money guaranteeReleasingAmount) {
		this.guaranteeReleasingAmount = guaranteeReleasingAmount;
	}
	
	public int getLoanHouseholds() {
		return loanHouseholds;
	}
	
	public void setLoanHouseholds(int loanHouseholds) {
		this.loanHouseholds = loanHouseholds;
	}
	
	public Money getLoanReleasingAmount() {
		return loanReleasingAmount;
	}
	
	public void setLoanReleasingAmount(Money loanReleasingAmount) {
		this.loanReleasingAmount = loanReleasingAmount;
	}
	
	public String getCreditRisk() {
		return creditRisk;
	}
	
	public void setCreditRisk(String creditRisk) {
		this.creditRisk = creditRisk;
	}
	
	public String getCreditAfterCheck() {
		return creditAfterCheck;
	}
	
	public void setCreditAfterCheck(String creditAfterCheck) {
		this.creditAfterCheck = creditAfterCheck;
	}
	
	public String getCreditAfterCheckProb() {
		return creditAfterCheckProb;
	}
	
	public void setCreditAfterCheckProb(String creditAfterCheckProb) {
		this.creditAfterCheckProb = creditAfterCheckProb;
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
