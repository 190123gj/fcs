package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

public class FChargeNotificationFeeOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -3913653710452337842L;
	
	private long id;
	
	private long notificationId;
	
	private FeeTypeEnum feeType;
	
	private String chargeBaseStr;
	
	private String chargeAmountStr;
	
	private String chargeRateStr;
	
	private Date startTime;
	
	private String startTimeStr;
	
	private Date endTime;
	
	private String endTimeStr;
	
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
	
	public String getChargeBaseStr() {
		return chargeBaseStr;
	}
	
	public void setChargeBaseStr(String chargeBaseStr) {
		this.chargeBaseStr = chargeBaseStr;
	}
	
	public String getChargeAmountStr() {
		return chargeAmountStr;
	}
	
	public void setChargeAmountStr(String chargeAmountStr) {
		this.chargeAmountStr = chargeAmountStr;
	}
	
	public Money getChargeBase() {
		if (isNull(this.chargeBaseStr)) {
			return new Money(0L);
		}
		return Money.amout(this.chargeBaseStr);
	}
	
	public Money getChargeAmount() {
		if (isNull(this.chargeAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.chargeAmountStr);
	}
	
	public double getChargeRate() {
		if (isNull(this.chargeRateStr)) {
			return new Double(0);
		}
		return Double.parseDouble(this.chargeRateStr);
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
	
	public String getStartTimeStr() {
		return startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public String getEndTimeStr() {
		return endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	public String getChargeRateStr() {
		return chargeRateStr;
	}
	
	public void setChargeRateStr(String chargeRateStr) {
		this.chargeRateStr = chargeRateStr;
	}
}
