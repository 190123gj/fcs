package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目情况调查
 * 
 * @author lirz
 *
 * 2016-3-10 下午5:32:02
 */
public class FInvestigationProjectStatusOrder extends ProjectFormOrderBase{

	private static final long serialVersionUID = -2483370328159986223L;

	private long statusId;
	//项目概述（简要说明客户/项目发起人的项目计划，生产规模，地理位置等）
	private String overview; 
	private String background; //项目建设背景/必要性
	private String approval; //项目审批依据或手续
	//项目建设进度（如项目已开工，请简述工程形象进度和资金投入进度）
	private String progress;
	private String marketOutlook; //市场前景分析
	private String totalCostStr; //总成本
	private String costFundraising; //项目总成本及资金筹措
	private String benefitReview; //项目财务效益评估
	//项目投资与资金筹措
	private List<FInvestigationProjectStatusFundOrder> funds;

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
		if (isNull(this.totalCostStr)) {
			return new Money(0L);
		}
		return Money.amout(this.totalCostStr);
	}
	
	public String getTotalCostStr() {
		return totalCostStr;
	}

	public void setTotalCostStr(String totalCostStr) {
		this.totalCostStr = totalCostStr;
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

	public List<FInvestigationProjectStatusFundOrder> getFunds() {
		return funds;
	}

	public void setFunds(List<FInvestigationProjectStatusFundOrder> funds) {
		this.funds = funds;
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
