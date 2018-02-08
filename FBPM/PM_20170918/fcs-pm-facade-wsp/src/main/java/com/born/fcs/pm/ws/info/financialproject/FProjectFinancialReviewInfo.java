package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 理财项目送审列表Info
 *
 * @author wuzj
 */
public class FProjectFinancialReviewInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -4732494285739750008L;
	
	/** 主键 */
	private long reviewId;
	/** 表单ID */
	private long formId;
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
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getReviewId() {
		return this.reviewId;
	}
	
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
}
