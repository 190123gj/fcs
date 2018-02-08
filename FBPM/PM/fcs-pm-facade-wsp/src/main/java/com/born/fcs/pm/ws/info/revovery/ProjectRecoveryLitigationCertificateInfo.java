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
 * 追偿跟踪表 - 诉讼-强制执行公证执行证书
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationCertificateInfo extends ProjectRecoveryLitigationBaseInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 公证机关
	 */
	private String notaryOrgan;
	
	/**
	 * 代理律所
	 */
	private String agentLawFirm;
	
	/**
	 * 承办律师
	 */
	private String agentAttorney;
	
	/**
	 * 公证员
	 */
	private String notarial;
	
	/**
	 * 申请时间
	 */
	private Date applyTime;
	
	/**
	 * 缴费时间
	 */
	private Date payTime;
	
	/**
	 * 执行证书
	 */
	private String certificate;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件  开始  
	
	/*** 诉讼-强制执行公证执行证书-执行证书-附件 **/
	private String recoveryLitigationCertificateNoUrl;
	/*** 诉讼-强制执行公证执行证书-执行证书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificateNo;
	
	/*** 诉讼-强制执行公证执行证书-申请书-附件 **/
	private String recoveryLitigationCertificateDeclarationUrl;
	/*** 诉讼-强制执行公证执行证书-申请书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificateDeclaration;
	
	/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件 **/
	private String recoveryLitigationCertificateEvidenceUrl;
	/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificateEvidence;
	
	/*** 诉讼-强制执行公证执行证书-缴费通知书-附件 **/
	private String recoveryLitigationCertificatePayNoticeUrl;
	/*** 诉讼-强制执行公证执行证书-缴费通知书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificatePayNotice;
	
	/*** 诉讼-强制执行公证执行证书-其他-附件 **/
	private String recoveryLitigationCertificateOtherUrl;
	/*** 诉讼-强制执行公证执行证书-其他-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificateOther;
	
	/*** 诉讼-强制执行公证执行证书-备注-附件 **/
	private String recoveryLitigationCertificateMemoUrl;
	/*** 诉讼-强制执行公证执行证书-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationCertificateMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationCertificateNoUrl() {
		return this.recoveryLitigationCertificateNoUrl;
	}
	
	public void setRecoveryLitigationCertificateNoUrl(String recoveryLitigationCertificateNoUrl) {
		this.recoveryLitigationCertificateNoUrl = recoveryLitigationCertificateNoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificateNo() {
		return this.recoveryLitigationCertificateNo;
	}
	
	public void setRecoveryLitigationCertificateNo(	List<CommonAttachmentInfo> recoveryLitigationCertificateNo) {
		this.recoveryLitigationCertificateNo = recoveryLitigationCertificateNo;
	}
	
	public String getRecoveryLitigationCertificateDeclarationUrl() {
		return this.recoveryLitigationCertificateDeclarationUrl;
	}
	
	public void setRecoveryLitigationCertificateDeclarationUrl(	String recoveryLitigationCertificateDeclarationUrl) {
		this.recoveryLitigationCertificateDeclarationUrl = recoveryLitigationCertificateDeclarationUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificateDeclaration() {
		return this.recoveryLitigationCertificateDeclaration;
	}
	
	public void setRecoveryLitigationCertificateDeclaration(List<CommonAttachmentInfo> recoveryLitigationCertificateDeclaration) {
		this.recoveryLitigationCertificateDeclaration = recoveryLitigationCertificateDeclaration;
	}
	
	public String getRecoveryLitigationCertificateEvidenceUrl() {
		return this.recoveryLitigationCertificateEvidenceUrl;
	}
	
	public void setRecoveryLitigationCertificateEvidenceUrl(String recoveryLitigationCertificateEvidenceUrl) {
		this.recoveryLitigationCertificateEvidenceUrl = recoveryLitigationCertificateEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificateEvidence() {
		return this.recoveryLitigationCertificateEvidence;
	}
	
	public void setRecoveryLitigationCertificateEvidence(	List<CommonAttachmentInfo> recoveryLitigationCertificateEvidence) {
		this.recoveryLitigationCertificateEvidence = recoveryLitigationCertificateEvidence;
	}
	
	public String getRecoveryLitigationCertificatePayNoticeUrl() {
		return this.recoveryLitigationCertificatePayNoticeUrl;
	}
	
	public void setRecoveryLitigationCertificatePayNoticeUrl(	String recoveryLitigationCertificatePayNoticeUrl) {
		this.recoveryLitigationCertificatePayNoticeUrl = recoveryLitigationCertificatePayNoticeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificatePayNotice() {
		return this.recoveryLitigationCertificatePayNotice;
	}
	
	public void setRecoveryLitigationCertificatePayNotice(	List<CommonAttachmentInfo> recoveryLitigationCertificatePayNotice) {
		this.recoveryLitigationCertificatePayNotice = recoveryLitigationCertificatePayNotice;
	}
	
	public String getRecoveryLitigationCertificateOtherUrl() {
		return this.recoveryLitigationCertificateOtherUrl;
	}
	
	public void setRecoveryLitigationCertificateOtherUrl(	String recoveryLitigationCertificateOtherUrl) {
		this.recoveryLitigationCertificateOtherUrl = recoveryLitigationCertificateOtherUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificateOther() {
		return this.recoveryLitigationCertificateOther;
	}
	
	public void setRecoveryLitigationCertificateOther(	List<CommonAttachmentInfo> recoveryLitigationCertificateOther) {
		this.recoveryLitigationCertificateOther = recoveryLitigationCertificateOther;
	}
	
	public String getRecoveryLitigationCertificateMemoUrl() {
		return this.recoveryLitigationCertificateMemoUrl;
	}
	
	public void setRecoveryLitigationCertificateMemoUrl(String recoveryLitigationCertificateMemoUrl) {
		this.recoveryLitigationCertificateMemoUrl = recoveryLitigationCertificateMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationCertificateMemo() {
		return this.recoveryLitigationCertificateMemo;
	}
	
	public void setRecoveryLitigationCertificateMemo(	List<CommonAttachmentInfo> recoveryLitigationCertificateMemo) {
		this.recoveryLitigationCertificateMemo = recoveryLitigationCertificateMemo;
	}
	
	public String getNotaryOrgan() {
		return notaryOrgan;
	}
	
	public void setNotaryOrgan(String notaryOrgan) {
		this.notaryOrgan = notaryOrgan;
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
	
	public String getNotarial() {
		return notarial;
	}
	
	public void setNotarial(String notarial) {
		this.notarial = notarial;
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
	
	public String getCertificate() {
		return certificate;
	}
	
	public void setCertificate(String certificate) {
		this.certificate = certificate;
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
