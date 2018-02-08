/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目转让列表info
 *
 * @author wuzj
 */
public class FinancialProjectTransferFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 7004732449736362609L;
	
	/** 申请ID */
	private long applyId;
	/** 项目编号 */
	private String projectCode;
	/** 买入份额 */
	private double buyNum;
	/** 申请时持有数 */
	private double holdNum;
	/** 申请时可转让数 */
	private double actualHoldNum;
	/** 转让中数量 */
	private double transferingNum;
	/** 赎回数量 */
	private double redeemingNum;
	/** 转让单价 */
	private Money transferPrice = new Money(0, 0);
	/** 转让数量 */
	private double transferNum;
	/** 预计转让时间 */
	private Date transferTime;
	/** 预计转让收益 */
	private Money transferInterest = new Money(0, 0);
	/** 转让对象 */
	private String transferTo;
	/** 融资利率 */
	private double interestRate;
	/** 转让原因 */
	private String transferReason;
	/** 是否过户 */
	private BooleanEnum isTransferOwnership;
	/** 是否回购 */
	private BooleanEnum isBuyBack;
	/** 回购单价 */
	private Money buyBackPrice = new Money(0, 0);
	/** 回购时间 */
	private Date buyBackTime;
	/** 上会申请ID */
	private long councilApplyId;
	/** 上会类型 */
	private ProjectCouncilEnum councilType;
	/** 上会状态 */
	private ProjectCouncilStatusEnum councilStatus;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	//转让信息
	private ProjectFinancialTradeTansferInfo trade;
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
	
	public Money getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(Money transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public double getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(double transferNum) {
		this.transferNum = transferNum;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public Money getTransferInterest() {
		return this.transferInterest;
	}
	
	public void setTransferInterest(Money transferInterest) {
		this.transferInterest = transferInterest;
	}
	
	public String getTransferTo() {
		return this.transferTo;
	}
	
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public BooleanEnum getIsTransferOwnership() {
		return this.isTransferOwnership;
	}
	
	public void setIsTransferOwnership(BooleanEnum isTransferOwnership) {
		this.isTransferOwnership = isTransferOwnership;
	}
	
	public BooleanEnum getIsBuyBack() {
		return this.isBuyBack;
	}
	
	public void setIsBuyBack(BooleanEnum isBuyBack) {
		this.isBuyBack = isBuyBack;
	}
	
	public Money getBuyBackPrice() {
		return this.buyBackPrice;
	}
	
	public void setBuyBackPrice(Money buyBackPrice) {
		this.buyBackPrice = buyBackPrice;
	}
	
	public Date getBuyBackTime() {
		return this.buyBackTime;
	}
	
	public void setBuyBackTime(Date buyBackTime) {
		this.buyBackTime = buyBackTime;
	}
	
	public long getCouncilApplyId() {
		return this.councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public ProjectCouncilEnum getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(ProjectCouncilEnum councilType) {
		this.councilType = councilType;
	}
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return this.councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
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
	
	public ProjectFinancialTradeTansferInfo getTrade() {
		return this.trade;
	}
	
	public void setTrade(ProjectFinancialTradeTansferInfo trade) {
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
