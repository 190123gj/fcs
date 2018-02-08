/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/***
 * 追偿跟踪表 - 债务人重整或破产清算表
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 申请人
	 */
	private String applicant;
	
	/**
	 * 受理法院
	 */
	private String acceptingCourt;
	
	/**
	 * 债权申报截止日期 Date
	 */
	private String debtsDeclareEndTime;
	
	/**
	 * 我司申报日期 Date
	 */
	private String divisionWeDeclareTime;
	
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
	 * 回收总金额 Money
	 */
	private String recoveryTotalAmount;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件开始 
	/*** 债务人重整或破产清算-我司申报时间-附件 **/
	private String recoveryReorganizationDivisionWeDeclareTimeUrl;
	
	/*** 债务人重整或破产清算-债权确认-附件 **/
	private String recoveryReorganizationDebtsConfirmUrl;
	/*** 债务人重整或破产清算-会议情况-附件 **/
	private String recoveryReorganizationCouncilCircumstancesUrl;
	
	/*** 债务人重整或破产清算-我司意见-附件 **/
	private String recoveryReorganizationWeSuggestionUrl;
	/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件 **/
	private String recoveryReorganizationReExecutionPlanUrl;
	/*** 债务人重整或破产清算-和解方案-附件 **/
	private String recoveryReorganizationSettlementSchemeContentUrl;
	/*** 债务人重整或破产清算-清算方案-附件 **/
	private String recoveryReorganizationLiquidationSchemeUrl;
	/*** 债务人重整或破产清算-清偿情况-附件 **/
	private String recoveryReorganizationLiquidationSituationUrl;
	
	// 添加子表信息  
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表 **/
	private List<ProjectRecoveryDebtorReorganizationDebtsCouncilOrder> projectRecoveryDebtorReorganizationDebtsCouncilOrder;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细 **/
	private List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> projectRecoveryDebtorReorganizationAmountDetailOrder;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 **/
	private List<ProjectRecoveryDebtorReorganizationPledgeOrder> projectRecoveryDebtorReorganizationPledgeOrder;
	
	//========== getters and setters ==========
	
	public String getRecoveryReorganizationDivisionWeDeclareTimeUrl() {
		return this.recoveryReorganizationDivisionWeDeclareTimeUrl;
	}
	
	public List<ProjectRecoveryDebtorReorganizationDebtsCouncilOrder> getProjectRecoveryDebtorReorganizationDebtsCouncilOrder() {
		return this.projectRecoveryDebtorReorganizationDebtsCouncilOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationDebtsCouncilOrder(List<ProjectRecoveryDebtorReorganizationDebtsCouncilOrder> projectRecoveryDebtorReorganizationDebtsCouncilOrder) {
		this.projectRecoveryDebtorReorganizationDebtsCouncilOrder = projectRecoveryDebtorReorganizationDebtsCouncilOrder;
	}
	
	public List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> getProjectRecoveryDebtorReorganizationAmountDetailOrder() {
		return this.projectRecoveryDebtorReorganizationAmountDetailOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationAmountDetailOrder(List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> projectRecoveryDebtorReorganizationAmountDetailOrder) {
		this.projectRecoveryDebtorReorganizationAmountDetailOrder = projectRecoveryDebtorReorganizationAmountDetailOrder;
	}
	
	public List<ProjectRecoveryDebtorReorganizationPledgeOrder> getProjectRecoveryDebtorReorganizationPledgeOrder() {
		return this.projectRecoveryDebtorReorganizationPledgeOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeOrder(	List<ProjectRecoveryDebtorReorganizationPledgeOrder> projectRecoveryDebtorReorganizationPledgeOrder) {
		this.projectRecoveryDebtorReorganizationPledgeOrder = projectRecoveryDebtorReorganizationPledgeOrder;
	}
	
	public void setRecoveryReorganizationDivisionWeDeclareTimeUrl(	String recoveryReorganizationDivisionWeDeclareTimeUrl) {
		this.recoveryReorganizationDivisionWeDeclareTimeUrl = recoveryReorganizationDivisionWeDeclareTimeUrl;
	}
	
	public String getRecoveryReorganizationDebtsConfirmUrl() {
		return this.recoveryReorganizationDebtsConfirmUrl;
	}
	
	public void setRecoveryReorganizationDebtsConfirmUrl(	String recoveryReorganizationDebtsConfirmUrl) {
		this.recoveryReorganizationDebtsConfirmUrl = recoveryReorganizationDebtsConfirmUrl;
	}
	
	public String getRecoveryReorganizationCouncilCircumstancesUrl() {
		return this.recoveryReorganizationCouncilCircumstancesUrl;
	}
	
	public void setRecoveryReorganizationCouncilCircumstancesUrl(	String recoveryReorganizationCouncilCircumstancesUrl) {
		this.recoveryReorganizationCouncilCircumstancesUrl = recoveryReorganizationCouncilCircumstancesUrl;
	}
	
	public String getRecoveryReorganizationWeSuggestionUrl() {
		return this.recoveryReorganizationWeSuggestionUrl;
	}
	
	public void setRecoveryReorganizationWeSuggestionUrl(	String recoveryReorganizationWeSuggestionUrl) {
		this.recoveryReorganizationWeSuggestionUrl = recoveryReorganizationWeSuggestionUrl;
	}
	
	public String getRecoveryReorganizationReExecutionPlanUrl() {
		return this.recoveryReorganizationReExecutionPlanUrl;
	}
	
	public void setRecoveryReorganizationReExecutionPlanUrl(String recoveryReorganizationReExecutionPlanUrl) {
		this.recoveryReorganizationReExecutionPlanUrl = recoveryReorganizationReExecutionPlanUrl;
	}
	
	public String getRecoveryReorganizationSettlementSchemeContentUrl() {
		return this.recoveryReorganizationSettlementSchemeContentUrl;
	}
	
	public void setRecoveryReorganizationSettlementSchemeContentUrl(String recoveryReorganizationSettlementSchemeContentUrl) {
		this.recoveryReorganizationSettlementSchemeContentUrl = recoveryReorganizationSettlementSchemeContentUrl;
	}
	
	public String getRecoveryReorganizationLiquidationSchemeUrl() {
		return this.recoveryReorganizationLiquidationSchemeUrl;
	}
	
	public void setRecoveryReorganizationLiquidationSchemeUrl(	String recoveryReorganizationLiquidationSchemeUrl) {
		this.recoveryReorganizationLiquidationSchemeUrl = recoveryReorganizationLiquidationSchemeUrl;
	}
	
	public String getRecoveryReorganizationLiquidationSituationUrl() {
		return this.recoveryReorganizationLiquidationSituationUrl;
	}
	
	public void setRecoveryReorganizationLiquidationSituationUrl(	String recoveryReorganizationLiquidationSituationUrl) {
		this.recoveryReorganizationLiquidationSituationUrl = recoveryReorganizationLiquidationSituationUrl;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectRecoveryId() {
		return this.projectRecoveryId;
	}
	
	public void setProjectRecoveryId(Long projectRecoveryId) {
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
	
	public String getDebtsDeclareEndTime() {
		return this.debtsDeclareEndTime;
	}
	
	public void setDebtsDeclareEndTime(String debtsDeclareEndTime) {
		this.debtsDeclareEndTime = debtsDeclareEndTime;
	}
	
	public String getDivisionWeDeclareTime() {
		return this.divisionWeDeclareTime;
	}
	
	public void setDivisionWeDeclareTime(String divisionWeDeclareTime) {
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
	
	public String getRecoveryTotalAmount() {
		return this.recoveryTotalAmount;
	}
	
	public void setRecoveryTotalAmount(String recoveryTotalAmount) {
		this.recoveryTotalAmount = recoveryTotalAmount;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
