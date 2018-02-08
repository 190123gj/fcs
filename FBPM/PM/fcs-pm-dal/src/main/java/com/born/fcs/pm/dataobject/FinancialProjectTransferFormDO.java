/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目转让列表DO
 *
 * @author wuzj
 */
public class FinancialProjectTransferFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = 8923304347083019426L;
	
	private long applyId;
	
	private String projectCode;
	
	private double buyNum;
	
	private double holdNum;
	
	private double actualHoldNum;
	
	private double transferingNum;
	
	private double redeemingNum;
	
	private Money transferPrice = new Money(0, 0);
	
	private double transferNum;
	
	private String transferReason;
	
	private Date transferTime;
	
	private Money transferInterest = new Money(0, 0);
	
	private String transferTo;
	
	private double interestRate;
	
	private String isTransferOwnership;
	
	private String isBuyBack;
	
	private Money buyBackPrice = new Money(0, 0);
	
	private Date buyBackTime;
	
	private String councilType;
	
	private long councilApplyId;
	
	private String councilStatus;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private long createUserId;
	
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	private List<String> formStatusList;
	
	private Date transferTimeStart;
	
	private Date transferTimeEnd;
	
	private String productName;
	
	private String sortCol;
	
	private String sortOrder;
	
	private long limitStart;
	
	private long pageSize;
	
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
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
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
	
	public String getIsTransferOwnership() {
		return this.isTransferOwnership;
	}
	
	public void setIsTransferOwnership(String isTransferOwnership) {
		this.isTransferOwnership = isTransferOwnership;
	}
	
	public String getIsBuyBack() {
		return this.isBuyBack;
	}
	
	public void setIsBuyBack(String isBuyBack) {
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
	
	public String getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}
	
	public long getCouncilApplyId() {
		return this.councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public String getCouncilStatus() {
		return this.councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
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
