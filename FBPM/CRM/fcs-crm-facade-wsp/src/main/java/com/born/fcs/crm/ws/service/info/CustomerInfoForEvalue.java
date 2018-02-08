package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;

import com.yjf.common.lang.util.money.Money;

/**
 * 评级是修改的客户信息
 * */
public class CustomerInfoForEvalue implements Serializable {
	
	private static final long serialVersionUID = 6346723179330819336L;
	
	private long id;
	
	private String customerId;
	
	private String customerName;
	
	private String loanCardNo;
	
	private Money actualCapital = new Money(0, 0);
	
	private String subordinateRelationship;
	
	private Money salesProceedsLastYear = new Money(0, 0);
	
	private String accountNo;
	
	private String isGroup;
	
	private String isListedCompany;
	
	private String changePerson;
	
	private long changePersonId;
	
	private String year;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setActualCapital(Money actualCapital) {
		this.actualCapital = actualCapital;
	}
	
	public void setSalesProceedsLastYear(Money salesProceedsLastYear) {
		this.salesProceedsLastYear = salesProceedsLastYear;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getLoanCardNo() {
		return this.loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	
	public String getSubordinateRelationship() {
		return this.subordinateRelationship;
	}
	
	public void setSubordinateRelationship(String subordinateRelationship) {
		this.subordinateRelationship = subordinateRelationship;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getIsGroup() {
		return this.isGroup;
	}
	
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	
	public String getIsListedCompany() {
		return this.isListedCompany;
	}
	
	public void setIsListedCompany(String isListedCompany) {
		this.isListedCompany = isListedCompany;
	}
	
	public String getChangePerson() {
		return this.changePerson;
	}
	
	public void setChangePerson(String changePerson) {
		this.changePerson = changePerson;
	}
	
	public long getChangePersonId() {
		return this.changePersonId;
	}
	
	public void setChangePersonId(long changePersonId) {
		this.changePersonId = changePersonId;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public Money getActualCapital() {
		return this.actualCapital;
	}
	
	public Money getSalesProceedsLastYear() {
		return this.salesProceedsLastYear;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerInfoForEvalue [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", loanCardNo=");
		builder.append(loanCardNo);
		builder.append(", actualCapital=");
		builder.append(actualCapital);
		builder.append(", subordinateRelationship=");
		builder.append(subordinateRelationship);
		builder.append(", salesProceedsLastYear=");
		builder.append(salesProceedsLastYear);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", isGroup=");
		builder.append(isGroup);
		builder.append(", isListedCompany=");
		builder.append(isListedCompany);
		builder.append(", changePerson=");
		builder.append(changePerson);
		builder.append(", changePersonId=");
		builder.append(changePersonId);
		builder.append(", year=");
		builder.append(year);
		builder.append("]");
		return builder.toString();
	}
	
}
