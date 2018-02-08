package com.born.fcs.pm.ws.order.fund;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

public class FChargeNotificationFeeQueryOrder extends FcsQueryPageBase {
	
	private static final long serialVersionUID = -9085037307882566874L;
	
	private long id;
	
	private long notificationId;
	
	private String type;
	
	private String feeType;
	
	private String feeTypeDesc;
	
	private String chargeBase;
	
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
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeTypeDesc() {
		return this.feeTypeDesc;
	}
	
	public void setFeeTypeDesc(String feeTypeDesc) {
		this.feeTypeDesc = feeTypeDesc;
	}
	
	public String getChargeBase() {
		return this.chargeBase;
	}
	
	public void setChargeBase(String chargeBase) {
		this.chargeBase = chargeBase;
	}
	
}
