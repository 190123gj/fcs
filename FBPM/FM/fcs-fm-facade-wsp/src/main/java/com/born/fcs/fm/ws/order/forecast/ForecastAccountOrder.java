/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:44:10 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import java.util.Date;

import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 预测表
 * @author hjiajie
 * 
 */
public class ForecastAccountOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 1L;
	
	/** 来源系统 */
	private SystemEnum systemForm;
	
	/** 来源系统唯一标识 */
	private String orderNo;
	
	/** 资金流向 */
	private FundDirectionEnum fundDirection;
	
	/** 用款部门id */
	private String usedDeptId;
	
	/** 用款部门name */
	private String usedDeptName;
	
	/** 预测类型 */
	private ForecastTypeEnum forecastType;
	
	/** 子类型1 [中长期，短期] */
	private ForecastChildTypeOneEnum forecastChildTypeOne;
	
	/** 子类型2 [债权,信托 deng小类型] */
	private ForecastChildTypeTwoEnum forecastChildTypeTwo;
	
	/** 费用类型 */
	private ForecastFeeTypeEnum feeType;
	
	/** 关联项目编号 */
	private String projectCode;
	/** 关联客户ID（理财产品ID） */
	private long customerId;
	/** 关联客户名称（理财产品名称） */
	private String customerName;
	
	/*** 预计发生时间 */
	private Date forecastStartTime;
	
	/*** 发生事由 */
	private String forecastMemo;
	
	/*** 金额 */
	private Money amount = new Money(0, 0);
	
	/*** 最近更新时间 */
	private Date lastUpdateTime;
	
	/** 更新来源 */
	private String updateFrom;
	
	@Override
	public void check() {
		validateNotNull(systemForm, "来源系统");
		validateNotNull(orderNo, "orderNo");
		validateNotNull(fundDirection, "资金流向");
		validateNotNull(usedDeptId, "用款部门id");
		validateNotNull(usedDeptName, "用款部门name");
		validateNotNull(forecastType, "预测类型");
		validateNotNull(forecastStartTime, "预计发生时间");
		validateNotNull(feeType, "费用类型");
	}
	
	public SystemEnum getSystemForm() {
		return this.systemForm;
	}
	
	public void setSystemForm(SystemEnum systemForm) {
		this.systemForm = systemForm;
	}
	
	public ForecastChildTypeOneEnum getForecastChildTypeOne() {
		return this.forecastChildTypeOne;
	}
	
	public void setForecastChildTypeOne(ForecastChildTypeOneEnum forecastChildTypeOne) {
		this.forecastChildTypeOne = forecastChildTypeOne;
	}
	
	public ForecastChildTypeTwoEnum getForecastChildTypeTwo() {
		return this.forecastChildTypeTwo;
	}
	
	public void setForecastChildTypeTwo(ForecastChildTypeTwoEnum forecastChildTypeTwo) {
		this.forecastChildTypeTwo = forecastChildTypeTwo;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public FundDirectionEnum getFundDirection() {
		return this.fundDirection;
	}
	
	public void setFundDirection(FundDirectionEnum fundDirection) {
		this.fundDirection = fundDirection;
	}
	
	public String getUsedDeptId() {
		return this.usedDeptId;
	}
	
	public void setUsedDeptId(String usedDeptId) {
		this.usedDeptId = usedDeptId;
	}
	
	public String getUsedDeptName() {
		return this.usedDeptName;
	}
	
	public void setUsedDeptName(String usedDeptName) {
		this.usedDeptName = usedDeptName;
	}
	
	public ForecastTypeEnum getForecastType() {
		return this.forecastType;
	}
	
	public void setForecastType(ForecastTypeEnum forecastType) {
		this.forecastType = forecastType;
	}
	
	public Date getForecastStartTime() {
		return this.forecastStartTime;
	}
	
	public void setForecastStartTime(Date forecastStartTime) {
		this.forecastStartTime = forecastStartTime;
	}
	
	public String getForecastMemo() {
		return this.forecastMemo;
	}
	
	public void setForecastMemo(String forecastMemo) {
		this.forecastMemo = forecastMemo;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public String getUpdateFrom() {
		return this.updateFrom;
	}
	
	public void setUpdateFrom(String updateFrom) {
		this.updateFrom = updateFrom;
	}
	
	public ForecastFeeTypeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(ForecastFeeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ForecastAccountOrder [systemForm=");
		builder.append(systemForm);
		builder.append(", orderNo=");
		builder.append(orderNo);
		builder.append(", fundDirection=");
		builder.append(fundDirection);
		builder.append(", usedDeptId=");
		builder.append(usedDeptId);
		builder.append(", usedDeptName=");
		builder.append(usedDeptName);
		builder.append(", forecastType=");
		builder.append(forecastType);
		builder.append(", forecastStartTime=");
		builder.append(forecastStartTime);
		builder.append(", forecastMemo=");
		builder.append(forecastMemo);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", lastUpdateTime=");
		builder.append(lastUpdateTime);
		builder.append(", updateFrom=");
		builder.append(updateFrom);
		builder.append("]");
		return builder.toString();
	}
	
}
