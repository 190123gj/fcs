package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 理财项目送审Order
 * @author wuzj
 */
public class ProjectFinancialReviewOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 3053665378110481257L;
	/** 主键 */
	private long reviewId;
	/** 理财项目编号（立项） */
	private String projectCode;
	/** 尽调内容 */
	private String investigation;
	/** 尽调附件 */
	private String investigationAttach;
	/** 风险调查内容 */
	private String riskReview;
	/** 风险调查附件 */
	private String riskReviewAttach;
	
	public long getReviewId() {
		return this.reviewId;
	}
	
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getInvestigation() {
		return this.investigation;
	}
	
	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}
	
	public String getInvestigationAttach() {
		return this.investigationAttach;
	}
	
	public void setInvestigationAttach(String investigationAttach) {
		this.investigationAttach = investigationAttach;
	}
	
	public String getRiskReview() {
		return this.riskReview;
	}
	
	public void setRiskReview(String riskReview) {
		this.riskReview = riskReview;
	}
	
	public String getRiskReviewAttach() {
		return this.riskReviewAttach;
	}
	
	public void setRiskReviewAttach(String riskReviewAttach) {
		this.riskReviewAttach = riskReviewAttach;
	}
	
}
