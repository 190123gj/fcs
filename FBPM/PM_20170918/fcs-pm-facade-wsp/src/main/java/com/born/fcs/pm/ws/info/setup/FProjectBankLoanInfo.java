package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 银行贷款情况
 *
 * @author wuzj
 *
 */
public class FProjectBankLoanInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 3368653397968066172L;

	private long id;
	
	private String fundingSide;
	
	private String channelSide;
	
	private Money loanAmount = new Money(0, 0);
	
	private Money loanBalance = new Money(0, 0);
	
	private Date loanStartTime;
	
	private Date loanEndTime;
	
	private String guaranteeWay;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFundingSide() {
		return this.fundingSide;
	}

	public void setFundingSide(String fundingSide) {
		this.fundingSide = fundingSide;
	}

	public String getChannelSide() {
		return this.channelSide;
	}

	public void setChannelSide(String channelSide) {
		this.channelSide = channelSide;
	}

	public Money getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Money getLoanBalance() {
		return this.loanBalance;
	}

	public void setLoanBalance(Money loanBalance) {
		this.loanBalance = loanBalance;
	}

	public Date getLoanStartTime() {
		return this.loanStartTime;
	}

	public void setLoanStartTime(Date loanStartTime) {
		this.loanStartTime = loanStartTime;
	}

	public Date getLoanEndTime() {
		return this.loanEndTime;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}

	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRawAddTime() {
		return this.rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
