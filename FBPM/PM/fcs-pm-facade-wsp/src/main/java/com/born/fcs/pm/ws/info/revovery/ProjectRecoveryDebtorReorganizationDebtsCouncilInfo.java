/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/***
 * 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationDebtsCouncilInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 追偿跟踪表 - 债务人重整或破产清算表主键
	 */
	private long projectRecoveryDebtorReorganizationId;
	
	/**
	 * 债权人会议描述
	 */
	private String debtsCouncilDescribe;
	
	/**
	 * 债权人会议日期
	 */
	private Date debtsCouncilTime;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public long getProjectRecoveryDebtorReorganizationId() {
		return projectRecoveryDebtorReorganizationId;
	}
	
	public void setProjectRecoveryDebtorReorganizationId(long projectRecoveryDebtorReorganizationId) {
		this.projectRecoveryDebtorReorganizationId = projectRecoveryDebtorReorganizationId;
	}
	
	public String getDebtsCouncilDescribe() {
		return debtsCouncilDescribe;
	}
	
	public void setDebtsCouncilDescribe(String debtsCouncilDescribe) {
		this.debtsCouncilDescribe = debtsCouncilDescribe;
	}
	
	public Date getDebtsCouncilTime() {
		return debtsCouncilTime;
	}
	
	public void setDebtsCouncilTime(Date debtsCouncilTime) {
		this.debtsCouncilTime = debtsCouncilTime;
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
