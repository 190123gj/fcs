/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:17:10 创建
 */
package com.born.fcs.fm.ws.result.forecast;

import java.util.List;

import com.born.fcs.fm.ws.info.forecast.AccountAmountDetailInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 首页数据
 * @author hjiajie
 * 
 */
public class AccountAmountDetailMainIndexResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private List<AccountAmountDetailInfo> dayDetail;
	
	private List<AccountAmountDetailInfo> weekDetail;
	
	private List<AccountAmountDetailInfo> monthDetail;
	
	private List<AccountAmountDetailInfo> fourmohthDetail;
	
	///  主要资金构成情况信息
	/** 资金总额 */
	private Money totalamount;
	
	/*** 可用资金总计【银行账户余额加总】 */
	private Money bankAccountAmount;
	
	/** 理财产品：持有的理财产品总额 */
	private Money financialAmount;
	
	/** 股权投资 */
	private Money equityInvestmentAmount;
	
	/** 应收代偿款：资金划付申请单审核通过的代偿本金额代偿利息加总 */
	private Money compensatoryAmount;
	
	/*** 存出保证金 */
	private Money refundableDepositsAmount;
	
	/*** 委托贷款 */
	private Money entrustLoanAmount;
	
	public Money getTotalamount() {
		return this.totalamount;
	}
	
	public void setTotalamount(Money totalamount) {
		this.totalamount = totalamount;
	}
	
	public Money getBankAccountAmount() {
		return this.bankAccountAmount;
	}
	
	public void setBankAccountAmount(Money bankAccountAmount) {
		this.bankAccountAmount = bankAccountAmount;
	}
	
	public Money getFinancialAmount() {
		return this.financialAmount;
	}
	
	public void setFinancialAmount(Money financialAmount) {
		this.financialAmount = financialAmount;
	}
	
	public Money getEquityInvestmentAmount() {
		return this.equityInvestmentAmount;
	}
	
	public void setEquityInvestmentAmount(Money equityInvestmentAmount) {
		this.equityInvestmentAmount = equityInvestmentAmount;
	}
	
	public Money getCompensatoryAmount() {
		return this.compensatoryAmount;
	}
	
	public void setCompensatoryAmount(Money compensatoryAmount) {
		this.compensatoryAmount = compensatoryAmount;
	}
	
	public Money getRefundableDepositsAmount() {
		return this.refundableDepositsAmount;
	}
	
	public void setRefundableDepositsAmount(Money refundableDepositsAmount) {
		this.refundableDepositsAmount = refundableDepositsAmount;
	}
	
	public Money getEntrustLoanAmount() {
		return this.entrustLoanAmount;
	}
	
	public void setEntrustLoanAmount(Money entrustLoanAmount) {
		this.entrustLoanAmount = entrustLoanAmount;
	}
	
	public List<AccountAmountDetailInfo> getDayDetail() {
		return this.dayDetail;
	}
	
	public void setDayDetail(List<AccountAmountDetailInfo> dayDetail) {
		this.dayDetail = dayDetail;
	}
	
	public List<AccountAmountDetailInfo> getMonthDetail() {
		return this.monthDetail;
	}
	
	public void setMonthDetail(List<AccountAmountDetailInfo> monthDetail) {
		this.monthDetail = monthDetail;
	}
	
	public List<AccountAmountDetailInfo> getFourmohthDetail() {
		return this.fourmohthDetail;
	}
	
	public void setFourmohthDetail(List<AccountAmountDetailInfo> fourmohthDetail) {
		this.fourmohthDetail = fourmohthDetail;
	}
	
	public List<AccountAmountDetailInfo> getWeekDetail() {
		return this.weekDetail;
	}
	
	public void setWeekDetail(List<AccountAmountDetailInfo> weekDetail) {
		this.weekDetail = weekDetail;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountAmountDetailMainIndexResult [dayDetail=");
		builder.append(dayDetail);
		builder.append(", monthDetail=");
		builder.append(monthDetail);
		builder.append(", weekDetail=");
		builder.append(weekDetail);
		builder.append(", fourmohthDetail=");
		builder.append(fourmohthDetail);
		builder.append("]");
		return builder.toString();
	}
	
}
