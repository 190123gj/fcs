/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-再审程序-二审
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationAdjournedSecondOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 上诉主体
	 */
	private String appealCenter;
	
	/**
	 * 上诉请求
	 */
	private String appealDemand;
	
	/**
	 * 公告时间 Date
	 */
	private String noticeTime;
	
	/**
	 * 开庭时间 Date
	 */
	private String openingTime;
	
	/**
	 * 新证据
	 */
	private String newEvidence;
	
	/**
	 * 争议焦点
	 */
	private String controversyFocus;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/** 诉讼顺序 */
	private int litigationIndex;
	
	// 附件 开始 
	
	/*** 诉讼-再审程序 二审-上诉请求-附件 **/
	private String recoveryLitigationAdjournedSecondAppealDemandUrl;
	/*** 诉讼-再审程序 二审-公告时间-附件 **/
	private String recoveryLitigationAdjournedSecondNoticeTimeUrl;
	/*** 诉讼-再审程序 二审-开庭时间-附件 **/
	private String recoveryLitigationAdjournedSecondOpeningTimeUrl;
	/*** 诉讼-再审程序 二审-新证据-附件 **/
	private String recoveryLitigationAdjournedSecondNewEvidenceUrl;
	/*** 诉讼-再审程序 二审-争议焦点-附件 **/
	private String recoveryLitigationAdjournedSecondControversyFocusUrl;
	/*** 诉讼-再审程序 二审-备注-附件 **/
	private String recoveryLitigationAdjournedSecondMemoUrl;
	
	//========== getters and setters ==========
	
	public String getAppealCenter() {
		return appealCenter;
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
	
	public void setAppealCenter(String appealCenter) {
		this.appealCenter = appealCenter;
	}
	
	public String getAppealDemand() {
		return appealDemand;
	}
	
	public void setAppealDemand(String appealDemand) {
		this.appealDemand = appealDemand;
	}
	
	public String getNoticeTime() {
		return this.noticeTime;
	}
	
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public String getOpeningTime() {
		return this.openingTime;
	}
	
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	
	public String getNewEvidence() {
		return newEvidence;
	}
	
	public void setNewEvidence(String newEvidence) {
		this.newEvidence = newEvidence;
	}
	
	public String getControversyFocus() {
		return controversyFocus;
	}
	
	public void setControversyFocus(String controversyFocus) {
		this.controversyFocus = controversyFocus;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public int getLitigationIndex() {
		return this.litigationIndex;
	}
	
	public void setLitigationIndex(int litigationIndex) {
		this.litigationIndex = litigationIndex;
	}
	
	public String getRecoveryLitigationAdjournedSecondAppealDemandUrl() {
		return this.recoveryLitigationAdjournedSecondAppealDemandUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondAppealDemandUrl(String recoveryLitigationAdjournedSecondAppealDemandUrl) {
		this.recoveryLitigationAdjournedSecondAppealDemandUrl = recoveryLitigationAdjournedSecondAppealDemandUrl;
	}
	
	public String getRecoveryLitigationAdjournedSecondNoticeTimeUrl() {
		return this.recoveryLitigationAdjournedSecondNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondNoticeTimeUrl(	String recoveryLitigationAdjournedSecondNoticeTimeUrl) {
		this.recoveryLitigationAdjournedSecondNoticeTimeUrl = recoveryLitigationAdjournedSecondNoticeTimeUrl;
	}
	
	public String getRecoveryLitigationAdjournedSecondOpeningTimeUrl() {
		return this.recoveryLitigationAdjournedSecondOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondOpeningTimeUrl(	String recoveryLitigationAdjournedSecondOpeningTimeUrl) {
		this.recoveryLitigationAdjournedSecondOpeningTimeUrl = recoveryLitigationAdjournedSecondOpeningTimeUrl;
	}
	
	public String getRecoveryLitigationAdjournedSecondNewEvidenceUrl() {
		return this.recoveryLitigationAdjournedSecondNewEvidenceUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondNewEvidenceUrl(	String recoveryLitigationAdjournedSecondNewEvidenceUrl) {
		this.recoveryLitigationAdjournedSecondNewEvidenceUrl = recoveryLitigationAdjournedSecondNewEvidenceUrl;
	}
	
	public String getRecoveryLitigationAdjournedSecondControversyFocusUrl() {
		return this.recoveryLitigationAdjournedSecondControversyFocusUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondControversyFocusUrl(String recoveryLitigationAdjournedSecondControversyFocusUrl) {
		this.recoveryLitigationAdjournedSecondControversyFocusUrl = recoveryLitigationAdjournedSecondControversyFocusUrl;
	}
	
	public String getRecoveryLitigationAdjournedSecondMemoUrl() {
		return this.recoveryLitigationAdjournedSecondMemoUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondMemoUrl(String recoveryLitigationAdjournedSecondMemoUrl) {
		this.recoveryLitigationAdjournedSecondMemoUrl = recoveryLitigationAdjournedSecondMemoUrl;
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
