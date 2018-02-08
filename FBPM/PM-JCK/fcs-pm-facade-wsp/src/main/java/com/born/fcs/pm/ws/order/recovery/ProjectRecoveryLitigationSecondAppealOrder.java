/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-二审上述
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationSecondAppealOrder extends ProcessOrder {
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
	
	// 附件   开始  
	
	/*** 诉讼-二审上述-上诉请求-附件 **/
	private String recoveryLitigationSecondAppealAppealDemandUrl;
	/*** 诉讼-二审上述-公告时间-附件 **/
	private String recoveryLitigationSecondAppealNoticeTimeUrl;
	/*** 诉讼-二审上述-开庭时间-附件 **/
	private String recoveryLitigationSecondAppealOpeningTimeUrl;
	/*** 诉讼-二审上述-新证据-附件 **/
	private String recoveryLitigationSecondAppealNewEvidenceUrl;
	/*** 诉讼-二审上述-争议焦点-附件 **/
	private String recoveryLitigationSecondAppealControversyFocusUrl;
	/*** 诉讼-二审上述-备注-附件 **/
	private String recoveryLitigationSecondAppealMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationSecondAppealAppealDemandUrl() {
		return this.recoveryLitigationSecondAppealAppealDemandUrl;
	}
	
	public void setRecoveryLitigationSecondAppealAppealDemandUrl(	String recoveryLitigationSecondAppealAppealDemandUrl) {
		this.recoveryLitigationSecondAppealAppealDemandUrl = recoveryLitigationSecondAppealAppealDemandUrl;
	}
	
	public String getRecoveryLitigationSecondAppealNoticeTimeUrl() {
		return this.recoveryLitigationSecondAppealNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationSecondAppealNoticeTimeUrl(	String recoveryLitigationSecondAppealNoticeTimeUrl) {
		this.recoveryLitigationSecondAppealNoticeTimeUrl = recoveryLitigationSecondAppealNoticeTimeUrl;
	}
	
	public String getRecoveryLitigationSecondAppealOpeningTimeUrl() {
		return this.recoveryLitigationSecondAppealOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationSecondAppealOpeningTimeUrl(String recoveryLitigationSecondAppealOpeningTimeUrl) {
		this.recoveryLitigationSecondAppealOpeningTimeUrl = recoveryLitigationSecondAppealOpeningTimeUrl;
	}
	
	public String getRecoveryLitigationSecondAppealNewEvidenceUrl() {
		return this.recoveryLitigationSecondAppealNewEvidenceUrl;
	}
	
	public void setRecoveryLitigationSecondAppealNewEvidenceUrl(String recoveryLitigationSecondAppealNewEvidenceUrl) {
		this.recoveryLitigationSecondAppealNewEvidenceUrl = recoveryLitigationSecondAppealNewEvidenceUrl;
	}
	
	public String getRecoveryLitigationSecondAppealControversyFocusUrl() {
		return this.recoveryLitigationSecondAppealControversyFocusUrl;
	}
	
	public void setRecoveryLitigationSecondAppealControversyFocusUrl(	String recoveryLitigationSecondAppealControversyFocusUrl) {
		this.recoveryLitigationSecondAppealControversyFocusUrl = recoveryLitigationSecondAppealControversyFocusUrl;
	}
	
	public String getRecoveryLitigationSecondAppealMemoUrl() {
		return this.recoveryLitigationSecondAppealMemoUrl;
	}
	
	public void setRecoveryLitigationSecondAppealMemoUrl(	String recoveryLitigationSecondAppealMemoUrl) {
		this.recoveryLitigationSecondAppealMemoUrl = recoveryLitigationSecondAppealMemoUrl;
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
	
	public String getAppealCenter() {
		return appealCenter;
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
