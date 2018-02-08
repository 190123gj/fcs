package com.born.fcs.pm.ws.info.financeaffirm;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务确认-资金划付和收费通知-明细info
 *
 * @author Ji
 *
 */
public class FFinanceAffirmDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 815482211913294773L;
	
	private long id;
	
	private long affirmId;
	
	private long detailId;
	
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
	
	private String attach;
	
	private Date rawAddTime;
	
	private FeeTypeEnum chargeFeeType;
	
	private PaymentMenthodEnum menthodType;

	private Money useAmount;

	public Money getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(Money useAmount) {
		this.useAmount = useAmount;
	}

	public PaymentMenthodEnum getMenthodType() {
		return menthodType;
	}
	
	public void setMenthodType(PaymentMenthodEnum menthodType) {
		this.menthodType = menthodType;
	}
	
	public FeeTypeEnum getChargeFeeType() {
		return chargeFeeType;
	}
	
	public void setChargeFeeType(FeeTypeEnum chargeFeeType) {
		this.chargeFeeType = chargeFeeType;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getAffirmId() {
		return affirmId;
	}
	
	public void setAffirmId(long affirmId) {
		this.affirmId = affirmId;
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getFeeType() {
		return feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
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
	
	public String getPayeeAccountName() {
		return payeeAccountName;
	}
	
	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}
	
	public String getDepositAccount() {
		return depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public double getMarginRate() {
		return marginRate;
	}
	
	public void setMarginRate(double marginRate) {
		this.marginRate = marginRate;
	}
	
	public Date getDepositTime() {
		return depositTime;
	}
	
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public String getPeriodUnit() {
		return periodUnit;
	}
	
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
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
	
	public Money getReturnCustomerAmount() {
		return returnCustomerAmount;
	}
	
	public void setReturnCustomerAmount(Money returnCustomerAmount) {
		this.returnCustomerAmount = returnCustomerAmount;
	}
	
}
