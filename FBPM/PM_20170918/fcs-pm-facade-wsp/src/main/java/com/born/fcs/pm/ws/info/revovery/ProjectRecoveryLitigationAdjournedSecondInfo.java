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
 * 追偿跟踪表 - 诉讼-再审程序-二审
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationAdjournedSecondInfo extends ProjectRecoveryLitigationBaseInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 上诉主体
	 */
	private String appealCenter;
	
	/**
	 * 上诉请求
	 */
	private String appealDemand;
	
	/**
	 * 公告时间
	 */
	private Date noticeTime;
	
	/**
	 * 开庭时间
	 */
	private Date openingTime;
	
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
	
	/*** 诉讼-再审程序 二审-上诉请求-附件 **/
	private String recoveryLitigationAdjournedSecondAppealDemandUrl;
	/*** 诉讼-再审程序 二审-上诉请求-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondAppealDemand;
	/*** 诉讼-再审程序 二审-公告时间-附件 **/
	private String recoveryLitigationAdjournedSecondNoticeTimeUrl;
	/*** 诉讼-再审程序 二审-公告时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondNoticeTime;
	/*** 诉讼-再审程序 二审-开庭时间-附件 **/
	private String recoveryLitigationAdjournedSecondOpeningTimeUrl;
	/*** 诉讼-再审程序 二审-开庭时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondOpeningTime;
	/*** 诉讼-再审程序 二审-新证据-附件 **/
	private String recoveryLitigationAdjournedSecondNewEvidenceUrl;
	/*** 诉讼-再审程序 二审-新证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondNewEvidence;
	/*** 诉讼-再审程序 二审-争议焦点-附件 **/
	private String recoveryLitigationAdjournedSecondControversyFocusUrl;
	/*** 诉讼-再审程序 二审-争议焦点-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondControversyFocus;
	/*** 诉讼-再审程序 二审-备注-附件 **/
	private String recoveryLitigationAdjournedSecondMemoUrl;
	/*** 诉讼-再审程序 二审-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public String getRecoveryLitigationAdjournedSecondAppealDemandUrl() {
		return this.recoveryLitigationAdjournedSecondAppealDemandUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondAppealDemandUrl(String recoveryLitigationAdjournedSecondAppealDemandUrl) {
		this.recoveryLitigationAdjournedSecondAppealDemandUrl = recoveryLitigationAdjournedSecondAppealDemandUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondAppealDemand() {
		return this.recoveryLitigationAdjournedSecondAppealDemand;
	}
	
	public void setRecoveryLitigationAdjournedSecondAppealDemand(	List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondAppealDemand) {
		this.recoveryLitigationAdjournedSecondAppealDemand = recoveryLitigationAdjournedSecondAppealDemand;
	}
	
	public String getRecoveryLitigationAdjournedSecondNoticeTimeUrl() {
		return this.recoveryLitigationAdjournedSecondNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondNoticeTimeUrl(	String recoveryLitigationAdjournedSecondNoticeTimeUrl) {
		this.recoveryLitigationAdjournedSecondNoticeTimeUrl = recoveryLitigationAdjournedSecondNoticeTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondNoticeTime() {
		return this.recoveryLitigationAdjournedSecondNoticeTime;
	}
	
	public void setRecoveryLitigationAdjournedSecondNoticeTime(	List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondNoticeTime) {
		this.recoveryLitigationAdjournedSecondNoticeTime = recoveryLitigationAdjournedSecondNoticeTime;
	}
	
	public String getRecoveryLitigationAdjournedSecondOpeningTimeUrl() {
		return this.recoveryLitigationAdjournedSecondOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondOpeningTimeUrl(	String recoveryLitigationAdjournedSecondOpeningTimeUrl) {
		this.recoveryLitigationAdjournedSecondOpeningTimeUrl = recoveryLitigationAdjournedSecondOpeningTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondOpeningTime() {
		return this.recoveryLitigationAdjournedSecondOpeningTime;
	}
	
	public void setRecoveryLitigationAdjournedSecondOpeningTime(List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondOpeningTime) {
		this.recoveryLitigationAdjournedSecondOpeningTime = recoveryLitigationAdjournedSecondOpeningTime;
	}
	
	public String getRecoveryLitigationAdjournedSecondNewEvidenceUrl() {
		return this.recoveryLitigationAdjournedSecondNewEvidenceUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondNewEvidenceUrl(	String recoveryLitigationAdjournedSecondNewEvidenceUrl) {
		this.recoveryLitigationAdjournedSecondNewEvidenceUrl = recoveryLitigationAdjournedSecondNewEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondNewEvidence() {
		return this.recoveryLitigationAdjournedSecondNewEvidence;
	}
	
	public void setRecoveryLitigationAdjournedSecondNewEvidence(List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondNewEvidence) {
		this.recoveryLitigationAdjournedSecondNewEvidence = recoveryLitigationAdjournedSecondNewEvidence;
	}
	
	public String getRecoveryLitigationAdjournedSecondControversyFocusUrl() {
		return this.recoveryLitigationAdjournedSecondControversyFocusUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondControversyFocusUrl(String recoveryLitigationAdjournedSecondControversyFocusUrl) {
		this.recoveryLitigationAdjournedSecondControversyFocusUrl = recoveryLitigationAdjournedSecondControversyFocusUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondControversyFocus() {
		return this.recoveryLitigationAdjournedSecondControversyFocus;
	}
	
	public void setRecoveryLitigationAdjournedSecondControversyFocus(	List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondControversyFocus) {
		this.recoveryLitigationAdjournedSecondControversyFocus = recoveryLitigationAdjournedSecondControversyFocus;
	}
	
	public String getRecoveryLitigationAdjournedSecondMemoUrl() {
		return this.recoveryLitigationAdjournedSecondMemoUrl;
	}
	
	public void setRecoveryLitigationAdjournedSecondMemoUrl(String recoveryLitigationAdjournedSecondMemoUrl) {
		this.recoveryLitigationAdjournedSecondMemoUrl = recoveryLitigationAdjournedSecondMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedSecondMemo() {
		return this.recoveryLitigationAdjournedSecondMemo;
	}
	
	public void setRecoveryLitigationAdjournedSecondMemo(	List<CommonAttachmentInfo> recoveryLitigationAdjournedSecondMemo) {
		this.recoveryLitigationAdjournedSecondMemo = recoveryLitigationAdjournedSecondMemo;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public Date getNoticeTime() {
		return noticeTime;
	}
	
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public Date getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Date openingTime) {
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
