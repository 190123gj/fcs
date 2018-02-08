package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 客户管理能力评价
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:33:17
 */
public class FInvestigationMabilityReviewInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -624546783305189571L;
	
	private long maReviewId;
	//领导人整体评价（包括主要领导人简历、管理层的稳定性）
	private String leaderReview;
	//员工基本情况及整体素质评价
	private String staffReview;
	//客户主要高管人员
	private List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getMaReviewId() {
		return maReviewId;
	}
	
	public void setMaReviewId(long maReviewId) {
		this.maReviewId = maReviewId;
	}
	
	public String getLeaderReview() {
		return leaderReview;
	}
	
	public void setLeaderReview(String leaderReview) {
		this.leaderReview = leaderReview;
	}
	
	public String getStaffReview() {
		return staffReview;
	}
	
	public void setStaffReview(String staffReview) {
		this.staffReview = staffReview;
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

	public List<FInvestigationMabilityReviewLeadingTeamInfo> getLeadingTeams() {
		return leadingTeams;
	}

	public void setLeadingTeams(List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams) {
		this.leadingTeams = leadingTeams;
	}
	
}
