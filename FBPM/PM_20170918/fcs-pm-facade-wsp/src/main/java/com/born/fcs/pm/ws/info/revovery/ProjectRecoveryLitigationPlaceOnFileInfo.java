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
 * 追偿跟踪表 - 诉讼-立案
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationPlaceOnFileInfo extends ProjectRecoveryLitigationBaseInfo {
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
	 * 立案时间
	 */
	private Date placeOnFileTime;
	
	/**
	 * 缴费时间
	 */
	private Date payTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	
	/*** 诉讼-立案-诉状-附件 **/
	private String recoveryLitigationPlaceOnFilePetitionUrl;
	/*** 诉讼-立案-诉状-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPlaceOnFilePetition;
	
	/*** 诉讼-立案-证据清单及证据-附件 **/
	private String recoveryLitigationPlaceOnFileEvidenceUrl;
	/*** 诉讼-立案-证据清单及证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileEvidence;
	
	/*** 诉讼-立案-案件受理通知书-附件 **/
	private String recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	/*** 诉讼-立案-案件受理通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileAcceptNotice;
	
	/*** 诉讼-立案-缴费通知书-附件 **/
	private String recoveryLitigationPlaceOnFilePayNoticeUrl;
	/*** 诉讼-立案-缴费通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPlaceOnFilePayNotice;
	
	/*** 诉讼-立案-其他-附件 **/
	private String recoveryLitigationPlaceOnFileOtherUrl;
	/*** 诉讼-立案-其他-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileOther;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationPlaceOnFilePetitionUrl() {
		return this.recoveryLitigationPlaceOnFilePetitionUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFilePetitionUrl(String recoveryLitigationPlaceOnFilePetitionUrl) {
		this.recoveryLitigationPlaceOnFilePetitionUrl = recoveryLitigationPlaceOnFilePetitionUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPlaceOnFilePetition() {
		return this.recoveryLitigationPlaceOnFilePetition;
	}
	
	public void setRecoveryLitigationPlaceOnFilePetition(	List<CommonAttachmentInfo> recoveryLitigationPlaceOnFilePetition) {
		this.recoveryLitigationPlaceOnFilePetition = recoveryLitigationPlaceOnFilePetition;
	}
	
	public String getRecoveryLitigationPlaceOnFileEvidenceUrl() {
		return this.recoveryLitigationPlaceOnFileEvidenceUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileEvidenceUrl(String recoveryLitigationPlaceOnFileEvidenceUrl) {
		this.recoveryLitigationPlaceOnFileEvidenceUrl = recoveryLitigationPlaceOnFileEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPlaceOnFileEvidence() {
		return this.recoveryLitigationPlaceOnFileEvidence;
	}
	
	public void setRecoveryLitigationPlaceOnFileEvidence(	List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileEvidence) {
		this.recoveryLitigationPlaceOnFileEvidence = recoveryLitigationPlaceOnFileEvidence;
	}
	
	public String getRecoveryLitigationPlaceOnFileAcceptNoticeUrl() {
		return this.recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileAcceptNoticeUrl(String recoveryLitigationPlaceOnFileAcceptNoticeUrl) {
		this.recoveryLitigationPlaceOnFileAcceptNoticeUrl = recoveryLitigationPlaceOnFileAcceptNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPlaceOnFileAcceptNotice() {
		return this.recoveryLitigationPlaceOnFileAcceptNotice;
	}
	
	public void setRecoveryLitigationPlaceOnFileAcceptNotice(	List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileAcceptNotice) {
		this.recoveryLitigationPlaceOnFileAcceptNotice = recoveryLitigationPlaceOnFileAcceptNotice;
	}
	
	public String getRecoveryLitigationPlaceOnFilePayNoticeUrl() {
		return this.recoveryLitigationPlaceOnFilePayNoticeUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFilePayNoticeUrl(	String recoveryLitigationPlaceOnFilePayNoticeUrl) {
		this.recoveryLitigationPlaceOnFilePayNoticeUrl = recoveryLitigationPlaceOnFilePayNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPlaceOnFilePayNotice() {
		return this.recoveryLitigationPlaceOnFilePayNotice;
	}
	
	public void setRecoveryLitigationPlaceOnFilePayNotice(	List<CommonAttachmentInfo> recoveryLitigationPlaceOnFilePayNotice) {
		this.recoveryLitigationPlaceOnFilePayNotice = recoveryLitigationPlaceOnFilePayNotice;
	}
	
	public String getRecoveryLitigationPlaceOnFileOtherUrl() {
		return this.recoveryLitigationPlaceOnFileOtherUrl;
	}
	
	public void setRecoveryLitigationPlaceOnFileOtherUrl(	String recoveryLitigationPlaceOnFileOtherUrl) {
		this.recoveryLitigationPlaceOnFileOtherUrl = recoveryLitigationPlaceOnFileOtherUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationPlaceOnFileOther() {
		return this.recoveryLitigationPlaceOnFileOther;
	}
	
	public void setRecoveryLitigationPlaceOnFileOther(	List<CommonAttachmentInfo> recoveryLitigationPlaceOnFileOther) {
		this.recoveryLitigationPlaceOnFileOther = recoveryLitigationPlaceOnFileOther;
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
