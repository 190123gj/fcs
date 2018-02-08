package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 理财项目送审Order
 * @author wuzj
 */
public class ProjectFinancialRiskReviewOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 8130632230806813445L;
	/** 表单 */
	private long formId;
	/** 风险调查内容 */
	private String riskReview;
	/** 风险调查附件 */
	private String riskReviewAttach;
	
	@Override
	public void check() {
		validateGreaterThan(formId, "送审申请单ID");
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
