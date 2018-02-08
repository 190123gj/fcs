package com.born.fcs.pm.ws.info.projectcreditcondition;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目授信条件 - 保证金落实情况
 *
 * @author Ji
 *
 */
public class ProjectCreditMarginInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7517350306932174086L;
	
	private long id;
	
	private long confirmId;
	
	private String creditId;
	
	private String projectCode;
	
	private Money marginAmount = new Money(0, 0);
	
	private String accountName;
	
	private String bank;
	
	private String account;
	
	private int period;
	
	private TimeUnitEnum periodUnit;
	
	private Date inTime;
	
	private BooleanEnum isPledge;
	
	private BooleanEnum isFrozen;
	
	private Date rawAddTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(long confirmId) {
		this.confirmId = confirmId;
	}
	
	public String getCreditId() {
		return creditId;
	}
	
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Money getMarginAmount() {
		return marginAmount;
	}
	
	public void setMarginAmount(Money marginAmount) {
		this.marginAmount = marginAmount;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public TimeUnitEnum getPeriodUnit() {
		return periodUnit;
	}
	
	public void setPeriodUnit(TimeUnitEnum periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	public Date getInTime() {
		return inTime;
	}
	
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	
	public BooleanEnum getIsPledge() {
		return isPledge;
	}
	
	public void setIsPledge(BooleanEnum isPledge) {
		this.isPledge = isPledge;
	}
	
	public BooleanEnum getIsFrozen() {
		return isFrozen;
	}
	
	public void setIsFrozen(BooleanEnum isFrozen) {
		this.isFrozen = isFrozen;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
