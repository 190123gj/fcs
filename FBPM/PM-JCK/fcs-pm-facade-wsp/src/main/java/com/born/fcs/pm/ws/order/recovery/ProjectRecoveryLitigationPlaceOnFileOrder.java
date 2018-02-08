/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-立案
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationPlaceOnFileOrder extends ProcessOrder {
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
	 * 立案时间 Date
	 */
	private String placeOnFileTime;
	
	/**
	 * 缴费时间 Date
	 */
	private String payTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	
	/*** 诉讼-立案-诉状-附件 **/
	private String recoveryLitigationPlaceOnFilePetitionUrl;
	/*** 诉讼-立案-证据清单及证据-附件 **/
	private String recoveryLitigationPlaceOnFileEvidenceUrl;
	/*** 诉讼-立案-案件受理通知书-附件 **/
	private String recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	/*** 诉讼-立案-缴费通知书-附件 **/
	private String recoveryLitigationPlaceOnFilePayNoticeUrl;
	/*** 诉讼-立案-其他-附件 **/
	private String recoveryLitigationPlaceOnFileOtherUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationPlaceOnFilePetitionUrl() {
		return this.recoveryLitigationPlaceOnFilePetitionUrl;
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
	
	public void setRecoveryLitigationPlaceOnFilePetitionUrl(String recoveryLitigationPlaceOnFilePetitionUrl) {
		this.recoveryLitigationPlaceOnFilePetitionUrl = recoveryLitigationPlaceOnFilePetitionUrl;
	}
	
	public String getRecoveryLitigationPlaceOnFileEvidenceUrl() {
		return this.recoveryLitigationPlaceOnFileEvidenceUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileEvidenceUrl(String recoveryLitigationPlaceOnFileEvidenceUrl) {
		this.recoveryLitigationPlaceOnFileEvidenceUrl = recoveryLitigationPlaceOnFileEvidenceUrl;
	}
	
	public String getRecoveryLitigationPlaceOnFileAcceptNoticeUrl() {
		return this.recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileAcceptNoticeUrl(String recoveryLitigationPlaceOnFileAcceptNoticeUrl) {
		this.recoveryLitigationPlaceOnFileAcceptNoticeUrl = recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	}
	
	public String getRecoveryLitigationPlaceOnFilePayNoticeUrl() {
		return this.recoveryLitigationPlaceOnFilePayNoticeUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFilePayNoticeUrl(	String recoveryLitigationPlaceOnFilePayNoticeUrl) {
		this.recoveryLitigationPlaceOnFilePayNoticeUrl = recoveryLitigationPlaceOnFilePayNoticeUrl;
	}
	
	public String getRecoveryLitigationPlaceOnFileOtherUrl() {
		return this.recoveryLitigationPlaceOnFileOtherUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileOtherUrl(	String recoveryLitigationPlaceOnFileOtherUrl) {
		this.recoveryLitigationPlaceOnFileOtherUrl = recoveryLitigationPlaceOnFileOtherUrl;
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
