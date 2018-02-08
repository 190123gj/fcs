/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-庭前准备
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationBeforeTrailOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 开庭时间 Date
	 */
	private String openingTime;
	
	/**
	 * 公告时间 Date
	 */
	private String noticeTime;
	
	/**
	 * 文书送达时间 Date
	 */
	private String clerkArrivedTime;
	
	/**
	 * 管辖异议
	 */
	private String jurisdictionObjection;
	
	/**
	 * 管辖异议裁定
	 */
	private String jurisdictionObjectionJudgment;
	
	/**
	 * 管辖异议上诉
	 */
	private String jurisdictionObjectionAppeal;
	
	/**
	 * 管辖异议二审裁定
	 */
	private String jurisdictionObjectionJudgmentSecond;
	
	/**
	 * 证据交换
	 */
	private String evidenceExchange;
	
	/**
	 * 鉴定申请
	 */
	private String appraisalApply;
	
	/**
	 * 鉴定材料
	 */
	private String appraisalMaterial;
	
	/**
	 * 鉴定费用
	 */
	private String appraisalAmount;
	
	/**
	 * 申请调查取证
	 */
	private String investigatingApply;
	
	/**
	 * 申请证人出庭
	 */
	private String witnessesApply;
	
	/**
	 * 增加诉讼请求申请
	 */
	private String increaseLitigationApply;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/** 诉讼顺序 */
	private int litigationIndex;
	
	/** 是否停止通知 */
	private BooleanEnum endNotice;
	
	// 附件开始
	/*** 诉讼-庭前准备-开庭时间-附件 **/
	private String recoveryLitigationBeforeTrailOpeningTimeUrl;
	/*** 诉讼-庭前准备-公告时间-附件 **/
	private String recoveryLitigationBeforeTrailNoticeTimeUrl;
	/*** 诉讼-庭前准备-文书送达时间-附件 **/
	private String recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	/*** 诉讼-庭前准备-管辖异议-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	/*** 诉讼-庭前准备-管辖异议裁定-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	/*** 诉讼-庭前准备-管辖异议上诉-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	/*** 诉讼-庭前准备-管辖异议二审裁定-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	/*** 诉讼-庭前准备-证据交换-附件 **/
	private String recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	/*** 诉讼-庭前准备-鉴定申请-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalApplyUrl;
	/*** 诉讼-庭前准备-鉴定材料-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	/*** 诉讼-庭前准备-鉴定费用-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalAmountUrl;
	/*** 诉讼-庭前准备-申请调查取证-附件 **/
	private String recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	/*** 诉讼-庭前准备-申请证人出庭-附件 **/
	private String recoveryLitigationBeforeTrailWitnessesApplyUrl;
	/*** 诉讼-庭前准备-增加诉讼请求申请-附件 **/
	private String recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
	
	/*** 诉讼-庭前准备-memo附件-附件 **/
	private String recoveryLitigationBeforeTrailMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationBeforeTrailMemoUrl() {
		return this.recoveryLitigationBeforeTrailMemoUrl;
	}
	
	public BooleanEnum getEndNotice() {
		return this.endNotice;
	}
	
	public void setEndNotice(BooleanEnum endNotice) {
		this.endNotice = endNotice;
	}
	
	public void setRecoveryLitigationBeforeTrailMemoUrl(String recoveryLitigationBeforeTrailMemoUrl) {
		this.recoveryLitigationBeforeTrailMemoUrl = recoveryLitigationBeforeTrailMemoUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailOpeningTimeUrl() {
		return this.recoveryLitigationBeforeTrailOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailOpeningTimeUrl(	String recoveryLitigationBeforeTrailOpeningTimeUrl) {
		this.recoveryLitigationBeforeTrailOpeningTimeUrl = recoveryLitigationBeforeTrailOpeningTimeUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailNoticeTimeUrl() {
		return this.recoveryLitigationBeforeTrailNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailNoticeTimeUrl(	String recoveryLitigationBeforeTrailNoticeTimeUrl) {
		this.recoveryLitigationBeforeTrailNoticeTimeUrl = recoveryLitigationBeforeTrailNoticeTimeUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailClerkArrivedTimeUrl() {
		return this.recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailClerkArrivedTimeUrl(String recoveryLitigationBeforeTrailClerkArrivedTimeUrl) {
		this.recoveryLitigationBeforeTrailClerkArrivedTimeUrl = recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionUrl = recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl = recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl = recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl = recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailEvidenceExchangeUrl() {
		return this.recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailEvidenceExchangeUrl(String recoveryLitigationBeforeTrailEvidenceExchangeUrl) {
		this.recoveryLitigationBeforeTrailEvidenceExchangeUrl = recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalApplyUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalApplyUrl(	String recoveryLitigationBeforeTrailAppraisalApplyUrl) {
		this.recoveryLitigationBeforeTrailAppraisalApplyUrl = recoveryLitigationBeforeTrailAppraisalApplyUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalMaterialUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalMaterialUrl(	String recoveryLitigationBeforeTrailAppraisalMaterialUrl) {
		this.recoveryLitigationBeforeTrailAppraisalMaterialUrl = recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalAmountUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalAmountUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalAmountUrl(	String recoveryLitigationBeforeTrailAppraisalAmountUrl) {
		this.recoveryLitigationBeforeTrailAppraisalAmountUrl = recoveryLitigationBeforeTrailAppraisalAmountUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailInvestigatingApplyUrl() {
		return this.recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailInvestigatingApplyUrl(	String recoveryLitigationBeforeTrailInvestigatingApplyUrl) {
		this.recoveryLitigationBeforeTrailInvestigatingApplyUrl = recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailWitnessesApplyUrl() {
		return this.recoveryLitigationBeforeTrailWitnessesApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailWitnessesApplyUrl(	String recoveryLitigationBeforeTrailWitnessesApplyUrl) {
		this.recoveryLitigationBeforeTrailWitnessesApplyUrl = recoveryLitigationBeforeTrailWitnessesApplyUrl;
	}
	
	public String getRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl() {
		return this.recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl(	String recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl) {
		this.recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl = recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
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
	
	public String getOpeningTime() {
		return this.openingTime;
	}
	
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	
	public String getNoticeTime() {
		return this.noticeTime;
	}
	
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public String getClerkArrivedTime() {
		return this.clerkArrivedTime;
	}
	
	public void setClerkArrivedTime(String clerkArrivedTime) {
		this.clerkArrivedTime = clerkArrivedTime;
	}
	
	public String getJurisdictionObjection() {
		return jurisdictionObjection;
	}
	
	public void setJurisdictionObjection(String jurisdictionObjection) {
		this.jurisdictionObjection = jurisdictionObjection;
	}
	
	public String getJurisdictionObjectionJudgment() {
		return jurisdictionObjectionJudgment;
	}
	
	public void setJurisdictionObjectionJudgment(String jurisdictionObjectionJudgment) {
		this.jurisdictionObjectionJudgment = jurisdictionObjectionJudgment;
	}
	
	public String getJurisdictionObjectionAppeal() {
		return jurisdictionObjectionAppeal;
	}
	
	public void setJurisdictionObjectionAppeal(String jurisdictionObjectionAppeal) {
		this.jurisdictionObjectionAppeal = jurisdictionObjectionAppeal;
	}
	
	public String getJurisdictionObjectionJudgmentSecond() {
		return jurisdictionObjectionJudgmentSecond;
	}
	
	public void setJurisdictionObjectionJudgmentSecond(String jurisdictionObjectionJudgmentSecond) {
		this.jurisdictionObjectionJudgmentSecond = jurisdictionObjectionJudgmentSecond;
	}
	
	public String getEvidenceExchange() {
		return evidenceExchange;
	}
	
	public void setEvidenceExchange(String evidenceExchange) {
		this.evidenceExchange = evidenceExchange;
	}
	
	public String getAppraisalApply() {
		return appraisalApply;
	}
	
	public void setAppraisalApply(String appraisalApply) {
		this.appraisalApply = appraisalApply;
	}
	
	public String getAppraisalMaterial() {
		return appraisalMaterial;
	}
	
	public void setAppraisalMaterial(String appraisalMaterial) {
		this.appraisalMaterial = appraisalMaterial;
	}
	
	public String getAppraisalAmount() {
		return appraisalAmount;
	}
	
	public void setAppraisalAmount(String appraisalAmount) {
		this.appraisalAmount = appraisalAmount;
	}
	
	public String getInvestigatingApply() {
		return investigatingApply;
	}
	
	public void setInvestigatingApply(String investigatingApply) {
		this.investigatingApply = investigatingApply;
	}
	
	public String getWitnessesApply() {
		return witnessesApply;
	}
	
	public void setWitnessesApply(String witnessesApply) {
		this.witnessesApply = witnessesApply;
	}
	
	public String getIncreaseLitigationApply() {
		return increaseLitigationApply;
	}
	
	public void setIncreaseLitigationApply(String increaseLitigationApply) {
		this.increaseLitigationApply = increaseLitigationApply;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public int getLitigationIndex() {
		return this.litigationIndex;
	}
	
	public void setLitigationIndex(int litigationIndex) {
		this.litigationIndex = litigationIndex;
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
