package com.born.fcs.pm.ws.order.setup;

import java.util.Date;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目立项 - 银行贷款Order
 *
 * @author wuzj
 *
 */
public class FProjectBankLoanOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = 2102299957896809135L;
	
	private Long id;
	
	private String fundingSide;
	
	private String channelSide;
	
	private Money loanAmount = Money.zero();
	
	private String loanAmountStr;
	
	private Money loanBalance = Money.zero();
	
	private String loanBalanceStr;
	
	private Date loanStartTime;
	
	private Date loanEndTime;
	
	private String loanStartTimeStr;
	
	private String loanEndTimeStr;
	
	private String guaranteeWay;
	
	private Integer sortOrder;
	
	public boolean isNull() {
		return isNull(fundingSide) && isNull(channelSide) && isNull(loanAmountStr)
				&& isNull(loanBalanceStr) && isNull(loanStartTimeStr) && isNull(loanEndTimeStr)
				&& isNull(guaranteeWay);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
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
		if (StringUtil.isNotBlank(loanAmountStr))
			return Money.amout(loanAmountStr);
		return this.loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public String getLoanAmountStr() {
		return this.loanAmountStr;
	}
	
	public void setLoanAmountStr(String loanAmountStr) {
		this.loanAmountStr = loanAmountStr;
	}
	
	public Money getLoanBalance() {
		if (StringUtil.isNotBlank(loanBalanceStr))
			return Money.amout(loanBalanceStr);
		return this.loanBalance;
	}
	
	public void setLoanBalance(Money loanBalance) {
		this.loanBalance = loanBalance;
	}
	
	public String getLoanBalanceStr() {
		return this.loanBalanceStr;
	}
	
	public void setLoanBalanceStr(String loanBalanceStr) {
		this.loanBalanceStr = loanBalanceStr;
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
	
	public String getLoanStartTimeStr() {
		return this.loanStartTimeStr;
	}
	
	public void setLoanStartTimeStr(String loanStartTimeStr) {
		this.loanStartTimeStr = loanStartTimeStr;
	}
	
	public String getLoanEndTimeStr() {
		return this.loanEndTimeStr;
	}
	
	public void setLoanEndTimeStr(String loanEndTimeStr) {
		this.loanEndTimeStr = loanEndTimeStr;
	}
	
	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}
	
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}
	
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
