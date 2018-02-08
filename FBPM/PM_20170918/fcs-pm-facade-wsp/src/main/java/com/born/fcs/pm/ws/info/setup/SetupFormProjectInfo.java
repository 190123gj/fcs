package com.born.fcs.pm.ws.info.setup;

import com.born.fcs.pm.ws.info.common.FormProjectInfo;

public class SetupFormProjectInfo extends FormProjectInfo {
	
	private static final long serialVersionUID = 2597383193470879907L;
	
	private long investigateId; //尽职调查ID
	
	private long investigateFormId;
	
	//IS : 尽职复议但未填写 ，  YES：复议  ， NO：非复议
	private String investigateReview;
	
	private long riskReviewId; //风险审查报告ID
	
	private long riskReviewFormId;
	
	public long getInvestigateId() {
		return this.investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public long getInvestigateFormId() {
		return this.investigateFormId;
	}
	
	public void setInvestigateFormId(long investigateFormId) {
		this.investigateFormId = investigateFormId;
	}
	
	public long getRiskReviewId() {
		return this.riskReviewId;
	}
	
	public void setRiskReviewId(long riskReviewId) {
		this.riskReviewId = riskReviewId;
	}
	
	public long getRiskReviewFormId() {
		return this.riskReviewFormId;
	}
	
	public void setRiskReviewFormId(long riskReviewFormId) {
		this.riskReviewFormId = riskReviewFormId;
	}
	
	public String getInvestigateReview() {
		return this.investigateReview;
	}
	
	public void setInvestigateReview(String investigateReview) {
		this.investigateReview = investigateReview;
	}
	
}
