/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryRefereeTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-裁判
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationRefereeOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 裁判类型
	 */
	private ProjectRecoveryRefereeTypeEnum projectRecoveryRefereeType;
	
	/**
	 * 裁判文书
	 */
	private String refereeClerk;
	
	/**
	 * 送达时间 Date
	 */
	private String arrivedTime;
	
	/**
	 * 公告时间 Date
	 */
	private String noticeTime;
	
	/**
	 * 生效时间 Date
	 */
	private String effectiveTime;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件  开始
	
	/*** 诉讼-裁判-裁判文书-附件 **/
	private String recoveryLitigationRefereeClerkUrl;
	/*** 诉讼-裁判-送达时间-附件 **/
	private String recoveryLitigationRefereeArrivedTimeUrl;
	/*** 诉讼-裁判-公告时间-附件 **/
	private String recoveryLitigationRefereeNoticeTimeUrl;
	/*** 诉讼-裁判-生效时间-附件 **/
	private String recoveryLitigationRefereeEffectiveTimeUrl;
	/*** 诉讼-裁判-备注-附件 **/
	private String recoveryLitigationRefereeMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationRefereeClerkUrl() {
		return this.recoveryLitigationRefereeClerkUrl;
	}
	
	public void setRecoveryLitigationRefereeClerkUrl(String recoveryLitigationRefereeClerkUrl) {
		this.recoveryLitigationRefereeClerkUrl = recoveryLitigationRefereeClerkUrl;
	}
	
	public String getRecoveryLitigationRefereeArrivedTimeUrl() {
		return this.recoveryLitigationRefereeArrivedTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeArrivedTimeUrl(	String recoveryLitigationRefereeArrivedTimeUrl) {
		this.recoveryLitigationRefereeArrivedTimeUrl = recoveryLitigationRefereeArrivedTimeUrl;
	}
	
	public String getRecoveryLitigationRefereeNoticeTimeUrl() {
		return this.recoveryLitigationRefereeNoticeTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeNoticeTimeUrl(	String recoveryLitigationRefereeNoticeTimeUrl) {
		this.recoveryLitigationRefereeNoticeTimeUrl = recoveryLitigationRefereeNoticeTimeUrl;
	}
	
	public String getRecoveryLitigationRefereeEffectiveTimeUrl() {
		return this.recoveryLitigationRefereeEffectiveTimeUrl;
	}
	
	public void setRecoveryLitigationRefereeEffectiveTimeUrl(	String recoveryLitigationRefereeEffectiveTimeUrl) {
		this.recoveryLitigationRefereeEffectiveTimeUrl = recoveryLitigationRefereeEffectiveTimeUrl;
	}
	
	public String getRecoveryLitigationRefereeMemoUrl() {
		return this.recoveryLitigationRefereeMemoUrl;
	}
	
	public void setRecoveryLitigationRefereeMemoUrl(String recoveryLitigationRefereeMemoUrl) {
		this.recoveryLitigationRefereeMemoUrl = recoveryLitigationRefereeMemoUrl;
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
	
	public String getRefereeClerk() {
		return refereeClerk;
	}
	
	public void setRefereeClerk(String refereeClerk) {
		this.refereeClerk = refereeClerk;
	}
	
	public ProjectRecoveryRefereeTypeEnum getProjectRecoveryRefereeType() {
		return this.projectRecoveryRefereeType;
	}
	
	public void setProjectRecoveryRefereeType(	ProjectRecoveryRefereeTypeEnum projectRecoveryRefereeType) {
		this.projectRecoveryRefereeType = projectRecoveryRefereeType;
	}
	
	public String getArrivedTime() {
		return this.arrivedTime;
	}
	
	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	
	public String getNoticeTime() {
		return this.noticeTime;
	}
	
	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}
	
	public String getEffectiveTime() {
		return this.effectiveTime;
	}
	
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
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
