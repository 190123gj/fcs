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
 * 追偿跟踪表 - 诉讼-开庭
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationOpeningInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 开庭时间
	 */
	private Date openingTime;
	
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
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件 开始
	/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件 **/
	private String recoveryLitigationOpeningWeLitigationDemandUrl;
	/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationOpeningWeLitigationDemand;
	
	/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件 **/
	private String recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationOpeningOtherSideLitigationDemand;
	
	/*** 诉讼-开庭-补充证据-附件 **/
	private String recoveryLitigationOpeningAdditionalEvidenceUrl;
	/*** 诉讼-开庭-补充证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationOpeningAdditionalEvidence;
	
	/*** 诉讼-开庭-备注-附件 **/
	private String recoveryLitigationOpeningMemoUrl;
	/*** 诉讼-开庭-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationOpeningMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationOpeningWeLitigationDemandUrl() {
		return this.recoveryLitigationOpeningWeLitigationDemandUrl;
	}
	
	public void setRecoveryLitigationOpeningWeLitigationDemandUrl(	String recoveryLitigationOpeningWeLitigationDemandUrl) {
		this.recoveryLitigationOpeningWeLitigationDemandUrl = recoveryLitigationOpeningWeLitigationDemandUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationOpeningWeLitigationDemand() {
		return this.recoveryLitigationOpeningWeLitigationDemand;
	}
	
	public void setRecoveryLitigationOpeningWeLitigationDemand(	List<CommonAttachmentInfo> recoveryLitigationOpeningWeLitigationDemand) {
		this.recoveryLitigationOpeningWeLitigationDemand = recoveryLitigationOpeningWeLitigationDemand;
	}
	
	public String getRecoveryLitigationOpeningOtherSideLitigationDemandUrl() {
		return this.recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	}
	
	public void setRecoveryLitigationOpeningOtherSideLitigationDemandUrl(	String recoveryLitigationOpeningOtherSideLitigationDemandUrl) {
		this.recoveryLitigationOpeningOtherSideLitigationDemandUrl = recoveryLitigationOpeningOtherSideLitigationDemandUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationOpeningOtherSideLitigationDemand() {
		return this.recoveryLitigationOpeningOtherSideLitigationDemand;
	}
	
	public void setRecoveryLitigationOpeningOtherSideLitigationDemand(	List<CommonAttachmentInfo> recoveryLitigationOpeningOtherSideLitigationDemand) {
		this.recoveryLitigationOpeningOtherSideLitigationDemand = recoveryLitigationOpeningOtherSideLitigationDemand;
	}
	
	public String getRecoveryLitigationOpeningAdditionalEvidenceUrl() {
		return this.recoveryLitigationOpeningAdditionalEvidenceUrl;
	}
	
	public void setRecoveryLitigationOpeningAdditionalEvidenceUrl(	String recoveryLitigationOpeningAdditionalEvidenceUrl) {
		this.recoveryLitigationOpeningAdditionalEvidenceUrl = recoveryLitigationOpeningAdditionalEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationOpeningAdditionalEvidence() {
		return this.recoveryLitigationOpeningAdditionalEvidence;
	}
	
	public void setRecoveryLitigationOpeningAdditionalEvidence(	List<CommonAttachmentInfo> recoveryLitigationOpeningAdditionalEvidence) {
		this.recoveryLitigationOpeningAdditionalEvidence = recoveryLitigationOpeningAdditionalEvidence;
	}
	
	public String getRecoveryLitigationOpeningMemoUrl() {
		return this.recoveryLitigationOpeningMemoUrl;
	}
	
	public void setRecoveryLitigationOpeningMemoUrl(String recoveryLitigationOpeningMemoUrl) {
		this.recoveryLitigationOpeningMemoUrl = recoveryLitigationOpeningMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationOpeningMemo() {
		return this.recoveryLitigationOpeningMemo;
	}
	
	public void setRecoveryLitigationOpeningMemo(	List<CommonAttachmentInfo> recoveryLitigationOpeningMemo) {
		this.recoveryLitigationOpeningMemo = recoveryLitigationOpeningMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public Date getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Date openingTime) {
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
