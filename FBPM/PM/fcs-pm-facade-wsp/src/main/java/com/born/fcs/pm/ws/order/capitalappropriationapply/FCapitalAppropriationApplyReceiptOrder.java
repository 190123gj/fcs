package com.born.fcs.pm.ws.order.capitalappropriationapply;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请Order-回执单信息
 *
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyReceiptOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7631309613833517038L;
	
	private Long id;
	
	private long formId;
	
	private Date strokeTime;
	
	private String payeeAccountName;
	
	private String bankAccount;
	
	private String payeeAccount;
	
	private Money paymentAmount = new Money(0, 0);
	
	private String attach;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	
}
