/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

// auto generated imports
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 表单项目信息
 *
 * @author wuzj
 */
public class SetupFormProjectDO extends FormProjectDO {
	
	private static final long serialVersionUID = 3164312825136406972L;
	
	private long investigateId; //尽职调查ID
	
	private long investigateFormId;
	
	//IS : 尽职复议但未填写 ，  YES：复议  ， NO：非复议
	private String investigateReview;
	
	private long riskReviewId; //风险审查报告ID
	
	private long riskReviewFormId;
	
	private long loginUserId;
	
	private long draftUserId;
	
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public String getInvestigateReview() {
		return this.investigateReview;
	}
	
	public void setInvestigateReview(String investigateReview) {
		this.investigateReview = investigateReview;
	}
	
	public long getDraftUserId() {
		return this.draftUserId;
	}
	
	public void setDraftUserId(long draftUserId) {
		this.draftUserId = draftUserId;
	}
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
