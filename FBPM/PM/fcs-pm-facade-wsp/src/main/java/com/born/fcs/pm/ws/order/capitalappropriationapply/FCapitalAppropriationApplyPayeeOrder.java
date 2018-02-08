package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 资金划付申请Order-收款方信息
 *
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyPayeeOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7631309613833517038L;
	
	private long applyId;
	
	private String payeeAccountName;
	
	private String bankAccount;
	
	private String payeeAccount;
	
	private Date plannedTime;
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
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
	
	public Date getPlannedTime() {
		return plannedTime;
	}
	
	public void setPlannedTime(Date plannedTime) {
		this.plannedTime = plannedTime;
	}
	
}
