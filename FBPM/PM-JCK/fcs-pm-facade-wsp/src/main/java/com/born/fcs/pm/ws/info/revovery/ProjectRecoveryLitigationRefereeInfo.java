/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryRefereeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 追偿跟踪表 - 诉讼-裁判
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationRefereeInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 裁判类型
	 */
	private ProjectRecoveryRefereeTypeEnum projectRecoveryRefereeType;
	
	/**
	 * 裁判文书
	 */
	private String refereeClerk;
	
	/**
	 * 送达时间
	 */
	private Date arrivedTime;
	
	/**
	 * 公告时间
	 */
	private Date noticeTime;
	
	/**
	 * 生效时间
	 */
	private Date effectiveTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件  开始
	
	/*** 诉讼-裁判-裁判文书-附件 **/
	private String recoveryLitigationRefereeClerkUrl;
	/*** 诉讼-裁判-裁判文书-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationRefereeClerk;
	
	/*** 诉讼-裁判-送达时间-附件 **/
	private String recoveryLitigationRefereeArrivedTimeUrl;
	/*** 诉讼-裁判-送达时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationRefereeArrivedTime;
	
	/*** 诉讼-裁判-公告时间-附件 **/
	private String recoveryLitigationRefereeNoticeTimeUrl;
	/*** 诉讼-裁判-公告时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationRefereeNoticeTime;
	
	/*** 诉讼-裁判-生效时间-附件 **/
	private String recoveryLitigationRefereeEffectiveTimeUrl;
	/*** 诉讼-裁判-生效时间-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationRefereeEffectiveTime;
	
	/*** 诉讼-裁判-备注-附件 **/
	private String recoveryLitigationRefereeMemoUrl;
	/*** 诉讼-裁判-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationRefereeMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationRefereeClerkUrl() {
		return this.recoveryLitigationRefereeClerkUrl;
	}
	
	public void setRecoveryLitigationRefereeClerkUrl(String recoveryLitigationRefereeClerkUrl) {
		this.recoveryLitigationRefereeClerkUrl = recoveryLitigationRefereeClerkUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationRefereeClerk() {
		return this.recoveryLitigationRefereeClerk;
	}
	
	public ProjectRecoveryRefereeTypeEnum getProjectRecoveryRefereeType() {
		return this.projectRecoveryRefereeType;
	}
	
	public void setProjectRecoveryRefereeType(	ProjectRecoveryRefereeTypeEnum projectRecoveryRefereeType) {
		this.projectRecoveryRefereeType = projectRecoveryRefereeType;
	}
	
	public void setRecoveryLitigationRefereeClerk(	List<CommonAttachmentInfo> recoveryLitigationRefereeClerk) {
		this.recoveryLitigationRefereeClerk = recoveryLitigationRefereeClerk;
	}
	
	public String getRecoveryLitigationRefereeArrivedTimeUrl() {
		return this.recoveryLitigationRefereeArrivedTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeArrivedTimeUrl(	String recoveryLitigationRefereeArrivedTimeUrl) {
		this.recoveryLitigationRefereeArrivedTimeUrl = recoveryLitigationRefereeArrivedTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationRefereeArrivedTime() {
		return this.recoveryLitigationRefereeArrivedTime;
	}
	
	public void setRecoveryLitigationRefereeArrivedTime(List<CommonAttachmentInfo> recoveryLitigationRefereeArrivedTime) {
		this.recoveryLitigationRefereeArrivedTime = recoveryLitigationRefereeArrivedTime;
	}
	
	public String getRecoveryLitigationRefereeNoticeTimeUrl() {
		return this.recoveryLitigationRefereeNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeNoticeTimeUrl(	String recoveryLitigationRefereeNoticeTimeUrl) {
		this.recoveryLitigationRefereeNoticeTimeUrl = recoveryLitigationRefereeNoticeTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationRefereeNoticeTime() {
		return this.recoveryLitigationRefereeNoticeTime;
	}
	
	public void setRecoveryLitigationRefereeNoticeTime(	List<CommonAttachmentInfo> recoveryLitigationRefereeNoticeTime) {
		this.recoveryLitigationRefereeNoticeTime = recoveryLitigationRefereeNoticeTime;
	}
	
	public String getRecoveryLitigationRefereeEffectiveTimeUrl() {
		return this.recoveryLitigationRefereeEffectiveTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeEffectiveTimeUrl(	String recoveryLitigationRefereeEffectiveTimeUrl) {
		this.recoveryLitigationRefereeEffectiveTimeUrl = recoveryLitigationRefereeEffectiveTimeUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationRefereeEffectiveTime() {
		return this.recoveryLitigationRefereeEffectiveTime;
	}
	
	public void setRecoveryLitigationRefereeEffectiveTime(	List<CommonAttachmentInfo> recoveryLitigationRefereeEffectiveTime) {
		this.recoveryLitigationRefereeEffectiveTime = recoveryLitigationRefereeEffectiveTime;
	}
	
	public String getRecoveryLitigationRefereeMemoUrl() {
		return this.recoveryLitigationRefereeMemoUrl;
	}
	
	public void setRecoveryLitigationRefereeMemoUrl(String recoveryLitigationRefereeMemoUrl) {
		this.recoveryLitigationRefereeMemoUrl = recoveryLitigationRefereeMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationRefereeMemo() {
		return this.recoveryLitigationRefereeMemo;
	}
	
	public void setRecoveryLitigationRefereeMemo(	List<CommonAttachmentInfo> recoveryLitigationRefereeMemo) {
		this.recoveryLitigationRefereeMemo = recoveryLitigationRefereeMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public String getRefereeClerk() {
		return refereeClerk;
	}
	
	public void setRefereeClerk(String refereeClerk) {
		this.refereeClerk = refereeClerk;
	}
	
	public Date getArrivedTime() {
		return arrivedTime;
	}
	
	public void setArrivedTime(Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	
	public Date getNoticeTime() {
		return noticeTime;
	}
	
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public Date getEffectiveTime() {
		return effectiveTime;
	}
	
	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
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
