package com.born.fcs.pm.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付导出
 * @author ji
 *
 */
public class CapitalExportDO implements Serializable {
	
	private static final long serialVersionUID = -5569141510297458120L;
	
	private String projectCode;
	
	private String projectName;
	
	private String projectType;
	
	private String customerName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);
	
	private String issueInstitution;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private Money price;
	
	private String appropriateReason;
	
	private Money appropriateAmount = new Money(0, 0);
	
	private Money payAmount = new Money(0, 0);
	
	private Date payTime;
	
	private String formUserName;
	
	private String formStatus;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getIssueInstitution() {
		return issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Money getPrice() {
		return price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public String getAppropriateReason() {
		return appropriateReason;
	}
	
	public void setAppropriateReason(String appropriateReason) {
		this.appropriateReason = appropriateReason;
	}
	
	public Money getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Money appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}
	
	public Money getPayAmount() {
		return payAmount;
	}
	
	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	public Date getPayTime() {
		return payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getFormUserName() {
		return formUserName;
	}
	
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
