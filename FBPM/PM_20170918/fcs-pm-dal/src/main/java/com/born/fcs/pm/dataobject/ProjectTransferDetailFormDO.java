/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

// auto generated imports
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 项目移交明细表单明细
 *
 * @author wuzj
 */
public class ProjectTransferDetailFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = 2531967817041109576L;
	
	private long id;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String projectPhase;
	
	private String projectStatus;
	
	private Money projectAmount = new Money(0, 0);
	
	private Money projectBalance = new Money(0, 0);
	
	private long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private String transferStatus;
	
	private String transferType;
	
	private Date transferTime;
	
	private long originalUserId;
	
	private String originalUserAccount;
	
	private String originalUserName;
	
	private long originalUserDeptId;
	
	private String originalUserDeptName;
	
	private long originalUserbId;
	
	private String originalUserbAccount;
	
	private String originalUserbName;
	
	private long originalUserbDeptId;
	
	private String originalUserbDeptName;
	
	private long acceptUserId;
	
	private String acceptUserAccount;
	
	private String acceptUserName;
	
	private long acceptUserDeptId;
	
	private String acceptUserDeptName;
	
	private long acceptUserbId;
	
	private String acceptUserbAccount;
	
	private String acceptUserbName;
	
	private long acceptUserbDeptId;
	
	private String acceptUserbDeptName;
	
	private String transferFile;
	
	private String formChange;
	
	private String riskCouncilSummary;
	
	private String projectApproval;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public String getProjectPhase() {
		return this.projectPhase;
	}
	
	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}
	
	public String getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public Money getProjectAmount() {
		return this.projectAmount;
	}
	
	public void setProjectAmount(Money projectAmount) {
		this.projectAmount = projectAmount;
	}
	
	public Money getProjectBalance() {
		return this.projectBalance;
	}
	
	public void setProjectBalance(Money projectBalance) {
		this.projectBalance = projectBalance;
	}
	
	public long getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return this.applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return this.applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public String getTransferStatus() {
		return this.transferStatus;
	}
	
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	public String getTransferType() {
		return this.transferType;
	}
	
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public long getOriginalUserId() {
		return this.originalUserId;
	}
	
	public void setOriginalUserId(long originalUserId) {
		this.originalUserId = originalUserId;
	}
	
	public String getOriginalUserAccount() {
		return this.originalUserAccount;
	}
	
	public void setOriginalUserAccount(String originalUserAccount) {
		this.originalUserAccount = originalUserAccount;
	}
	
	public String getOriginalUserName() {
		return this.originalUserName;
	}
	
	public void setOriginalUserName(String originalUserName) {
		this.originalUserName = originalUserName;
	}
	
	public long getOriginalUserDeptId() {
		return this.originalUserDeptId;
	}
	
	public void setOriginalUserDeptId(long originalUserDeptId) {
		this.originalUserDeptId = originalUserDeptId;
	}
	
	public String getOriginalUserDeptName() {
		return this.originalUserDeptName;
	}
	
	public void setOriginalUserDeptName(String originalUserDeptName) {
		this.originalUserDeptName = originalUserDeptName;
	}
	
	public long getOriginalUserbId() {
		return this.originalUserbId;
	}
	
	public void setOriginalUserbId(long originalUserbId) {
		this.originalUserbId = originalUserbId;
	}
	
	public String getOriginalUserbAccount() {
		return this.originalUserbAccount;
	}
	
	public void setOriginalUserbAccount(String originalUserbAccount) {
		this.originalUserbAccount = originalUserbAccount;
	}
	
	public String getOriginalUserbName() {
		return this.originalUserbName;
	}
	
	public void setOriginalUserbName(String originalUserbName) {
		this.originalUserbName = originalUserbName;
	}
	
	public long getOriginalUserbDeptId() {
		return this.originalUserbDeptId;
	}
	
	public void setOriginalUserbDeptId(long originalUserbDeptId) {
		this.originalUserbDeptId = originalUserbDeptId;
	}
	
	public String getOriginalUserbDeptName() {
		return this.originalUserbDeptName;
	}
	
	public void setOriginalUserbDeptName(String originalUserbDeptName) {
		this.originalUserbDeptName = originalUserbDeptName;
	}
	
	public long getAcceptUserId() {
		return this.acceptUserId;
	}
	
	public void setAcceptUserId(long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	
	public String getAcceptUserAccount() {
		return this.acceptUserAccount;
	}
	
	public void setAcceptUserAccount(String acceptUserAccount) {
		this.acceptUserAccount = acceptUserAccount;
	}
	
	public String getAcceptUserName() {
		return this.acceptUserName;
	}
	
	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}
	
	public long getAcceptUserDeptId() {
		return this.acceptUserDeptId;
	}
	
	public void setAcceptUserDeptId(long acceptUserDeptId) {
		this.acceptUserDeptId = acceptUserDeptId;
	}
	
	public String getAcceptUserDeptName() {
		return this.acceptUserDeptName;
	}
	
	public void setAcceptUserDeptName(String acceptUserDeptName) {
		this.acceptUserDeptName = acceptUserDeptName;
	}
	
	public long getAcceptUserbId() {
		return this.acceptUserbId;
	}
	
	public void setAcceptUserbId(long acceptUserbId) {
		this.acceptUserbId = acceptUserbId;
	}
	
	public String getAcceptUserbAccount() {
		return this.acceptUserbAccount;
	}
	
	public void setAcceptUserbAccount(String acceptUserbAccount) {
		this.acceptUserbAccount = acceptUserbAccount;
	}
	
	public String getAcceptUserbName() {
		return this.acceptUserbName;
	}
	
	public void setAcceptUserbName(String acceptUserbName) {
		this.acceptUserbName = acceptUserbName;
	}
	
	public long getAcceptUserbDeptId() {
		return this.acceptUserbDeptId;
	}
	
	public void setAcceptUserbDeptId(long acceptUserbDeptId) {
		this.acceptUserbDeptId = acceptUserbDeptId;
	}
	
	public String getAcceptUserbDeptName() {
		return this.acceptUserbDeptName;
	}
	
	public void setAcceptUserbDeptName(String acceptUserbDeptName) {
		this.acceptUserbDeptName = acceptUserbDeptName;
	}
	
	public String getTransferFile() {
		return this.transferFile;
	}
	
	public void setTransferFile(String transferFile) {
		this.transferFile = transferFile;
	}
	
	public String getFormChange() {
		return this.formChange;
	}
	
	public void setFormChange(String formChange) {
		this.formChange = formChange;
	}
	
	public String getRiskCouncilSummary() {
		return this.riskCouncilSummary;
	}
	
	public void setRiskCouncilSummary(String riskCouncilSummary) {
		this.riskCouncilSummary = riskCouncilSummary;
	}
	
	public String getProjectApproval() {
		return this.projectApproval;
	}
	
	public void setProjectApproval(String projectApproval) {
		this.projectApproval = projectApproval;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
