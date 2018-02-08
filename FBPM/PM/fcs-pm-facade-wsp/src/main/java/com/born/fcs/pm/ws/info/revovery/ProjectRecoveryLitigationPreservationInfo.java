/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 追偿跟踪表 - 诉讼-诉讼保全
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationPreservationInfo extends ProjectRecoveryLitigationBaseInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
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
	 * 提交申请时间
	 */
	private Date applyTime;
	
	/**
	 * 缴费时间
	 */
	private Date payTime;
	
	/**
	 * 诉讼担保方式
	 */
	private String litigationGuaranteeType;
	
	/**
	 * 保全时间
	 */
	private Date preservationTime;
	
	// 附件 开始 
	/*** 诉讼-诉讼保全-保全裁定书-附件 **/
	private String recoveryLitigationPreservationWrittenVerdictUrl;
	/*** 诉讼-诉讼保全-保全裁定书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPreservationWrittenVerdict;
	
	/*** 诉讼-诉讼保全-协助执行通知书-附件 **/
	private String recoveryLitigationPreservationExecutionNoticeUrl;
	/*** 诉讼-诉讼保全-协助执行通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPreservationExecutionNotice;
	
	/*** 诉讼-诉讼保全-送达回执-附件 **/
	private String recoveryLitigationPreservationDeliveryReceiptUrl;
	/*** 诉讼-诉讼保全-送达回执-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPreservationDeliveryReceipt;
	
	/*** 诉讼-诉讼保全-其他-附件 **/
	private String recoveryLitigationPreservationOtherUrl;
	/*** 诉讼-诉讼保全-其他-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPreservationOther;
	
	// 添加子表 
	
	/** 追偿跟踪表 - 诉讼-诉前保全-保全措施 */
	private List<ProjectRecoveryLitigationBeforePreservationPrecautionInfo> projectRecoveryLitigationBeforePreservationPrecautionInfos;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<ProjectRecoveryLitigationBeforePreservationPrecautionInfo> getProjectRecoveryLitigationBeforePreservationPrecautionInfos() {
		return this.projectRecoveryLitigationBeforePreservationPrecautionInfos;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationPrecautionInfos(	List<ProjectRecoveryLitigationBeforePreservationPrecautionInfo> projectRecoveryLitigationBeforePreservationPrecautionInfos) {
		this.projectRecoveryLitigationBeforePreservationPrecautionInfos = projectRecoveryLitigationBeforePreservationPrecautionInfos;
	}
	
	public String getRecoveryLitigationPreservationWrittenVerdictUrl() {
		return this.recoveryLitigationPreservationWrittenVerdictUrl;
	}
	
	public void setRecoveryLitigationPreservationWrittenVerdictUrl(	String recoveryLitigationPreservationWrittenVerdictUrl) {
		this.recoveryLitigationPreservationWrittenVerdictUrl = recoveryLitigationPreservationWrittenVerdictUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPreservationWrittenVerdict() {
		return this.recoveryLitigationPreservationWrittenVerdict;
	}
	
	public void setRecoveryLitigationPreservationWrittenVerdict(List<CommonAttachmentInfo> recoveryLitigationPreservationWrittenVerdict) {
		this.recoveryLitigationPreservationWrittenVerdict = recoveryLitigationPreservationWrittenVerdict;
	}
	
	public String getRecoveryLitigationPreservationExecutionNoticeUrl() {
		return this.recoveryLitigationPreservationExecutionNoticeUrl;
	}
	
	public void setRecoveryLitigationPreservationExecutionNoticeUrl(String recoveryLitigationPreservationExecutionNoticeUrl) {
		this.recoveryLitigationPreservationExecutionNoticeUrl = recoveryLitigationPreservationExecutionNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPreservationExecutionNotice() {
		return this.recoveryLitigationPreservationExecutionNotice;
	}
	
	public void setRecoveryLitigationPreservationExecutionNotice(	List<CommonAttachmentInfo> recoveryLitigationPreservationExecutionNotice) {
		this.recoveryLitigationPreservationExecutionNotice = recoveryLitigationPreservationExecutionNotice;
	}
	
	public String getRecoveryLitigationPreservationDeliveryReceiptUrl() {
		return this.recoveryLitigationPreservationDeliveryReceiptUrl;
	}
	
	public void setRecoveryLitigationPreservationDeliveryReceiptUrl(String recoveryLitigationPreservationDeliveryReceiptUrl) {
		this.recoveryLitigationPreservationDeliveryReceiptUrl = recoveryLitigationPreservationDeliveryReceiptUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPreservationDeliveryReceipt() {
		return this.recoveryLitigationPreservationDeliveryReceipt;
	}
	
	public void setRecoveryLitigationPreservationDeliveryReceipt(	List<CommonAttachmentInfo> recoveryLitigationPreservationDeliveryReceipt) {
		this.recoveryLitigationPreservationDeliveryReceipt = recoveryLitigationPreservationDeliveryReceipt;
	}
	
	public String getRecoveryLitigationPreservationOtherUrl() {
		return this.recoveryLitigationPreservationOtherUrl;
	}
	
	public void setRecoveryLitigationPreservationOtherUrl(	String recoveryLitigationPreservationOtherUrl) {
		this.recoveryLitigationPreservationOtherUrl = recoveryLitigationPreservationOtherUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPreservationOther() {
		return this.recoveryLitigationPreservationOther;
	}
	
	public void setRecoveryLitigationPreservationOther(	List<CommonAttachmentInfo> recoveryLitigationPreservationOther) {
		this.recoveryLitigationPreservationOther = recoveryLitigationPreservationOther;
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
	
	public Date getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Date getPayTime() {
		return payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getLitigationGuaranteeType() {
		return this.litigationGuaranteeType;
	}
	
	public void setLitigationGuaranteeType(String litigationGuaranteeType) {
		this.litigationGuaranteeType = litigationGuaranteeType;
	}
	
	public Date getPreservationTime() {
		return preservationTime;
	}
	
	public void setPreservationTime(Date preservationTime) {
		this.preservationTime = preservationTime;
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
