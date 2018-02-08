package com.born.fcs.pm.dataobject;

import java.util.Date;

public class RiskReviewFormProjectDO extends FormProjectDO {
	
	private static final long serialVersionUID = 7784317685945398495L;
	
	private Date riskReviewAddTime;
	
	private long id;
	
	public Date getRiskReviewAddTime() {
		return this.riskReviewAddTime;
	}
	
	public void setRiskReviewAddTime(Date riskReviewAddTime) {
		this.riskReviewAddTime = riskReviewAddTime;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
