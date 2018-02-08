/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 手写sql 的付款单表单数据
 * @author wuzj
 * 
 */
public class FormInnerLoanFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String billNo;
	
	private String innerLoanType;
	
	private long formId;
	
	private Money loanAmount = new Money(0, 0);
	
	private Date useTime;
	
	private Date backTime;
	
	/*** 还款时间 */
	private Date backTimeEnd;
	
	private Date interestTime;
	
	private String formInnerLoanInterestType;
	
	private String interestRate;
	
	private String protocolCode;
	
	private String creditorId;
	
	private String creditorName;
	
	private String loanReason;
	
	private long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getBillNo() {
		return this.billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getInnerLoanType() {
		return this.innerLoanType;
	}
	
	public void setInnerLoanType(String innerLoanType) {
		this.innerLoanType = innerLoanType;
	}
	
	public Money getLoanAmount() {
		return this.loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public Date getUseTime() {
		return this.useTime;
	}
	
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	
	public Date getBackTimeEnd() {
		return this.backTimeEnd;
	}
	
	public void setBackTimeEnd(Date backTimeEnd) {
		this.backTimeEnd = backTimeEnd;
	}
	
	public Date getBackTime() {
		return this.backTime;
	}
	
	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}
	
	public Date getInterestTime() {
		return this.interestTime;
	}
	
	public void setInterestTime(Date interestTime) {
		this.interestTime = interestTime;
	}
	
	public String getFormInnerLoanInterestType() {
		return this.formInnerLoanInterestType;
	}
	
	public void setFormInnerLoanInterestType(String formInnerLoanInterestType) {
		this.formInnerLoanInterestType = formInnerLoanInterestType;
	}
	
	public String getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getProtocolCode() {
		return this.protocolCode;
	}
	
	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}
	
	public String getCreditorId() {
		return this.creditorId;
	}
	
	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}
	
	public String getCreditorName() {
		return this.creditorName;
	}
	
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	
	public String getLoanReason() {
		return this.loanReason;
	}
	
	public void setLoanReason(String loanReason) {
		this.loanReason = loanReason;
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
	
	public long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public String getApplyDeptCode() {
		return this.applyDeptCode;
	}
	
	public void setApplyDeptCode(String applyDeptCode) {
		this.applyDeptCode = applyDeptCode;
	}
	
	public String getApplyDeptName() {
		return this.applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
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
