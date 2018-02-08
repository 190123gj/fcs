/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
// auto generated imports
import com.yjf.common.lang.util.money.Money;

/**
 * 保后项目汇总 在保余额
 * @author Administrator
 *
 */
public class AfterwardsSummaryReleasingAmountDO implements Serializable {
	
	private static final long serialVersionUID = -1975469370540814851L;
	
	private String customerName;
	
	private Money loanedAmount = new Money(0, 0);
	
	private Money applyAmount = new Money(0, 0);
	
	private Date finishTime;
	
	private String busiType;
	/** 业务类型 */
	List<String> busiTypeList = Lists.newArrayList();
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Money getLoanedAmount() {
		return loanedAmount;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public Money getApplyAmount() {
		return applyAmount;
	}
	
	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public List<String> getBusiTypeList() {
		return busiTypeList;
	}
	
	public void setBusiTypeList(List<String> busiTypeList) {
		this.busiTypeList = busiTypeList;
	}
	
}
