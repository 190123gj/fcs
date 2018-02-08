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

/**
 * 追偿跟踪表 - 诉讼-实现担保物权特别程序
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationSpecialProcedureInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主
	 */
	private long projectRecoveryId;
	
	/**
	 * 受理法院
	 */
	private String acceptingCourt;
	
	/**
	 * 代理律所
	 */
	private String agentLawFirm;
	
	/**
	 * 承办律师
	 */
	private String agentAttorney;
	
	/**
	 * 承办法官
	 */
	private String agentJudge;
	
	/**
	 * 立案时间
	 */
	private Date placeOnFileTime;
	
	/**
	 * 缴费时间
	 */
	private Date payTime;
	
	/**
	 * 裁判文书
	 */
	private String refereeClerk;
	
	/**
	 * 裁决时间
	 */
	private Date refereeTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件 开始 
	
	// 附件 开始 
	
	/*** 诉讼-实现担保物权特别程序-裁判文书-附件 **/
	private String recoveryLitigationSpecialProcedureRefereeClerkUrl;
	/*** 诉讼-实现担保物权特别程序-裁判文书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureRefereeClerk;
	
	/*** 诉讼-实现担保物权特别程序-诉状-附件 **/
	private String recoveryLitigationSpecialProcedurePetitionUrl;
	/*** 诉讼-实现担保物权特别程序-诉状-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedurePetition;
	
	/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件 **/
	private String recoveryLitigationSpecialProcedureEvidenceUrl;
	/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureEvidence;
	
	/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件 **/
	private String recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureAcceptNotice;
	
	/*** 诉讼-实现担保物权特别程序-缴费通知书-附件 **/
	private String recoveryLitigationSpecialProcedurePayNoticeUrl;
	/*** 诉讼-实现担保物权特别程序-缴费通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedurePayNotice;
	
	/*** 诉讼-实现担保物权特别程序-备注-附件 **/
	private String recoveryLitigationSpecialProcedureMemoUrl;
	/*** 诉讼-实现担保物权特别程序-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationSpecialProcedureRefereeClerkUrl() {
		return this.recoveryLitigationSpecialProcedureRefereeClerkUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureRefereeClerkUrl(	String recoveryLitigationSpecialProcedureRefereeClerkUrl) {
		this.recoveryLitigationSpecialProcedureRefereeClerkUrl = recoveryLitigationSpecialProcedureRefereeClerkUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedureRefereeClerk() {
		return this.recoveryLitigationSpecialProcedureRefereeClerk;
	}
	
	public void setRecoveryLitigationSpecialProcedureRefereeClerk(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureRefereeClerk) {
		this.recoveryLitigationSpecialProcedureRefereeClerk = recoveryLitigationSpecialProcedureRefereeClerk;
	}
	
	public String getRecoveryLitigationSpecialProcedurePetitionUrl() {
		return this.recoveryLitigationSpecialProcedurePetitionUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedurePetitionUrl(	String recoveryLitigationSpecialProcedurePetitionUrl) {
		this.recoveryLitigationSpecialProcedurePetitionUrl = recoveryLitigationSpecialProcedurePetitionUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedurePetition() {
		return this.recoveryLitigationSpecialProcedurePetition;
	}
	
	public void setRecoveryLitigationSpecialProcedurePetition(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedurePetition) {
		this.recoveryLitigationSpecialProcedurePetition = recoveryLitigationSpecialProcedurePetition;
	}
	
	public String getRecoveryLitigationSpecialProcedureEvidenceUrl() {
		return this.recoveryLitigationSpecialProcedureEvidenceUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureEvidenceUrl(	String recoveryLitigationSpecialProcedureEvidenceUrl) {
		this.recoveryLitigationSpecialProcedureEvidenceUrl = recoveryLitigationSpecialProcedureEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedureEvidence() {
		return this.recoveryLitigationSpecialProcedureEvidence;
	}
	
	public void setRecoveryLitigationSpecialProcedureEvidence(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureEvidence) {
		this.recoveryLitigationSpecialProcedureEvidence = recoveryLitigationSpecialProcedureEvidence;
	}
	
	public String getRecoveryLitigationSpecialProcedureAcceptNoticeUrl() {
		return this.recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureAcceptNoticeUrl(	String recoveryLitigationSpecialProcedureAcceptNoticeUrl) {
		this.recoveryLitigationSpecialProcedureAcceptNoticeUrl = recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedureAcceptNotice() {
		return this.recoveryLitigationSpecialProcedureAcceptNotice;
	}
	
	public void setRecoveryLitigationSpecialProcedureAcceptNotice(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureAcceptNotice) {
		this.recoveryLitigationSpecialProcedureAcceptNotice = recoveryLitigationSpecialProcedureAcceptNotice;
	}
	
	public String getRecoveryLitigationSpecialProcedurePayNoticeUrl() {
		return this.recoveryLitigationSpecialProcedurePayNoticeUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedurePayNoticeUrl(	String recoveryLitigationSpecialProcedurePayNoticeUrl) {
		this.recoveryLitigationSpecialProcedurePayNoticeUrl = recoveryLitigationSpecialProcedurePayNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedurePayNotice() {
		return this.recoveryLitigationSpecialProcedurePayNotice;
	}
	
	public void setRecoveryLitigationSpecialProcedurePayNotice(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedurePayNotice) {
		this.recoveryLitigationSpecialProcedurePayNotice = recoveryLitigationSpecialProcedurePayNotice;
	}
	
	public String getRecoveryLitigationSpecialProcedureMemoUrl() {
		return this.recoveryLitigationSpecialProcedureMemoUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureMemoUrl(	String recoveryLitigationSpecialProcedureMemoUrl) {
		this.recoveryLitigationSpecialProcedureMemoUrl = recoveryLitigationSpecialProcedureMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSpecialProcedureMemo() {
		return this.recoveryLitigationSpecialProcedureMemo;
	}
	
	public void setRecoveryLitigationSpecialProcedureMemo(	List<CommonAttachmentInfo> recoveryLitigationSpecialProcedureMemo) {
		this.recoveryLitigationSpecialProcedureMemo = recoveryLitigationSpecialProcedureMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public String getAcceptingCourt() {
		return acceptingCourt;
	}
	
	public void setAcceptingCourt(String acceptingCourt) {
		this.acceptingCourt = acceptingCourt;
	}
	
	public String getAgentLawFirm() {
		return agentLawFirm;
	}
	
	public void setAgentLawFirm(String agentLawFirm) {
		this.agentLawFirm = agentLawFirm;
	}
	
	public String getAgentAttorney() {
		return agentAttorney;
	}
	
	public void setAgentAttorney(String agentAttorney) {
		this.agentAttorney = agentAttorney;
	}
	
	public String getAgentJudge() {
		return agentJudge;
	}
	
	public void setAgentJudge(String agentJudge) {
		this.agentJudge = agentJudge;
	}
	
	public Date getPlaceOnFileTime() {
		return placeOnFileTime;
	}
	
	public void setPlaceOnFileTime(Date placeOnFileTime) {
		this.placeOnFileTime = placeOnFileTime;
	}
	
	public Date getPayTime() {
		return payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getRefereeClerk() {
		return refereeClerk;
	}
	
	public void setRefereeClerk(String refereeClerk) {
		this.refereeClerk = refereeClerk;
	}
	
	public Date getRefereeTime() {
		return refereeTime;
	}
	
	public void setRefereeTime(Date refereeTime) {
		this.refereeTime = refereeTime;
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
