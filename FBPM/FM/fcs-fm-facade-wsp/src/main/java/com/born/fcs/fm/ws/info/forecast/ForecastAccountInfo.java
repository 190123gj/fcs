/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:06:11 创建
 */
package com.born.fcs.fm.ws.info.forecast;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.ForecastChildTypeOneEnum;
import com.born.fcs.fm.ws.enums.ForecastChildTypeTwoEnum;
import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * @author hjiajie
 */
public class ForecastAccountInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	private long id;
	
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
	
	private ForecastFeeTypeEnum feeType;
	
	private String projectCode;
	
	private long customerId;
	
	private String customerName;
	
	/** 子类型1 [中长期，短期] */
	private ForecastChildTypeOneEnum forecastChildTypeOne;
	
	/** 子类型2 [债权,信托 deng小类型] */
	private ForecastChildTypeTwoEnum forecastChildTypeTwo;
	
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
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	/***
	 * 变化明细
	 */
	List<ForecastAccountChangeDetailInfo> changeDetail;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public SystemEnum getSystemForm() {
		return this.systemForm;
	}
	
	public void setSystemForm(SystemEnum systemForm) {
		this.systemForm = systemForm;
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
	
	public List<ForecastAccountChangeDetailInfo> getChangeDetail() {
		return changeDetail;
	}
	
	public void setChangeDetail(List<ForecastAccountChangeDetailInfo> changeDetail) {
		this.changeDetail = changeDetail;
	}
	
}
