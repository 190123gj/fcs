/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

// auto generated imports

/**
 * 表单项目信息
 *
 * @author wuzj
 */
public class ProjectChargePayDO implements Serializable {
	
	private static final long serialVersionUID = 1664580240998456540L;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String affirmFormType;
	
	private String feeType;
	
	private Money payAmount = new Money(0, 0);
	
	private Money returnCustomerAmount = new Money(0, 0);
	
	private Date payTime;
	
	private String payeeAccountName;
	
	private String depositAccount;
	
	private double marginRate;
	
	private Date depositTime;
	
	private int period;
	
	private String periodUnit;
	
	private long payId;
	private long detailId;
	
	public long getPayId() {
		return payId;
	}
	
	public void setPayId(long payId) {
		this.payId = payId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getAffirmFormType() {
		return this.affirmFormType;
	}
	
	public void setAffirmFormType(String affirmFormType) {
		this.affirmFormType = affirmFormType;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public Money getPayAmount() {
		return this.payAmount;
	}
	
	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getPayeeAccountName() {
		return this.payeeAccountName;
	}
	
	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public double getMarginRate() {
		return this.marginRate;
	}
	
	public void setMarginRate(double marginRate) {
		this.marginRate = marginRate;
	}
	
	public Date getDepositTime() {
		return this.depositTime;
	}
	
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	
	public int getPeriod() {
		return this.period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public String getPeriodUnit() {
		return this.periodUnit;
	}
	
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	public Money getReturnCustomerAmount() {
		return returnCustomerAmount;
	}
	
	public void setReturnCustomerAmount(Money returnCustomerAmount) {
		this.returnCustomerAmount = returnCustomerAmount;
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
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
