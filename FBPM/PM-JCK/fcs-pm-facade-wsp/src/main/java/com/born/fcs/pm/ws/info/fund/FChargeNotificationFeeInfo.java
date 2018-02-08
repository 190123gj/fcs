package com.born.fcs.pm.ws.info.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FChargeNotificationFeeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -316530758648905251L;
	
	private long id;
	
	private long notificationId;
	
	private FeeTypeEnum feeType;
	
	private Money chargeBase = new Money(0, 0);
	
	private Money chargeAmount = new Money(0, 0);
	
	private double chargeRate;
	
	private Date startTime;
	
	private Date endTime;
	
	private String remark;
	
	private int sortOrder;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public FeeTypeEnum getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(FeeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getChargeBase() {
		return this.chargeBase;
	}
	
	public void setChargeBase(Money chargeBase) {
		this.chargeBase = chargeBase;
	}
	
	public Money getChargeAmount() {
		return this.chargeAmount;
	}
	
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public double getChargeRate() {
		return this.chargeRate;
	}
	
	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
