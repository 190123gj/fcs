/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目赎回列表DO
 *
 * @author wuzj
 */
public class FinancialProjectRedeemFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -445230413837390987L;
	
	private long applyId;
	
	private double buyNum;
	
	private double holdNum;
	
	private double actualHoldNum;
	
	private String projectCode;
	
	private Money redeemPrice = new Money(0, 0);
	
	private double transferingNum;
	
	private double redeemingNum;
	
	private double redeemNum;
	
	private Date redeemTime;
	
	private Money redeemPrincipal = new Money(0, 0);
	
	private Money redeemInterest = new Money(0, 0);
	
	private String redeemReason;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private String productName;
	
	private String sortCol;
	
	private String sortOrder;
	
	private long limitStart;
	
	private long pageSize;
	
	private Date applyTimeStart;
	
	private Date applyTimeEnd;
	
	private Date redeemTimeStart;
	
	private Date redeemTimeEnd;
	
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	private List<String> formStatusList;
	
	private long createUserId;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
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
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Money getRedeemPrice() {
		return this.redeemPrice;
	}
	
	public void setRedeemPrice(Money redeemPrice) {
		this.redeemPrice = redeemPrice;
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
	
	public Date getApplyTimeStart() {
		return this.applyTimeStart;
	}
	
	public void setApplyTimeStart(Date applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	
	public Date getApplyTimeEnd() {
		return this.applyTimeEnd;
	}
	
	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public Date getRedeemTimeStart() {
		return this.redeemTimeStart;
	}
	
	public void setRedeemTimeStart(Date redeemTimeStart) {
		this.redeemTimeStart = redeemTimeStart;
	}
	
	public Date getRedeemTimeEnd() {
		return this.redeemTimeEnd;
	}
	
	public void setRedeemTimeEnd(Date redeemTimeEnd) {
		this.redeemTimeEnd = redeemTimeEnd;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
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
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
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
