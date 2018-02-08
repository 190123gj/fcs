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
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FormInnerLoanOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private FormInnerLoanTypeEnum innerLoanType;
	
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
	
	private Long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private Long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	
	public Money getLoanAmount() {
		return this.loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public Date getUseTime() {
		return this.useTime;
	}
	
	public FormInnerLoanTypeEnum getInnerLoanType() {
		return this.innerLoanType;
	}
	
	public void setInnerLoanType(FormInnerLoanTypeEnum innerLoanType) {
		this.innerLoanType = innerLoanType;
	}
	
	public Long getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(Long applyUserId) {
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
	
	public Long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(Long applyDeptId) {
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
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
