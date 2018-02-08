/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-强制执行公证执行证书
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationCertificateOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
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
	 * 申请时间 Date
	 */
	private String applyTime;
	
	/**
	 * 缴费时间 Date
	 */
	private String payTime;
	
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
	/*** 诉讼-强制执行公证执行证书-申请书-附件 **/
	private String recoveryLitigationCertificateDeclarationUrl;
	/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件 **/
	private String recoveryLitigationCertificateEvidenceUrl;
	/*** 诉讼-强制执行公证执行证书-缴费通知书-附件 **/
	private String recoveryLitigationCertificatePayNoticeUrl;
	/*** 诉讼-强制执行公证执行证书-其他-附件 **/
	private String recoveryLitigationCertificateOtherUrl;
	/*** 诉讼-强制执行公证执行证书-备注-附件 **/
	private String recoveryLitigationCertificateMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationCertificateNoUrl() {
		return this.recoveryLitigationCertificateNoUrl;
	}
	
	public void setRecoveryLitigationCertificateNoUrl(String recoveryLitigationCertificateNoUrl) {
		this.recoveryLitigationCertificateNoUrl = recoveryLitigationCertificateNoUrl;
	}
	
	public String getRecoveryLitigationCertificateDeclarationUrl() {
		return this.recoveryLitigationCertificateDeclarationUrl;
	}
	
	public void setRecoveryLitigationCertificateDeclarationUrl(	String recoveryLitigationCertificateDeclarationUrl) {
		this.recoveryLitigationCertificateDeclarationUrl = recoveryLitigationCertificateDeclarationUrl;
	}
	
	public String getRecoveryLitigationCertificateEvidenceUrl() {
		return this.recoveryLitigationCertificateEvidenceUrl;
	}
	
	public void setRecoveryLitigationCertificateEvidenceUrl(String recoveryLitigationCertificateEvidenceUrl) {
		this.recoveryLitigationCertificateEvidenceUrl = recoveryLitigationCertificateEvidenceUrl;
	}
	
	public String getRecoveryLitigationCertificatePayNoticeUrl() {
		return this.recoveryLitigationCertificatePayNoticeUrl;
	}
	
	public void setRecoveryLitigationCertificatePayNoticeUrl(	String recoveryLitigationCertificatePayNoticeUrl) {
		this.recoveryLitigationCertificatePayNoticeUrl = recoveryLitigationCertificatePayNoticeUrl;
	}
	
	public String getRecoveryLitigationCertificateOtherUrl() {
		return this.recoveryLitigationCertificateOtherUrl;
	}
	
	public void setRecoveryLitigationCertificateOtherUrl(	String recoveryLitigationCertificateOtherUrl) {
		this.recoveryLitigationCertificateOtherUrl = recoveryLitigationCertificateOtherUrl;
	}
	
	public String getRecoveryLitigationCertificateMemoUrl() {
		return this.recoveryLitigationCertificateMemoUrl;
	}
	
	public void setRecoveryLitigationCertificateMemoUrl(String recoveryLitigationCertificateMemoUrl) {
		this.recoveryLitigationCertificateMemoUrl = recoveryLitigationCertificateMemoUrl;
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
	
	public String getApplyTime() {
		return this.applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(String payTime) {
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
