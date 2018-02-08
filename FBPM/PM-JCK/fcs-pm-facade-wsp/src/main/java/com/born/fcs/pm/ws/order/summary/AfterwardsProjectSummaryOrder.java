/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.summary;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 保后汇总Order
 *
 * @author jil
 *
 */
public class AfterwardsProjectSummaryOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6953235945072301582L;
	
	private Long summaryId;
	
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
	
	public Long getSummaryId() {
		return summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
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
	
	public Money getLoanReleasingAmount() {
		return loanReleasingAmount;
	}
	
	public void setLoanReleasingAmount(Money loanReleasingAmount) {
		this.loanReleasingAmount = loanReleasingAmount;
	}
	
	public int getLoanHouseholds() {
		return loanHouseholds;
	}
	
	public void setLoanHouseholds(int loanHouseholds) {
		this.loanHouseholds = loanHouseholds;
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
	
}
