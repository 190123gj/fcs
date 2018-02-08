/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.financialproject;

// auto generated imports
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目赎回列表Info
 *
 * @author wuzj
 */
public class FinancialProjectRedeemFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -4299437608535277423L;
	
	/** 申请ID */
	private long applyId;
	/** 项目编号 */
	private String projectCode;
	/** 买入份额 */
	private double buyNum;
	/** 持有份额 */
	private double holdNum;
	/** 申请时可转让数 */
	private double actualHoldNum;
	/** 当前转让中份额 */
	private double transferingNum;
	/** 当前赎回中份额 */
	private double redeemingNum;
	/** 赎回单价 */
	private Money redeemPrice = new Money(0, 0);
	/** 赎回份额 */
	private double redeemNum;
	/** 赎回时间 */
	private Date redeemTime;
	/** 赎回本金 */
	private Money redeemPrincipal = new Money(0, 0);
	/** 赎回利息 */
	private Money redeemInterest = new Money(0, 0);
	/** 赎回原因 */
	private String redeemReason;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	//赎回信息
	ProjectFinancialTradeRedeemInfo trade;
	
	//项目信息
	private ProjectFinancialInfo project;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public double getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(double buyNum) {
		this.buyNum = buyNum;
	}
	
	public double getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(double holdNum) {
		this.holdNum = holdNum;
	}
	
	public double getActualHoldNum() {
		return this.actualHoldNum;
	}
	
	public void setActualHoldNum(double actualHoldNum) {
		this.actualHoldNum = actualHoldNum;
	}
	
	public double getTransferingNum() {
		return this.transferingNum;
	}
	
	public void setTransferingNum(double transferingNum) {
		this.transferingNum = transferingNum;
	}
	
	public double getRedeemingNum() {
		return this.redeemingNum;
	}
	
	public void setRedeemingNum(double redeemingNum) {
		this.redeemingNum = redeemingNum;
	}
	
	public Money getRedeemPrice() {
		return this.redeemPrice;
	}
	
	public void setRedeemPrice(Money redeemPrice) {
		this.redeemPrice = redeemPrice;
	}
	
	public double getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(double redeemNum) {
		this.redeemNum = redeemNum;
	}
	
	public Date getRedeemTime() {
		return this.redeemTime;
	}
	
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
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
	
	public String getRedeemReason() {
		return this.redeemReason;
	}
	
	public void setRedeemReason(String redeemReason) {
		this.redeemReason = redeemReason;
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
	
	public ProjectFinancialTradeRedeemInfo getTrade() {
		return this.trade;
	}
	
	public void setTrade(ProjectFinancialTradeRedeemInfo trade) {
		this.trade = trade;
	}
	
	public ProjectFinancialInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectFinancialInfo project) {
		this.project = project;
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
