/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.yjf.common.lang.util.money.Money;

/***
 * 追偿跟踪表 - 债务人重整或破产清算表
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 申请人
	 */
	private String applicant;
	
	/**
	 * 受理法院
	 */
	private String acceptingCourt;
	
	/**
	 * 债权申报截止日期
	 */
	private Date debtsDeclareEndTime;
	
	/**
	 * 我司申报日期
	 */
	private Date divisionWeDeclareTime;
	
	/**
	 * 债权确认
	 */
	private String debtsConfirm;
	
	/**
	 * 会议情况
	 */
	private String councilCircumstances;
	
	/**
	 * 我司意见
	 */
	private String weSuggestion;
	
	/**
	 * 批准的重整方案及执行情况
	 */
	private String reExecutionPlan;
	
	/**
	 * 和解方案
	 */
	private String settlementSchemeContent;
	
	/**
	 * 清算方案
	 */
	private String liquidationScheme;
	
	/**
	 * 清偿情况
	 */
	private String liquidationSituation;
	
	/**
	 * 回收总金额
	 */
	private Money recoveryTotalAmount = new Money(0, 0);
	
	/**
	 * 备注
	 */
	private String memo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件开始 
	/*** 债务人重整或破产清算-我司申报时间-附件 **/
	private String recoveryReorganizationDivisionWeDeclareTimeUrl;
	/*** 债务人重整或破产清算-我司申报时间-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationDivisionWeDeclareTime;
	
	/*** 债务人重整或破产清算-债权确认-附件 **/
	private String recoveryReorganizationDebtsConfirmUrl;
	/*** 债务人重整或破产清算-债权确认-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationDebtsConfirm;
	/*** 债务人重整或破产清算-会议情况-附件 **/
	private String recoveryReorganizationCouncilCircumstancesUrl;
	/*** 债务人重整或破产清算-会议情况-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationCouncilCircumstances;
	
	/*** 债务人重整或破产清算-我司意见-附件 **/
	private String recoveryReorganizationWeSuggestionUrl;
	/*** 债务人重整或破产清算-我司意见-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationWeSuggestion;
	/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件 **/
	private String recoveryReorganizationReExecutionPlanUrl;
	/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationReExecutionPlan;
	/*** 债务人重整或破产清算-和解方案-附件 **/
	private String recoveryReorganizationSettlementSchemeContentUrl;
	/*** 债务人重整或破产清算-和解方案-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationSettlementSchemeContent;
	/*** 债务人重整或破产清算-清算方案-附件 **/
	private String recoveryReorganizationLiquidationSchemeUrl;
	/*** 债务人重整或破产清算-清算方案-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationLiquidationScheme;
	/*** 债务人重整或破产清算-清偿情况-附件 **/
	private String recoveryReorganizationLiquidationSituationUrl;
	/*** 债务人重整或破产清算-清偿情况-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationLiquidationSituation;
	
	// 添加子表信息  
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表 **/
	private List<ProjectRecoveryDebtorReorganizationDebtsCouncilInfo> projectRecoveryDebtorReorganizationDebtsCouncilInfos;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细 **/
	private List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> projectRecoveryDebtorReorganizationAmountDetailInfos;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 **/
	private List<ProjectRecoveryDebtorReorganizationPledgeInfo> projectRecoveryDebtorReorganizationPledgeInfos;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细-总金额 **/
	private Money projectRecoveryDebtorReorganizationPledgeTotalAmount;
	
	//========== getters and setters ==========
	
	public Money getProjectRecoveryDebtorReorganizationPledgeTotalAmount() {
		if (projectRecoveryDebtorReorganizationPledgeTotalAmount != null) {
			return projectRecoveryDebtorReorganizationPledgeTotalAmount;
		}
		if (projectRecoveryDebtorReorganizationPledgeInfos != null) {
			projectRecoveryDebtorReorganizationPledgeTotalAmount = Money.zero();
			for (ProjectRecoveryDebtorReorganizationPledgeInfo pledgeInfo : projectRecoveryDebtorReorganizationPledgeInfos) {
				projectRecoveryDebtorReorganizationPledgeTotalAmount.addTo(pledgeInfo
					.getPledgeAmount());
			}
		}
		
		return projectRecoveryDebtorReorganizationPledgeTotalAmount;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeTotalAmount(Money projectRecoveryDebtorReorganizationPledgeTotalAmount) {
		this.projectRecoveryDebtorReorganizationPledgeTotalAmount = projectRecoveryDebtorReorganizationPledgeTotalAmount;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<ProjectRecoveryDebtorReorganizationDebtsCouncilInfo> getProjectRecoveryDebtorReorganizationDebtsCouncilInfos() {
		return this.projectRecoveryDebtorReorganizationDebtsCouncilInfos;
	}
	
	public void setProjectRecoveryDebtorReorganizationDebtsCouncilInfos(List<ProjectRecoveryDebtorReorganizationDebtsCouncilInfo> projectRecoveryDebtorReorganizationDebtsCouncilInfos) {
		this.projectRecoveryDebtorReorganizationDebtsCouncilInfos = projectRecoveryDebtorReorganizationDebtsCouncilInfos;
	}
	
	public List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> getProjectRecoveryDebtorReorganizationAmountDetailInfos() {
		return this.projectRecoveryDebtorReorganizationAmountDetailInfos;
	}
	
	public void setProjectRecoveryDebtorReorganizationAmountDetailInfos(List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> projectRecoveryDebtorReorganizationAmountDetailInfos) {
		this.projectRecoveryDebtorReorganizationAmountDetailInfos = projectRecoveryDebtorReorganizationAmountDetailInfos;
	}
	
	public List<ProjectRecoveryDebtorReorganizationPledgeInfo> getProjectRecoveryDebtorReorganizationPledgeInfos() {
		return this.projectRecoveryDebtorReorganizationPledgeInfos;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeInfos(	List<ProjectRecoveryDebtorReorganizationPledgeInfo> projectRecoveryDebtorReorganizationPledgeInfos) {
		this.projectRecoveryDebtorReorganizationPledgeInfos = projectRecoveryDebtorReorganizationPledgeInfos;
	}
	
	public String getRecoveryReorganizationDivisionWeDeclareTimeUrl() {
		return this.recoveryReorganizationDivisionWeDeclareTimeUrl;
	}
	
	public void setRecoveryReorganizationDivisionWeDeclareTimeUrl(	String recoveryReorganizationDivisionWeDeclareTimeUrl) {
		this.recoveryReorganizationDivisionWeDeclareTimeUrl = recoveryReorganizationDivisionWeDeclareTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationDivisionWeDeclareTime() {
		return this.recoveryReorganizationDivisionWeDeclareTime;
	}
	
	public void setRecoveryReorganizationDivisionWeDeclareTime(	List<CommonAttachmentInfo> recoveryReorganizationDivisionWeDeclareTime) {
		this.recoveryReorganizationDivisionWeDeclareTime = recoveryReorganizationDivisionWeDeclareTime;
	}
	
	public String getRecoveryReorganizationDebtsConfirmUrl() {
		return this.recoveryReorganizationDebtsConfirmUrl;
	}
	
	public void setRecoveryReorganizationDebtsConfirmUrl(	String recoveryReorganizationDebtsConfirmUrl) {
		this.recoveryReorganizationDebtsConfirmUrl = recoveryReorganizationDebtsConfirmUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationDebtsConfirm() {
		return this.recoveryReorganizationDebtsConfirm;
	}
	
	public void setRecoveryReorganizationDebtsConfirm(	List<CommonAttachmentInfo> recoveryReorganizationDebtsConfirm) {
		this.recoveryReorganizationDebtsConfirm = recoveryReorganizationDebtsConfirm;
	}
	
	public String getRecoveryReorganizationCouncilCircumstancesUrl() {
		return this.recoveryReorganizationCouncilCircumstancesUrl;
	}
	
	public void setRecoveryReorganizationCouncilCircumstancesUrl(	String recoveryReorganizationCouncilCircumstancesUrl) {
		this.recoveryReorganizationCouncilCircumstancesUrl = recoveryReorganizationCouncilCircumstancesUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationCouncilCircumstances() {
		return this.recoveryReorganizationCouncilCircumstances;
	}
	
	public void setRecoveryReorganizationCouncilCircumstances(	List<CommonAttachmentInfo> recoveryReorganizationCouncilCircumstances) {
		this.recoveryReorganizationCouncilCircumstances = recoveryReorganizationCouncilCircumstances;
	}
	
	public String getRecoveryReorganizationWeSuggestionUrl() {
		return this.recoveryReorganizationWeSuggestionUrl;
	}
	
	public void setRecoveryReorganizationWeSuggestionUrl(	String recoveryReorganizationWeSuggestionUrl) {
		this.recoveryReorganizationWeSuggestionUrl = recoveryReorganizationWeSuggestionUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationWeSuggestion() {
		return this.recoveryReorganizationWeSuggestion;
	}
	
	public void setRecoveryReorganizationWeSuggestion(	List<CommonAttachmentInfo> recoveryReorganizationWeSuggestion) {
		this.recoveryReorganizationWeSuggestion = recoveryReorganizationWeSuggestion;
	}
	
	public String getRecoveryReorganizationReExecutionPlanUrl() {
		return this.recoveryReorganizationReExecutionPlanUrl;
	}
	
	public void setRecoveryReorganizationReExecutionPlanUrl(String recoveryReorganizationReExecutionPlanUrl) {
		this.recoveryReorganizationReExecutionPlanUrl = recoveryReorganizationReExecutionPlanUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationReExecutionPlan() {
		return this.recoveryReorganizationReExecutionPlan;
	}
	
	public void setRecoveryReorganizationReExecutionPlan(	List<CommonAttachmentInfo> recoveryReorganizationReExecutionPlan) {
		this.recoveryReorganizationReExecutionPlan = recoveryReorganizationReExecutionPlan;
	}
	
	public String getRecoveryReorganizationSettlementSchemeContentUrl() {
		return this.recoveryReorganizationSettlementSchemeContentUrl;
	}
	
	public void setRecoveryReorganizationSettlementSchemeContentUrl(String recoveryReorganizationSettlementSchemeContentUrl) {
		this.recoveryReorganizationSettlementSchemeContentUrl = recoveryReorganizationSettlementSchemeContentUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationSettlementSchemeContent() {
		return this.recoveryReorganizationSettlementSchemeContent;
	}
	
	public void setRecoveryReorganizationSettlementSchemeContent(	List<CommonAttachmentInfo> recoveryReorganizationSettlementSchemeContent) {
		this.recoveryReorganizationSettlementSchemeContent = recoveryReorganizationSettlementSchemeContent;
	}
	
	public String getRecoveryReorganizationLiquidationSchemeUrl() {
		return this.recoveryReorganizationLiquidationSchemeUrl;
	}
	
	public void setRecoveryReorganizationLiquidationSchemeUrl(	String recoveryReorganizationLiquidationSchemeUrl) {
		this.recoveryReorganizationLiquidationSchemeUrl = recoveryReorganizationLiquidationSchemeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationLiquidationScheme() {
		return this.recoveryReorganizationLiquidationScheme;
	}
	
	public void setRecoveryReorganizationLiquidationScheme(	List<CommonAttachmentInfo> recoveryReorganizationLiquidationScheme) {
		this.recoveryReorganizationLiquidationScheme = recoveryReorganizationLiquidationScheme;
	}
	
	public String getRecoveryReorganizationLiquidationSituationUrl() {
		return this.recoveryReorganizationLiquidationSituationUrl;
	}
	
	public void setRecoveryReorganizationLiquidationSituationUrl(	String recoveryReorganizationLiquidationSituationUrl) {
		this.recoveryReorganizationLiquidationSituationUrl = recoveryReorganizationLiquidationSituationUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationLiquidationSituation() {
		return this.recoveryReorganizationLiquidationSituation;
	}
	
	public void setRecoveryReorganizationLiquidationSituation(	List<CommonAttachmentInfo> recoveryReorganizationLiquidationSituation) {
		this.recoveryReorganizationLiquidationSituation = recoveryReorganizationLiquidationSituation;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public String getApplicant() {
		return applicant;
	}
	
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	public String getAcceptingCourt() {
		return acceptingCourt;
	}
	
	public void setAcceptingCourt(String acceptingCourt) {
		this.acceptingCourt = acceptingCourt;
	}
	
	public Date getDebtsDeclareEndTime() {
		return debtsDeclareEndTime;
	}
	
	public void setDebtsDeclareEndTime(Date debtsDeclareEndTime) {
		this.debtsDeclareEndTime = debtsDeclareEndTime;
	}
	
	public Date getDivisionWeDeclareTime() {
		return divisionWeDeclareTime;
	}
	
	public void setDivisionWeDeclareTime(Date divisionWeDeclareTime) {
		this.divisionWeDeclareTime = divisionWeDeclareTime;
	}
	
	public String getDebtsConfirm() {
		return debtsConfirm;
	}
	
	public void setDebtsConfirm(String debtsConfirm) {
		this.debtsConfirm = debtsConfirm;
	}
	
	public String getCouncilCircumstances() {
		return councilCircumstances;
	}
	
	public void setCouncilCircumstances(String councilCircumstances) {
		this.councilCircumstances = councilCircumstances;
	}
	
	public String getWeSuggestion() {
		return weSuggestion;
	}
	
	public void setWeSuggestion(String weSuggestion) {
		this.weSuggestion = weSuggestion;
	}
	
	public String getReExecutionPlan() {
		return reExecutionPlan;
	}
	
	public void setReExecutionPlan(String reExecutionPlan) {
		this.reExecutionPlan = reExecutionPlan;
	}
	
	public String getSettlementSchemeContent() {
		return settlementSchemeContent;
	}
	
	public void setSettlementSchemeContent(String settlementSchemeContent) {
		this.settlementSchemeContent = settlementSchemeContent;
	}
	
	public String getLiquidationScheme() {
		return liquidationScheme;
	}
	
	public void setLiquidationScheme(String liquidationScheme) {
		this.liquidationScheme = liquidationScheme;
	}
	
	public String getLiquidationSituation() {
		return liquidationSituation;
	}
	
	public void setLiquidationSituation(String liquidationSituation) {
		this.liquidationSituation = liquidationSituation;
	}
	
	public Money getRecoveryTotalAmount() {
		return recoveryTotalAmount;
	}
	
	public void setRecoveryTotalAmount(Money recoveryTotalAmount) {
		if (recoveryTotalAmount == null) {
			this.recoveryTotalAmount = new Money(0, 0);
		} else {
			this.recoveryTotalAmount = recoveryTotalAmount;
		}
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
