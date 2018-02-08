package com.born.fcs.pm.ws.info.capitalappropriationapply;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请-回执信息
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyReceiptInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8119245438679941733L;
	
	private long id;
	
	private long formId;
	
	private Date strokeTime;
	
	private String payeeAccountName;
	
	private String bankAccount;
	
	private String payeeAccount;
	
	private Money paymentAmount = new Money(0, 0);
	
	private String attach;
	
	private Date rawAddTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public Date getStrokeTime() {
		return strokeTime;
	}
	
	public void setStrokeTime(Date strokeTime) {
		this.strokeTime = strokeTime;
	}
	
	public String getPayeeAccountName() {
		return payeeAccountName;
	}
	
	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}
	
	public String getBankAccount() {
		return bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getPayeeAccount() {
		return payeeAccount;
	}
	
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	
	public Money getPaymentAmount() {
		return paymentAmount;
	}
	
	public void setPaymentAmount(Money paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
