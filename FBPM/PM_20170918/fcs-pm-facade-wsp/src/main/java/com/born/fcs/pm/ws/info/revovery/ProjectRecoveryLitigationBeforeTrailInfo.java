/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 追偿跟踪表 - 诉讼-庭前准备
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationBeforeTrailInfo extends ProjectRecoveryLitigationBaseInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 开庭时间
	 */
	private Date openingTime;
	
	/**
	 * 公告时间
	 */
	private Date noticeTime;
	
	/**
	 * 文书送达时间
	 */
	private Date clerkArrivedTime;
	
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
	
	/** 是否停止通知 */
	private BooleanEnum endNotice;
	
	// 附件开始
	/*** 诉讼-庭前准备-开庭时间-附件 **/
	private String recoveryLitigationBeforeTrailOpeningTimeUrl;
	/*** 诉讼-庭前准备-开庭时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailOpeningTime;
	
	/*** 诉讼-庭前准备-公告时间-附件 **/
	private String recoveryLitigationBeforeTrailNoticeTimeUrl;
	/*** 诉讼-庭前准备-公告时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailNoticeTime;
	
	/*** 诉讼-庭前准备-文书送达时间-附件 **/
	private String recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	/*** 诉讼-庭前准备-文书送达时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailClerkArrivedTime;
	
	/*** 诉讼-庭前准备-管辖异议-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	/*** 诉讼-庭前准备-管辖异议-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjection;
	
	/*** 诉讼-庭前准备-管辖异议裁定-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	/*** 诉讼-庭前准备-管辖异议裁定-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionJudgment;
	
	/*** 诉讼-庭前准备-管辖异议上诉-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	/*** 诉讼-庭前准备-管辖异议上诉-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionAppeal;
	
	/*** 诉讼-庭前准备-管辖异议二审裁定-附件 **/
	private String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	/*** 诉讼-庭前准备-管辖异议二审裁定-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond;
	
	/*** 诉讼-庭前准备-证据交换-附件 **/
	private String recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	/*** 诉讼-庭前准备-证据交换-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailEvidenceExchange;
	
	/*** 诉讼-庭前准备-鉴定申请-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalApplyUrl;
	/*** 诉讼-庭前准备-鉴定申请-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalApply;
	
	/*** 诉讼-庭前准备-鉴定材料-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	/*** 诉讼-庭前准备-鉴定材料-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalMaterial;
	
	/*** 诉讼-庭前准备-鉴定费用-附件 **/
	private String recoveryLitigationBeforeTrailAppraisalAmountUrl;
	/*** 诉讼-庭前准备-鉴定费用-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalAmount;
	
	/*** 诉讼-庭前准备-申请调查取证-附件 **/
	private String recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	/*** 诉讼-庭前准备-申请调查取证-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailInvestigatingApply;
	
	/*** 诉讼-庭前准备-申请证人出庭-附件 **/
	private String recoveryLitigationBeforeTrailWitnessesApplyUrl;
	/*** 诉讼-庭前准备-申请证人出庭-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailWitnessesApply;
	
	/*** 诉讼-庭前准备-增加诉讼请求申请-附件 **/
	private String recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
	/*** 诉讼-庭前准备-增加诉讼请求申请-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailIncreaseLitigationApply;
	
	/*** 诉讼-庭前准备-memo附件-附件 **/
	private String recoveryLitigationBeforeTrailMemoUrl;
	/*** 诉讼-庭前准备-memo附件-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationBeforeTrailMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public BooleanEnum getEndNotice() {
		return this.endNotice;
	}
	
	public void setEndNotice(BooleanEnum endNotice) {
		this.endNotice = endNotice;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationBeforeTrailMemoUrl() {
		return this.recoveryLitigationBeforeTrailMemoUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailMemoUrl(String recoveryLitigationBeforeTrailMemoUrl) {
		this.recoveryLitigationBeforeTrailMemoUrl = recoveryLitigationBeforeTrailMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailMemo() {
		return this.recoveryLitigationBeforeTrailMemo;
	}
	
	public void setRecoveryLitigationBeforeTrailMemo(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailMemo) {
		this.recoveryLitigationBeforeTrailMemo = recoveryLitigationBeforeTrailMemo;
	}
	
	public String getRecoveryLitigationBeforeTrailOpeningTimeUrl() {
		return this.recoveryLitigationBeforeTrailOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailOpeningTimeUrl(	String recoveryLitigationBeforeTrailOpeningTimeUrl) {
		this.recoveryLitigationBeforeTrailOpeningTimeUrl = recoveryLitigationBeforeTrailOpeningTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailOpeningTime() {
		return this.recoveryLitigationBeforeTrailOpeningTime;
	}
	
	public void setRecoveryLitigationBeforeTrailOpeningTime(List<CommonAttachmentInfo> recoveryLitigationBeforeTrailOpeningTime) {
		this.recoveryLitigationBeforeTrailOpeningTime = recoveryLitigationBeforeTrailOpeningTime;
	}
	
	public String getRecoveryLitigationBeforeTrailNoticeTimeUrl() {
		return this.recoveryLitigationBeforeTrailNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailNoticeTimeUrl(	String recoveryLitigationBeforeTrailNoticeTimeUrl) {
		this.recoveryLitigationBeforeTrailNoticeTimeUrl = recoveryLitigationBeforeTrailNoticeTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailNoticeTime() {
		return this.recoveryLitigationBeforeTrailNoticeTime;
	}
	
	public void setRecoveryLitigationBeforeTrailNoticeTime(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailNoticeTime) {
		this.recoveryLitigationBeforeTrailNoticeTime = recoveryLitigationBeforeTrailNoticeTime;
	}
	
	public String getRecoveryLitigationBeforeTrailClerkArrivedTimeUrl() {
		return this.recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailClerkArrivedTimeUrl(String recoveryLitigationBeforeTrailClerkArrivedTimeUrl) {
		this.recoveryLitigationBeforeTrailClerkArrivedTimeUrl = recoveryLitigationBeforeTrailClerkArrivedTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailClerkArrivedTime() {
		return this.recoveryLitigationBeforeTrailClerkArrivedTime;
	}
	
	public void setRecoveryLitigationBeforeTrailClerkArrivedTime(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailClerkArrivedTime) {
		this.recoveryLitigationBeforeTrailClerkArrivedTime = recoveryLitigationBeforeTrailClerkArrivedTime;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionUrl = recoveryLitigationBeforeTrailJurisdictionObjectionUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailJurisdictionObjection() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjection;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjection(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjection) {
		this.recoveryLitigationBeforeTrailJurisdictionObjection = recoveryLitigationBeforeTrailJurisdictionObjection;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl = recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgment() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgment;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgment(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionJudgment) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgment = recoveryLitigationBeforeTrailJurisdictionObjectionJudgment;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl = recoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailJurisdictionObjectionAppeal() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionAppeal;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionAppeal(List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionAppeal) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionAppeal = recoveryLitigationBeforeTrailJurisdictionObjectionAppeal;
	}
	
	public String getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl(	String recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl = recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond() {
		return this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond;
	}
	
	public void setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond(List<CommonAttachmentInfo> recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond) {
		this.recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond = recoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond;
	}
	
	public String getRecoveryLitigationBeforeTrailEvidenceExchangeUrl() {
		return this.recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailEvidenceExchangeUrl(String recoveryLitigationBeforeTrailEvidenceExchangeUrl) {
		this.recoveryLitigationBeforeTrailEvidenceExchangeUrl = recoveryLitigationBeforeTrailEvidenceExchangeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailEvidenceExchange() {
		return this.recoveryLitigationBeforeTrailEvidenceExchange;
	}
	
	public void setRecoveryLitigationBeforeTrailEvidenceExchange(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailEvidenceExchange) {
		this.recoveryLitigationBeforeTrailEvidenceExchange = recoveryLitigationBeforeTrailEvidenceExchange;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalApplyUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalApplyUrl(	String recoveryLitigationBeforeTrailAppraisalApplyUrl) {
		this.recoveryLitigationBeforeTrailAppraisalApplyUrl = recoveryLitigationBeforeTrailAppraisalApplyUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailAppraisalApply() {
		return this.recoveryLitigationBeforeTrailAppraisalApply;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalApply(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalApply) {
		this.recoveryLitigationBeforeTrailAppraisalApply = recoveryLitigationBeforeTrailAppraisalApply;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalMaterialUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalMaterialUrl(	String recoveryLitigationBeforeTrailAppraisalMaterialUrl) {
		this.recoveryLitigationBeforeTrailAppraisalMaterialUrl = recoveryLitigationBeforeTrailAppraisalMaterialUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailAppraisalMaterial() {
		return this.recoveryLitigationBeforeTrailAppraisalMaterial;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalMaterial(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalMaterial) {
		this.recoveryLitigationBeforeTrailAppraisalMaterial = recoveryLitigationBeforeTrailAppraisalMaterial;
	}
	
	public String getRecoveryLitigationBeforeTrailAppraisalAmountUrl() {
		return this.recoveryLitigationBeforeTrailAppraisalAmountUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalAmountUrl(	String recoveryLitigationBeforeTrailAppraisalAmountUrl) {
		this.recoveryLitigationBeforeTrailAppraisalAmountUrl = recoveryLitigationBeforeTrailAppraisalAmountUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailAppraisalAmount() {
		return this.recoveryLitigationBeforeTrailAppraisalAmount;
	}
	
	public void setRecoveryLitigationBeforeTrailAppraisalAmount(List<CommonAttachmentInfo> recoveryLitigationBeforeTrailAppraisalAmount) {
		this.recoveryLitigationBeforeTrailAppraisalAmount = recoveryLitigationBeforeTrailAppraisalAmount;
	}
	
	public String getRecoveryLitigationBeforeTrailInvestigatingApplyUrl() {
		return this.recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailInvestigatingApplyUrl(	String recoveryLitigationBeforeTrailInvestigatingApplyUrl) {
		this.recoveryLitigationBeforeTrailInvestigatingApplyUrl = recoveryLitigationBeforeTrailInvestigatingApplyUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailInvestigatingApply() {
		return this.recoveryLitigationBeforeTrailInvestigatingApply;
	}
	
	public void setRecoveryLitigationBeforeTrailInvestigatingApply(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailInvestigatingApply) {
		this.recoveryLitigationBeforeTrailInvestigatingApply = recoveryLitigationBeforeTrailInvestigatingApply;
	}
	
	public String getRecoveryLitigationBeforeTrailWitnessesApplyUrl() {
		return this.recoveryLitigationBeforeTrailWitnessesApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailWitnessesApplyUrl(	String recoveryLitigationBeforeTrailWitnessesApplyUrl) {
		this.recoveryLitigationBeforeTrailWitnessesApplyUrl = recoveryLitigationBeforeTrailWitnessesApplyUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailWitnessesApply() {
		return this.recoveryLitigationBeforeTrailWitnessesApply;
	}
	
	public void setRecoveryLitigationBeforeTrailWitnessesApply(	List<CommonAttachmentInfo> recoveryLitigationBeforeTrailWitnessesApply) {
		this.recoveryLitigationBeforeTrailWitnessesApply = recoveryLitigationBeforeTrailWitnessesApply;
	}
	
	public String getRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl() {
		return this.recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
	}
	
	public void setRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl(	String recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl) {
		this.recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl = recoveryLitigationBeforeTrailIncreaseLitigationApplyUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationBeforeTrailIncreaseLitigationApply() {
		return this.recoveryLitigationBeforeTrailIncreaseLitigationApply;
	}
	
	public void setRecoveryLitigationBeforeTrailIncreaseLitigationApply(List<CommonAttachmentInfo> recoveryLitigationBeforeTrailIncreaseLitigationApply) {
		this.recoveryLitigationBeforeTrailIncreaseLitigationApply = recoveryLitigationBeforeTrailIncreaseLitigationApply;
	}
	
	public Date getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	
	public Date getNoticeTime() {
		return noticeTime;
	}
	
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public Date getClerkArrivedTime() {
		return clerkArrivedTime;
	}
	
	public void setClerkArrivedTime(Date clerkArrivedTime) {
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
