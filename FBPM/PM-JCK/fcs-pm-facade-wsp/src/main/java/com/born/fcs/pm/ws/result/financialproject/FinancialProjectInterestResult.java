package com.born.fcs.pm.ws.result.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目收益结果
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectInterestResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 9164926772083982823L;
	
	//项目编号
	private String projectCode;
	
	//项目名称
	private String projectName;
	
	//产品ID
	private long productId;
	
	//产品名称
	private String productName;
	
	//购买时间
	private Date buyDate;
	
	//计算截止日期
	private Date caculateDate;
	
	//持有期收益
	private Money holdingPeriodInterest = Money.zero();
	
	//计提收益
	private Money withdrawingInterest = Money.zero();
	
	//已计提收益
	private Money withdrawedInterest = Money.zero();
	
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
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Money getHoldingPeriodInterest() {
		return this.holdingPeriodInterest;
	}
	
	public void setHoldingPeriodInterest(Money holdingPeriodInterest) {
		this.holdingPeriodInterest = holdingPeriodInterest;
	}
	
	public Money getWithdrawingInterest() {
		return this.withdrawingInterest;
	}
	
	public void setWithdrawingInterest(Money withdrawingInterest) {
		this.withdrawingInterest = withdrawingInterest;
	}
	
	public Date getBuyDate() {
		return this.buyDate;
	}
	
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	public Date getCaculateDate() {
		return this.caculateDate;
	}
	
	public void setCaculateDate(Date caculateDate) {
		this.caculateDate = caculateDate;
	}
	
	public Money getWithdrawedInterest() {
		return this.withdrawedInterest;
	}
	
	public void setWithdrawedInterest(Money withdrawedInterest) {
		this.withdrawedInterest = withdrawedInterest;
	}
}
