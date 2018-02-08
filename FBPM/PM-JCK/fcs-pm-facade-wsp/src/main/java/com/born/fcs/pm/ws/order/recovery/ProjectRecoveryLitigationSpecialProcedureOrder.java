/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-实现担保物权特别程序
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationSpecialProcedureOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主
	 */
	private Long projectRecoveryId;
	
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
	 * 立案时间 Date
	 */
	private String placeOnFileTime;
	
	/**
	 * 缴费时间 Date
	 */
	private String payTime;
	
	/**
	 * 裁判文书
	 */
	private String refereeClerk;
	
	/**
	 * 裁决时间 Date
	 */
	private String refereeTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	
	/*** 诉讼-实现担保物权特别程序-裁判文书-附件 **/
	private String recoveryLitigationSpecialProcedureRefereeClerkUrl;
	/*** 诉讼-实现担保物权特别程序-诉状-附件 **/
	private String recoveryLitigationSpecialProcedurePetitionUrl;
	/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件 **/
	private String recoveryLitigationSpecialProcedureEvidenceUrl;
	/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件 **/
	private String recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	/*** 诉讼-实现担保物权特别程序-缴费通知书-附件 **/
	private String recoveryLitigationSpecialProcedurePayNoticeUrl;
	/*** 诉讼-实现担保物权特别程序-备注-附件 **/
	private String recoveryLitigationSpecialProcedureMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationSpecialProcedureRefereeClerkUrl() {
		return this.recoveryLitigationSpecialProcedureRefereeClerkUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureRefereeClerkUrl(	String recoveryLitigationSpecialProcedureRefereeClerkUrl) {
		this.recoveryLitigationSpecialProcedureRefereeClerkUrl = recoveryLitigationSpecialProcedureRefereeClerkUrl;
	}
	
	public String getRecoveryLitigationSpecialProcedurePetitionUrl() {
		return this.recoveryLitigationSpecialProcedurePetitionUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedurePetitionUrl(	String recoveryLitigationSpecialProcedurePetitionUrl) {
		this.recoveryLitigationSpecialProcedurePetitionUrl = recoveryLitigationSpecialProcedurePetitionUrl;
	}
	
	public String getRecoveryLitigationSpecialProcedureEvidenceUrl() {
		return this.recoveryLitigationSpecialProcedureEvidenceUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureEvidenceUrl(	String recoveryLitigationSpecialProcedureEvidenceUrl) {
		this.recoveryLitigationSpecialProcedureEvidenceUrl = recoveryLitigationSpecialProcedureEvidenceUrl;
	}
	
	public String getRecoveryLitigationSpecialProcedureAcceptNoticeUrl() {
		return this.recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureAcceptNoticeUrl(	String recoveryLitigationSpecialProcedureAcceptNoticeUrl) {
		this.recoveryLitigationSpecialProcedureAcceptNoticeUrl = recoveryLitigationSpecialProcedureAcceptNoticeUrl;
	}
	
	public String getRecoveryLitigationSpecialProcedurePayNoticeUrl() {
		return this.recoveryLitigationSpecialProcedurePayNoticeUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedurePayNoticeUrl(	String recoveryLitigationSpecialProcedurePayNoticeUrl) {
		this.recoveryLitigationSpecialProcedurePayNoticeUrl = recoveryLitigationSpecialProcedurePayNoticeUrl;
	}
	
	public String getRecoveryLitigationSpecialProcedureMemoUrl() {
		return this.recoveryLitigationSpecialProcedureMemoUrl;
	}
	
	public void setRecoveryLitigationSpecialProcedureMemoUrl(	String recoveryLitigationSpecialProcedureMemoUrl) {
		this.recoveryLitigationSpecialProcedureMemoUrl = recoveryLitigationSpecialProcedureMemoUrl;
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
	
	public String getRefereeClerk() {
		return refereeClerk;
	}
	
	public void setRefereeClerk(String refereeClerk) {
		this.refereeClerk = refereeClerk;
	}
	
	public String getPlaceOnFileTime() {
		return this.placeOnFileTime;
	}
	
	public void setPlaceOnFileTime(String placeOnFileTime) {
		this.placeOnFileTime = placeOnFileTime;
	}
	
	public String getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getRefereeTime() {
		return this.refereeTime;
	}
	
	public void setRefereeTime(String refereeTime) {
		this.refereeTime = refereeTime;
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
