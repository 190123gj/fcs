package com.born.fcs.pm.ws.info.finvestigation;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 列表
 * 
 * @author lirz
 * 
 * 2016-7-8 下午4:25:52
 */
public class InvestigationInfo extends SimpleFormProjectVOInfo {
	
	private static final long serialVersionUID = 4587839074100994281L;
	
	private long investigateId;
	private String review; //复议标识
	private BooleanEnum councilBack; //上会退回
	private Money creditAmount;
	private ProjectStatusEnum projectStatus; //项目状态
	
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
	
	public BooleanEnum getCouncilBack() {
		return this.councilBack;
	}

	public void setCouncilBack(BooleanEnum councilBack) {
		this.councilBack = councilBack;
	}

	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public ProjectStatusEnum getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}
	
}
