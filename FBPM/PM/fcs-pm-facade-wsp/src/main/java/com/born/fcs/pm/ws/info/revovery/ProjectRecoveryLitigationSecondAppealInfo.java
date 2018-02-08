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
 * 追偿跟踪表 - 诉讼-二审上述
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationSecondAppealInfo extends ProjectRecoveryLitigationBaseInfo {
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
	
	/*** 诉讼-二审上述-上诉请求-附件 **/
	private String recoveryLitigationSecondAppealAppealDemandUrl;
	/*** 诉讼-二审上述-上诉请求-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealAppealDemand;
	
	/*** 诉讼-二审上述-公告时间-附件 **/
	private String recoveryLitigationSecondAppealNoticeTimeUrl;
	/*** 诉讼-二审上述-公告时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealNoticeTime;
	
	/*** 诉讼-二审上述-开庭时间-附件 **/
	private String recoveryLitigationSecondAppealOpeningTimeUrl;
	/*** 诉讼-二审上述-开庭时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealOpeningTime;
	
	/*** 诉讼-二审上述-新证据-附件 **/
	private String recoveryLitigationSecondAppealNewEvidenceUrl;
	/*** 诉讼-二审上述-新证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealNewEvidence;
	
	/*** 诉讼-二审上述-争议焦点-附件 **/
	private String recoveryLitigationSecondAppealControversyFocusUrl;
	/*** 诉讼-二审上述-争议焦点-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealControversyFocus;
	
	/*** 诉讼-二审上述-备注-附件 **/
	private String recoveryLitigationSecondAppealMemoUrl;
	/*** 诉讼-二审上述-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationSecondAppealMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationSecondAppealAppealDemandUrl() {
		return this.recoveryLitigationSecondAppealAppealDemandUrl;
	}
	
	public void setRecoveryLitigationSecondAppealAppealDemandUrl(	String recoveryLitigationSecondAppealAppealDemandUrl) {
		this.recoveryLitigationSecondAppealAppealDemandUrl = recoveryLitigationSecondAppealAppealDemandUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealAppealDemand() {
		return this.recoveryLitigationSecondAppealAppealDemand;
	}
	
	public void setRecoveryLitigationSecondAppealAppealDemand(	List<CommonAttachmentInfo> recoveryLitigationSecondAppealAppealDemand) {
		this.recoveryLitigationSecondAppealAppealDemand = recoveryLitigationSecondAppealAppealDemand;
	}
	
	public String getRecoveryLitigationSecondAppealNoticeTimeUrl() {
		return this.recoveryLitigationSecondAppealNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationSecondAppealNoticeTimeUrl(	String recoveryLitigationSecondAppealNoticeTimeUrl) {
		this.recoveryLitigationSecondAppealNoticeTimeUrl = recoveryLitigationSecondAppealNoticeTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealNoticeTime() {
		return this.recoveryLitigationSecondAppealNoticeTime;
	}
	
	public void setRecoveryLitigationSecondAppealNoticeTime(List<CommonAttachmentInfo> recoveryLitigationSecondAppealNoticeTime) {
		this.recoveryLitigationSecondAppealNoticeTime = recoveryLitigationSecondAppealNoticeTime;
	}
	
	public String getRecoveryLitigationSecondAppealOpeningTimeUrl() {
		return this.recoveryLitigationSecondAppealOpeningTimeUrl;
	}
	
	public void setRecoveryLitigationSecondAppealOpeningTimeUrl(String recoveryLitigationSecondAppealOpeningTimeUrl) {
		this.recoveryLitigationSecondAppealOpeningTimeUrl = recoveryLitigationSecondAppealOpeningTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealOpeningTime() {
		return this.recoveryLitigationSecondAppealOpeningTime;
	}
	
	public void setRecoveryLitigationSecondAppealOpeningTime(	List<CommonAttachmentInfo> recoveryLitigationSecondAppealOpeningTime) {
		this.recoveryLitigationSecondAppealOpeningTime = recoveryLitigationSecondAppealOpeningTime;
	}
	
	public String getRecoveryLitigationSecondAppealNewEvidenceUrl() {
		return this.recoveryLitigationSecondAppealNewEvidenceUrl;
	}
	
	public void setRecoveryLitigationSecondAppealNewEvidenceUrl(String recoveryLitigationSecondAppealNewEvidenceUrl) {
		this.recoveryLitigationSecondAppealNewEvidenceUrl = recoveryLitigationSecondAppealNewEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealNewEvidence() {
		return this.recoveryLitigationSecondAppealNewEvidence;
	}
	
	public void setRecoveryLitigationSecondAppealNewEvidence(	List<CommonAttachmentInfo> recoveryLitigationSecondAppealNewEvidence) {
		this.recoveryLitigationSecondAppealNewEvidence = recoveryLitigationSecondAppealNewEvidence;
	}
	
	public String getRecoveryLitigationSecondAppealControversyFocusUrl() {
		return this.recoveryLitigationSecondAppealControversyFocusUrl;
	}
	
	public void setRecoveryLitigationSecondAppealControversyFocusUrl(	String recoveryLitigationSecondAppealControversyFocusUrl) {
		this.recoveryLitigationSecondAppealControversyFocusUrl = recoveryLitigationSecondAppealControversyFocusUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealControversyFocus() {
		return this.recoveryLitigationSecondAppealControversyFocus;
	}
	
	public void setRecoveryLitigationSecondAppealControversyFocus(	List<CommonAttachmentInfo> recoveryLitigationSecondAppealControversyFocus) {
		this.recoveryLitigationSecondAppealControversyFocus = recoveryLitigationSecondAppealControversyFocus;
	}
	
	public String getRecoveryLitigationSecondAppealMemoUrl() {
		return this.recoveryLitigationSecondAppealMemoUrl;
	}
	
	public void setRecoveryLitigationSecondAppealMemoUrl(	String recoveryLitigationSecondAppealMemoUrl) {
		this.recoveryLitigationSecondAppealMemoUrl = recoveryLitigationSecondAppealMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationSecondAppealMemo() {
		return this.recoveryLitigationSecondAppealMemo;
	}
	
	public void setRecoveryLitigationSecondAppealMemo(	List<CommonAttachmentInfo> recoveryLitigationSecondAppealMemo) {
		this.recoveryLitigationSecondAppealMemo = recoveryLitigationSecondAppealMemo;
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
