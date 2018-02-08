/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

/**
 * 理财项目送审列表DO
 *
 * @author wuzj
 */
public class FinancialProjectReviewFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = 3435342935097844719L;
	
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
	
	//查询部分
	private String productName;
	
	private String issueInstitution;
	
	private String sortCol;
	
	private String sortOrder;
	
	private long limitStart;
	
	private long pageSize;
	
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	private List<String> formStatusList;
	
	private long busiManagerId;
	
	private long busiManagerbId;
	
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
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
}
