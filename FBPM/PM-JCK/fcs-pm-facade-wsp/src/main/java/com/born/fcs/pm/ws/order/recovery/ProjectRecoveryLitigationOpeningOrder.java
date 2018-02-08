/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-开庭
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationOpeningOrder extends ProcessOrder {
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
	 * 出庭律师
	 */
	private String witnessAttorney;
	
	/**
	 * 我方主要诉讼请求或答辩意见
	 */
	private String weLitigationDemand;
	
	/**
	 * 对方主要诉讼请求或答辩意见
	 */
	private String otherSideLitigationDemand;
	
	/**
	 * 争议焦点
	 */
	private String controversyFocus;
	
	/**
	 * 补充证据
	 */
	private String additionalEvidence;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始
	/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件 **/
	private String recoveryLitigationOpeningWeLitigationDemandUrl;
	/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件 **/
	private String recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	/*** 诉讼-开庭-补充证据-附件 **/
	private String recoveryLitigationOpeningAdditionalEvidenceUrl;
	/*** 诉讼-开庭-备注-附件 **/
	private String recoveryLitigationOpeningMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationOpeningWeLitigationDemandUrl() {
		return this.recoveryLitigationOpeningWeLitigationDemandUrl;
	}
	
	public void setRecoveryLitigationOpeningWeLitigationDemandUrl(	String recoveryLitigationOpeningWeLitigationDemandUrl) {
		this.recoveryLitigationOpeningWeLitigationDemandUrl = recoveryLitigationOpeningWeLitigationDemandUrl;
	}
	
	public String getRecoveryLitigationOpeningOtherSideLitigationDemandUrl() {
		return this.recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	}
	
	public void setRecoveryLitigationOpeningOtherSideLitigationDemandUrl(	String recoveryLitigationOpeningOtherSideLitigationDemandUrl) {
		this.recoveryLitigationOpeningOtherSideLitigationDemandUrl = recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	}
	
	public String getRecoveryLitigationOpeningAdditionalEvidenceUrl() {
		return this.recoveryLitigationOpeningAdditionalEvidenceUrl;
	}
	
	public void setRecoveryLitigationOpeningAdditionalEvidenceUrl(	String recoveryLitigationOpeningAdditionalEvidenceUrl) {
		this.recoveryLitigationOpeningAdditionalEvidenceUrl = recoveryLitigationOpeningAdditionalEvidenceUrl;
	}
	
	public String getRecoveryLitigationOpeningMemoUrl() {
		return this.recoveryLitigationOpeningMemoUrl;
	}
	
	public void setRecoveryLitigationOpeningMemoUrl(String recoveryLitigationOpeningMemoUrl) {
		this.recoveryLitigationOpeningMemoUrl = recoveryLitigationOpeningMemoUrl;
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
	
	public String getWitnessAttorney() {
		return witnessAttorney;
	}
	
	public void setWitnessAttorney(String witnessAttorney) {
		this.witnessAttorney = witnessAttorney;
	}
	
	public String getWeLitigationDemand() {
		return weLitigationDemand;
	}
	
	public void setWeLitigationDemand(String weLitigationDemand) {
		this.weLitigationDemand = weLitigationDemand;
	}
	
	public String getOtherSideLitigationDemand() {
		return otherSideLitigationDemand;
	}
	
	public void setOtherSideLitigationDemand(String otherSideLitigationDemand) {
		this.otherSideLitigationDemand = otherSideLitigationDemand;
	}
	
	public String getControversyFocus() {
		return controversyFocus;
	}
	
	public void setControversyFocus(String controversyFocus) {
		this.controversyFocus = controversyFocus;
	}
	
	public String getAdditionalEvidence() {
		return additionalEvidence;
	}
	
	public void setAdditionalEvidence(String additionalEvidence) {
		this.additionalEvidence = additionalEvidence;
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
