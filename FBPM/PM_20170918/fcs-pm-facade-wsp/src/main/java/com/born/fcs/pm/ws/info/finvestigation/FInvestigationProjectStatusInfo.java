package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 项目情况调查
 * 
 * @author lirz
 *
 * 2016-3-10 下午5:32:02
 */
public class FInvestigationProjectStatusInfo extends InvestigationBaseInfo {

	private static final long serialVersionUID = 5811557170243648875L;
	
	private long statusId;
	//项目概述（简要说明客户/项目发起人的项目计划，生产规模，地理位置等）
	private String overview; 
	private String background; //项目建设背景/必要性
	private String approval; //项目审批依据或手续
	//项目建设进度（如项目已开工，请简述工程形象进度和资金投入进度）
	private String progress;
	private String marketOutlook; //市场前景分析
	private Money totalCost = new Money(0, 0); //总成本
	private String costFundraising; //项目总成本及资金筹措
	private String benefitReview; //项目财务效益评估
	private Date rawAddTime;
	private Date rawUpdateTime;
	//项目投资与资金筹措
	private List<FInvestigationProjectStatusFundInfo> funds;

    //========== getters and setters ==========

	public long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getOverview() {
		return overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBackground() {
		return background;
	}
	
	public void setBackground(String background) {
		this.background = background;
	}

	public String getApproval() {
		return approval;
	}
	
	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getProgress() {
		return progress;
	}
	
	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getMarketOutlook() {
		return marketOutlook;
	}
	
	public void setMarketOutlook(String marketOutlook) {
		this.marketOutlook = marketOutlook;
	}

	public Money getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Money totalCost) {
		this.totalCost = totalCost;
	}

	public String getCostFundraising() {
		return costFundraising;
	}
	
	public void setCostFundraising(String costFundraising) {
		this.costFundraising = costFundraising;
	}

	public String getBenefitReview() {
		return benefitReview;
	}
	
	public void setBenefitReview(String benefitReview) {
		this.benefitReview = benefitReview;
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

	public List<FInvestigationProjectStatusFundInfo> getFunds() {
		return funds;
	}

	public void setFunds(List<FInvestigationProjectStatusFundInfo> funds) {
		this.funds = funds;
	}

}
