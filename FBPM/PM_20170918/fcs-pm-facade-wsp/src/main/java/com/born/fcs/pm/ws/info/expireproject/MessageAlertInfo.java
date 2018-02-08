package com.born.fcs.pm.ws.info.expireproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class MessageAlertInfo extends BaseToStringInfo{

	private static final long serialVersionUID = -7239097329416552178L;

	private long alertId;
	private String projectCode;
	private String customerName;
	private String alertPhrase;
	private String active;
	private Date lastAlertTime;
	private Date rawAddTime;
	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getAlertId() {
		return alertId;
	}
	
	public void setAlertId(long alertId) {
		this.alertId = alertId;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAlertPhrase() {
		return alertPhrase;
	}
	
	public void setAlertPhrase(String alertPhrase) {
		this.alertPhrase = alertPhrase;
	}

	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}

	public Date getLastAlertTime() {
		return lastAlertTime;
	}
	
	public void setLastAlertTime(Date lastAlertTime) {
		this.lastAlertTime = lastAlertTime;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

}
