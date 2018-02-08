/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.ws.info.accountbalance;

import java.util.Date;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 科目余额数据
 * 
 * @author lirz
 *
 * 2016-8-4 下午3:42:23
 */
public class AccountBalanceDataInfo extends BaseToStringInfo{

	private static final long serialVersionUID = -2085141872338926810L;
	
	private long accountBalanceDataId;
	private long accountBalanceId;
	private String code;
	private String name;
	private String currency;
	private Money initialDebitBalance = new Money(0, 0);
	private Money initialCreditBalance = new Money(0, 0);
	private Money currentDebitAmount = new Money(0, 0);
	private Money currentCreditAmount = new Money(0, 0);
	private Money yearDebitAmount = new Money(0, 0);
	private Money yearCreditAmount = new Money(0, 0);
	private Money endingDebitBalance = new Money(0, 0);
	private Money endingCreditBalance = new Money(0, 0);
	
	private int sortOrder;

	private Date rawAddTime;
	private Date rawUpdateTime;

	public long getAccountBalanceDataId() {
		return accountBalanceDataId;
	}
	
	public void setAccountBalanceDataId(long accountBalanceDataId) {
		this.accountBalanceDataId = accountBalanceDataId;
	}

	public long getAccountBalanceId() {
		return accountBalanceId;
	}
	
	public void setAccountBalanceId(long accountBalanceId) {
		this.accountBalanceId = accountBalanceId;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Money getInitialDebitBalance() {
		return initialDebitBalance;
	}
	
	public void setInitialDebitBalance(Money initialDebitBalance) {
		if (initialDebitBalance == null) {
			this.initialDebitBalance = new Money(0, 0);
		} else {
			this.initialDebitBalance = initialDebitBalance;
		}
	}

	public Money getInitialCreditBalance() {
		return initialCreditBalance;
	}
	
	public void setInitialCreditBalance(Money initialCreditBalance) {
		if (initialCreditBalance == null) {
			this.initialCreditBalance = new Money(0, 0);
		} else {
			this.initialCreditBalance = initialCreditBalance;
		}
	}

	public Money getCurrentDebitAmount() {
		return currentDebitAmount;
	}
	
	public void setCurrentDebitAmount(Money currentDebitAmount) {
		if (currentDebitAmount == null) {
			this.currentDebitAmount = new Money(0, 0);
		} else {
			this.currentDebitAmount = currentDebitAmount;
		}
	}

	public Money getCurrentCreditAmount() {
		return currentCreditAmount;
	}
	
	public void setCurrentCreditAmount(Money currentCreditAmount) {
		if (currentCreditAmount == null) {
			this.currentCreditAmount = new Money(0, 0);
		} else {
			this.currentCreditAmount = currentCreditAmount;
		}
	}

	public Money getYearDebitAmount() {
		return yearDebitAmount;
	}
	
	public void setYearDebitAmount(Money yearDebitAmount) {
		if (yearDebitAmount == null) {
			this.yearDebitAmount = new Money(0, 0);
		} else {
			this.yearDebitAmount = yearDebitAmount;
		}
	}

	public Money getYearCreditAmount() {
		return yearCreditAmount;
	}
	
	public void setYearCreditAmount(Money yearCreditAmount) {
		if (yearCreditAmount == null) {
			this.yearCreditAmount = new Money(0, 0);
		} else {
			this.yearCreditAmount = yearCreditAmount;
		}
	}

	public Money getEndingDebitBalance() {
		return endingDebitBalance;
	}
	
	public void setEndingDebitBalance(Money endingDebitBalance) {
		if (endingDebitBalance == null) {
			this.endingDebitBalance = new Money(0, 0);
		} else {
			this.endingDebitBalance = endingDebitBalance;
		}
	}

	public Money getEndingCreditBalance() {
		return endingCreditBalance;
	}
	
	public void setEndingCreditBalance(Money endingCreditBalance) {
		if (endingCreditBalance == null) {
			this.endingCreditBalance = new Money(0, 0);
		} else {
			this.endingCreditBalance = endingCreditBalance;
		}
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
