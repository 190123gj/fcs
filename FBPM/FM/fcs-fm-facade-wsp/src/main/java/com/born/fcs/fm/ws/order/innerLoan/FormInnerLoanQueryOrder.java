/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:32:23 创建
 */
package com.born.fcs.fm.ws.order.innerLoan;

import java.util.Date;

import com.born.fcs.fm.ws.enums.FormInnerLoanInterestTypeEnum;
import com.born.fcs.fm.ws.enums.FormInnerLoanTypeEnum;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FormInnerLoanQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = 1L;
	
	private FormInnerLoanTypeEnum innerLoanType;
	
	private String billNo;
	
	public String getBillNo() {
		return this.billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	/*** 借款金额 */
	private Money loanAmount = new Money(0, 0);
	
	/*** 用款时间 */
	private Date useTime;
	
	/*** 还款时间 */
	private Date backTime;
	
	/*** 还款时间 */
	private Date backTimeEnd;
	
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
	
	private Long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	/** 表单状态 */
	private FormStatusEnum formStatus;
	
	public Money getLoanAmount() {
		return this.loanAmount;
	}
	
	public Long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public FormStatusEnum getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public void setApplyDeptId(Long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public Date getBackTimeEnd() {
		return this.backTimeEnd;
	}
	
	public FormInnerLoanTypeEnum getInnerLoanType() {
		return this.innerLoanType;
	}
	
	public void setInnerLoanType(FormInnerLoanTypeEnum innerLoanType) {
		this.innerLoanType = innerLoanType;
	}
	
	public void setBackTimeEnd(Date backTimeEnd) {
		this.backTimeEnd = backTimeEnd;
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
	
}
