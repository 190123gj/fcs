package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 赎回信息维护
 *
 * @author wuzj
 */
public class ProjectFinancialTradeRedeemOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -1492347710746308113L;
	//交易ID
	private long tradeId;
	//流水号
	private String flowNo;
	//项目编号
	private String projectCode;
	//申请单表单ID
	private long applyId;
	//赎回单价
	private Money redeemPrice = new Money(0, 0);
	//赎回数量
	private long redeemNum;
	//赎回本金
	private Money redeemPrincipal = new Money(0, 0);
	//赎回利息
	private Money redeemInterest = new Money(0, 0);
	//实际利率
	private double redeemInterestRate;
	//赎回时间
	private Date redeemTime;
	//赎回原因
	private String redeemReason;
	//附件
	private String attach;
	
	public long getTradeId() {
		return this.tradeId;
	}
	
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getFlowNo() {
		return this.flowNo;
	}
	
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public Money getRedeemPrice() {
		return this.redeemPrice;
	}
	
	public void setRedeemPrice(Money redeemPrice) {
		this.redeemPrice = redeemPrice;
	}
	
	public long getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(long redeemNum) {
		this.redeemNum = redeemNum;
	}
	
	public Money getRedeemPrincipal() {
		return this.redeemPrincipal;
	}
	
	public void setRedeemPrincipal(Money redeemPrincipal) {
		this.redeemPrincipal = redeemPrincipal;
	}
	
	public Money getRedeemInterest() {
		return this.redeemInterest;
	}
	
	public void setRedeemInterest(Money redeemInterest) {
		this.redeemInterest = redeemInterest;
	}
	
	public double getRedeemInterestRate() {
		return this.redeemInterestRate;
	}
	
	public void setRedeemInterestRate(double redeemInterestRate) {
		this.redeemInterestRate = redeemInterestRate;
	}
	
	public Date getRedeemTime() {
		return this.redeemTime;
	}
	
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
	}
	
	public String getRedeemReason() {
		return this.redeemReason;
	}
	
	public void setRedeemReason(String redeemReason) {
		this.redeemReason = redeemReason;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
}
