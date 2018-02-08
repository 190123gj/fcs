package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

/**
 * 授信方案主要事项合理性评价
 * 
 * @author lirz
 * 
 * 2016-3-10 下午5:58:07
 */
public class FInvestigationCsRationalityReviewInfo extends InvestigationBaseInfo {
	
	private static final long serialVersionUID = -4704542784055591438L;
	
	private long csrReviewId;
	private String amountLimitReview; //本次授信额度合理性评价
	private String timeLimitReview; //本次授信期限合理性评价
	private String loanPurposeReview; //授信用途合理性评价
	private String repaySourceReview; //第一还款来源分析
	
//	private FInvestigationMainlyReviewInfo mainlyReview;
	private String guarantorReview; //保证人合法性评价（此处不含担保公司评价）
	private String guaranteeCompanyInfo; //担保公司基本情况
	//担保公司担保能力总体评价（担保公司股东背景及股份结构、履保情况、合作情况等）
	private String guaranteeCompanyAbility;
	//客户提供反担保情况（担保方式、资产种类、数量及价值情况）
	private String counterGuaranteeInfo;
	private String guarantorInfo; //担保人基本情况及合法性评价
	private String pledgeValue; //担保物基本情况及评估价值评价
	//评估机构名称、评估时间、评估方法、初评价值评价
	private String reviewSummary;
	private String otherRepaySource; //其它还款来源
	//保证人合法性评价-基本信息
//	private FInvestigationReviewBaseInfo reviewBaseInfo;
	//已获得的资质证书
//	private List<FInvestigationMainlyReviewCertificateInfo> certificates;
	//客户高管人员列表
//	private List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams;
	//保证人主要财务指标
//	private List<FInvestigationCsRationalityReviewFinancialKpiInfo> kpies;
//	private InvestigationFinancialKpiInfo header;
//	private List<InvestigationFinancialKpiInfo> kpies;

	//保证人保证能力总体评价（非必填）
	private List<FInvestigationCsRationalityReviewGuarantorAbilityInfo> guarantorAbilities;
	private Date rawAddTime;
	private Date rawUpdateTime;
	//新增的担保人
	private List<FInvestigationCsRationalityReviewGuarantorInfo> guarantors; //保证人
	
	public long getCsrReviewId() {
		return csrReviewId;
	}
	
	public void setCsrReviewId(long csrReviewId) {
		this.csrReviewId = csrReviewId;
	}
	
	public String getAmountLimitReview() {
		return amountLimitReview;
	}
	
	public void setAmountLimitReview(String amountLimitReview) {
		this.amountLimitReview = amountLimitReview;
	}
	
	public String getTimeLimitReview() {
		return timeLimitReview;
	}
	
	public void setTimeLimitReview(String timeLimitReview) {
		this.timeLimitReview = timeLimitReview;
	}
	
	public String getLoanPurposeReview() {
		return loanPurposeReview;
	}
	
	public void setLoanPurposeReview(String loanPurposeReview) {
		this.loanPurposeReview = loanPurposeReview;
	}
	
	public String getRepaySourceReview() {
		return repaySourceReview;
	}
	
	public void setRepaySourceReview(String repaySourceReview) {
		this.repaySourceReview = repaySourceReview;
	}
	
	public String getGuarantorReview() {
		return guarantorReview;
	}
	
	public void setGuarantorReview(String guarantorReview) {
		this.guarantorReview = guarantorReview;
	}
	
	public String getGuaranteeCompanyInfo() {
		return guaranteeCompanyInfo;
	}
	
	public void setGuaranteeCompanyInfo(String guaranteeCompanyInfo) {
		this.guaranteeCompanyInfo = guaranteeCompanyInfo;
	}
	
	public String getGuaranteeCompanyAbility() {
		return guaranteeCompanyAbility;
	}
	
	public void setGuaranteeCompanyAbility(String guaranteeCompanyAbility) {
		this.guaranteeCompanyAbility = guaranteeCompanyAbility;
	}
	
	public String getCounterGuaranteeInfo() {
		return counterGuaranteeInfo;
	}
	
	public void setCounterGuaranteeInfo(String counterGuaranteeInfo) {
		this.counterGuaranteeInfo = counterGuaranteeInfo;
	}
	
	public String getGuarantorInfo() {
		return guarantorInfo;
	}
	
	public void setGuarantorInfo(String guarantorInfo) {
		this.guarantorInfo = guarantorInfo;
	}
	
	public String getPledgeValue() {
		return pledgeValue;
	}
	
	public void setPledgeValue(String pledgeValue) {
		this.pledgeValue = pledgeValue;
	}
	
	public String getReviewSummary() {
		return reviewSummary;
	}
	
	public void setReviewSummary(String reviewSummary) {
		this.reviewSummary = reviewSummary;
	}
	
	public String getOtherRepaySource() {
		return otherRepaySource;
	}
	
	public void setOtherRepaySource(String otherRepaySource) {
		this.otherRepaySource = otherRepaySource;
	}
	
	public List<FInvestigationCsRationalityReviewGuarantorAbilityInfo> getGuarantorAbilities() {
		return guarantorAbilities;
	}
	
	public void setGuarantorAbilities(	List<FInvestigationCsRationalityReviewGuarantorAbilityInfo> guarantorAbilities) {
		this.guarantorAbilities = guarantorAbilities;
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
	
	public List<FInvestigationCsRationalityReviewGuarantorInfo> getGuarantors() {
		return this.guarantors;
	}

	public void setGuarantors(List<FInvestigationCsRationalityReviewGuarantorInfo> guarantors) {
		this.guarantors = guarantors;
	}
	
}
