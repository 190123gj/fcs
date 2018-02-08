package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;

/**
 * 客户管理能力评价
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:33:17
 */
public class FInvestigationMabilityReviewOrder extends FInvestigationBaseOrder {
	
	private static final long serialVersionUID = -165842016322254969L;
	
	private long maReviewId;
	//领导人整体评价（包括主要领导人简历、管理层的稳定性）
	private String leaderReview;
	//员工基本情况及整体素质评价
	private String staffReview;
	//客户主要高管人员
	private List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams;
	
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
	
	public List<FInvestigationMabilityReviewLeadingTeamOrder> getLeadingTeams() {
		return leadingTeams;
	}

	public void setLeadingTeams(List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams) {
		this.leadingTeams = leadingTeams;
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
