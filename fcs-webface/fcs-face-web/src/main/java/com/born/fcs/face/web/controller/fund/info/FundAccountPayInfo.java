/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:23:03 创建
 */
package com.born.fcs.face.web.controller.fund.info;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.born.fcs.face.integration.bpm.service.info.WorkflowProcessLog;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FundAccountPayInfo implements Serializable {
	
	private static final long serialVersionUID = 1111516118977341711L;
	
	private FormInfo form;
	
	private String billNo;
	
	private String deptName;
	
	private String agent;
	
	private Date applicationTime;
	
	private Money amount;
	
	private String bank;
	private String voucherNo;
	
	private String payee;
	
	private String bankAccount;
	
	private String expenseType;
	
	private String costType;
	
	private String reason;
	
	private List<WorkflowProcessLog> checkList;
	
	public FormInfo getForm() {
		return this.form;
	}
	
	public void setForm(FormInfo form) {
		this.form = form;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getAgent() {
		return this.agent;
	}
	
	public String getVoucherNo() {
		return this.voucherNo;
	}
	
	public String getBillNo() {
		return this.billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public Date getApplicationTime() {
		return this.applicationTime;
	}
	
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getBank() {
		return this.bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getExpenseType() {
		return this.expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public String getCostType() {
		return this.costType;
	}
	
	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public List<WorkflowProcessLog> getCheckList() {
		return this.checkList;
	}
	
	public void setCheckList(List<WorkflowProcessLog> checkList) {
		this.checkList = checkList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FundAccountPayInfo [form=");
		builder.append(form);
		builder.append(", deptName=");
		builder.append(deptName);
		builder.append(", agent=");
		builder.append(agent);
		builder.append(", applicationTime=");
		builder.append(applicationTime);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", bank=");
		builder.append(bank);
		builder.append(", payee=");
		builder.append(payee);
		builder.append(", bankAccount=");
		builder.append(bankAccount);
		builder.append(", expenseType=");
		builder.append(expenseType);
		builder.append(", costType=");
		builder.append(costType);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", checkList=");
		builder.append(checkList);
		builder.append("]");
		return builder.toString();
	}
	
}
