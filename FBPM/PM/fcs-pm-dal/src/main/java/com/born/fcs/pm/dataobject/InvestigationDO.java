package com.born.fcs.pm.dataobject;

import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 尽职调查列表DO
 * 
 * @author lirz
 * 
 * 2016-5-27 下午4:01:28
 */
public class InvestigationDO extends SimpleFormProjectDO {
	
	private static final long serialVersionUID = -594969992570925673L;
	
	private long investigateId;
	private String review; //复议标识
	private String councilBack; //上会退回
	private Money creditAmount;
	private String projectStatus; //项目状态
	
	public long getInvestigateId() {
		return investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getCouncilBack() {
		return this.councilBack;
	}

	public void setCouncilBack(String councilBack) {
		this.councilBack = councilBack;
	}

	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
}
