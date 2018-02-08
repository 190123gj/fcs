/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:11:03 创建
 */
package com.born.fcs.fm.ws.info.innerLoan;

import java.util.Calendar;
import java.util.Date;

import com.born.fcs.fm.ws.enums.FormInnerLoanInterestTypeEnum;
import com.born.fcs.fm.ws.enums.FormInnerLoanTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FormInnerLoanInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String billNo;
	
	public String getBillNo() {
		return this.billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	private FormInnerLoanTypeEnum innerLoanType;
	
	private long formId;
	
	/*** 借款金额 */
	private Money loanAmount = new Money(0, 0);
	
	/*** 用款时间 */
	private Date useTime;
	
	/*** 还款时间 */
	private Date backTime;
	
	/*** 付息时间 */
	private Date interestTime;
	
	/*** 付息方式 */
	private FormInnerLoanInterestTypeEnum formInnerLoanInterestType;
	
	/*** 利率 */
	private String interestRate;
	
	/*** 协议编码 */
	private String protocolCode;
	
	/*** 债权人id */
	private String creditorId;
	
	/*** 债权人 */
	private String creditorName;
	
	/*** 借款用途 */
	private String loanReason;
	
	private long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	/// 计算利息
	
	/** 金额*费率/365*（还款时间-用款时间） */
	private Money interest;
	
	public Money getInterest() {
		if (this.interest == null) {
			
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(useTime);
				long time1 = cal.getTimeInMillis();
				cal.setTime(backTime);
				long time2 = cal.getTimeInMillis();
				long between_days = (time2 - time1) / (1000 * 3600 * 24);
				interest = loanAmount.multiply(Double.valueOf(interestRate)).multiply(between_days)
					.divide(36500);
			} catch (Exception e) {
				interest = new Money(-1);
			}
		}
		return this.interest;
	}
	
	public void setInterest(Money interest) {
		this.interest = interest;
	}
	
	public FormInnerLoanTypeEnum getInnerLoanType() {
		return this.innerLoanType;
	}
	
	public void setInnerLoanType(FormInnerLoanTypeEnum innerLoanType) {
		this.innerLoanType = innerLoanType;
	}
	
	public long getId() {
		return this.id;
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
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public void setId(long id) {
		this.id = id;
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
	
	public FormInnerLoanInterestTypeEnum getFormInnerLoanInterestType() {
		return this.formInnerLoanInterestType;
	}
	
	public void setFormInnerLoanInterestType(FormInnerLoanInterestTypeEnum formInnerLoanInterestType) {
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
	
}
