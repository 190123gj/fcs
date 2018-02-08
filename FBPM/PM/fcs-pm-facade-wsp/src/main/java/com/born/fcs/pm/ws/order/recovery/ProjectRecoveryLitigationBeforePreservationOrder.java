/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-诉前保全
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationBeforePreservationOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
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
	 * 提交申请时间 Date
	 */
	private String applyTime;
	
	/**
	 * 缴费时间 Date
	 */
	private String payTime;
	
	/**
	 * 诉讼担保方式
	 */
	private String litigationGuaranteeType;
	
	/**
	 * 保全时间 Date
	 */
	private String preservationTime;
	
	/** 诉讼顺序 */
	private int litigationIndex;
	
	// 附件 开始
	
	/*** 诉讼-诉前保全-保全裁定书-附件 **/
	private String recoveryLitigationBeforePreservationWrittenVerdictUrl;
	/*** 诉讼-诉前保全-协助执行通知书-附件 **/
	private String recoveryLitigationBeforePreservationExecutionNoticeUrl;
	/*** 诉讼-诉前保全-送达回执-附件 **/
	private String recoveryLitigationBeforePreservationDeliveryReceiptUrl;
	/*** 诉讼-诉前保全-其他-附件 **/
	private String recoveryLitigationBeforePreservationOtherUrl;
	
	// 添加子表 
	
	/** 追偿跟踪表 - 诉讼-诉前保全-保全措施 */
	private List<ProjectRecoveryLitigationBeforePreservationPrecautionOrder> projectRecoveryLitigationBeforePreservationPrecautionOrder;
	
	//========== getters and setters ==========
	
	public List<ProjectRecoveryLitigationBeforePreservationPrecautionOrder> getProjectRecoveryLitigationBeforePreservationPrecautionOrder() {
		return this.projectRecoveryLitigationBeforePreservationPrecautionOrder;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationPrecautionOrder(	List<ProjectRecoveryLitigationBeforePreservationPrecautionOrder> projectRecoveryLitigationBeforePreservationPrecautionOrder) {
		this.projectRecoveryLitigationBeforePreservationPrecautionOrder = projectRecoveryLitigationBeforePreservationPrecautionOrder;
	}
	
	public String getRecoveryLitigationBeforePreservationWrittenVerdictUrl() {
		return this.recoveryLitigationBeforePreservationWrittenVerdictUrl;
	}
	
	public void setRecoveryLitigationBeforePreservationWrittenVerdictUrl(	String recoveryLitigationBeforePreservationWrittenVerdictUrl) {
		this.recoveryLitigationBeforePreservationWrittenVerdictUrl = recoveryLitigationBeforePreservationWrittenVerdictUrl;
	}
	
	public String getRecoveryLitigationBeforePreservationExecutionNoticeUrl() {
		return this.recoveryLitigationBeforePreservationExecutionNoticeUrl;
	}
	
	public void setRecoveryLitigationBeforePreservationExecutionNoticeUrl(	String recoveryLitigationBeforePreservationExecutionNoticeUrl) {
		this.recoveryLitigationBeforePreservationExecutionNoticeUrl = recoveryLitigationBeforePreservationExecutionNoticeUrl;
	}
	
	public String getRecoveryLitigationBeforePreservationDeliveryReceiptUrl() {
		return this.recoveryLitigationBeforePreservationDeliveryReceiptUrl;
	}
	
	public void setRecoveryLitigationBeforePreservationDeliveryReceiptUrl(	String recoveryLitigationBeforePreservationDeliveryReceiptUrl) {
		this.recoveryLitigationBeforePreservationDeliveryReceiptUrl = recoveryLitigationBeforePreservationDeliveryReceiptUrl;
	}
	
	public String getRecoveryLitigationBeforePreservationOtherUrl() {
		return this.recoveryLitigationBeforePreservationOtherUrl;
	}
	
	public void setRecoveryLitigationBeforePreservationOtherUrl(String recoveryLitigationBeforePreservationOtherUrl) {
		this.recoveryLitigationBeforePreservationOtherUrl = recoveryLitigationBeforePreservationOtherUrl;
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
	
	public String getApplyTime() {
		return this.applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getLitigationGuaranteeType() {
		return this.litigationGuaranteeType;
	}
	
	public void setLitigationGuaranteeType(String litigationGuaranteeType) {
		this.litigationGuaranteeType = litigationGuaranteeType;
	}
	
	public String getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getPreservationTime() {
		return this.preservationTime;
	}
	
	public void setPreservationTime(String preservationTime) {
		this.preservationTime = preservationTime;
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
