/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:25:33 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ForecastAccountQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -8437241458354871699L;

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
	
	/*** 预计发生时间开始 */
	private Date forecastTimeStart;
	
	/*** 预计发生时间结束 */
	private Date forecastTimeEnd;
	
	/*** 发生事由 */
	private String forecastMemo;
	
	/*** 金额 */
	private Money amount = new Money(0, 0);
	
	/*** 最近更新时间 */
	private Date lastUpdateTime;
	
	/** 更新来源 */
	private String updateFrom;
	
	private String projectCode;
	
	private String customerName;
	
	/** 相关人员类型 必须配合 loginUserId 使用 */
	private List<ProjectRelatedUserTypeEnum> relatedRoleList;
	
	public SystemEnum getSystemForm() {
		return this.systemForm;
	}
	
	public void setSystemForm(SystemEnum systemForm) {
		this.systemForm = systemForm;
	}
	
	public FundDirectionEnum getFundDirection() {
		return this.fundDirection;
	}
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Date getForecastTimeStart() {
		return this.forecastTimeStart;
	}
	
	public void setForecastTimeStart(Date forecastTimeStart) {
		this.forecastTimeStart = forecastTimeStart;
	}
	
	public Date getForecastTimeEnd() {
		return this.forecastTimeEnd;
	}
	
	public void setForecastTimeEnd(Date forecastTimeEnd) {
		this.forecastTimeEnd = forecastTimeEnd;
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
	
	public List<ProjectRelatedUserTypeEnum> getRelatedRoleList() {
		return relatedRoleList;
	}
	
	public void setRelatedRoleList(List<ProjectRelatedUserTypeEnum> relatedRoleList) {
		this.relatedRoleList = relatedRoleList;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", fundDirection=");
		builder.append(fundDirection);
		builder.append(", usedDeptId=");
		builder.append(usedDeptId);
		builder.append(", usedDeptName=");
		builder.append(usedDeptName);
		builder.append(", forecastType=");
		builder.append(forecastType);
		builder.append(", forecastTimeStart=");
		builder.append(forecastTimeStart);
		builder.append(", forecastTimeEnd=");
		builder.append(forecastTimeEnd);
		builder.append(", forecastMemo=");
		builder.append(forecastMemo);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", lastUpdateTime=");
		builder.append(lastUpdateTime);
		builder.append(", updateFrom=");
		builder.append(updateFrom);
		builder.append(", loginUserId=");
		builder.append(getLoginUserId());
		builder.append(", deptIdList=");
		builder.append(getDeptIdList());
		builder.append(", relatedRoleList=");
		builder.append(relatedRoleList);
		builder.append("]");
		return builder.toString();
	}
}
