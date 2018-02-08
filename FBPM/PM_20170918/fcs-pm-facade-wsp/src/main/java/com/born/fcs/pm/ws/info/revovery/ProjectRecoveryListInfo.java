/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:47:01 创建
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryListInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private Long recoveryId;
	
	/***
	 * 项目编号
	 */
	private String projectCode;
	
	/***
	 * 客户id
	 */
	private Long customerId;
	/***
	 * 客户名称
	 */
	private String customerName;
	
	/***
	 * 业务类型
	 */
	private String busiType;
	
	/**
	 * 业务类型名称
	 */
	private String busiTypeName;
	
	/**
	 * 代偿金额
	 */
	private Money recoveryAmount = new Money(0, 0);
	
	/**
	 * 业务经理Id
	 */
	private Long busiManagerId;
	
	/**
	 * 业务经理
	 */
	private String busiManagerName;
	
	/**
	 * 法务经理Id
	 */
	private Long legalManagerId;
	
	/**
	 * 法务经理
	 */
	private String legalManagerName;
	
	/**
	 * 项目状态
	 */
	private ProjectRecoveryStatusEnum recoveryStatus;
	
	/***
	 * 状态更新时间
	 */
	private Date statusUpdateTime;
	
	private Date rawAddTime;
	
	/** 组长ID */
	private long chiefLeaderId;
	
	/** 组长名称 */
	private String chiefLeaderName;
	
	/** 副组长ID */
	private long viceLeaderId;
	
	/** 副组长名称 */
	private String viceLeaderName;
	
	/** 组员ID，多个,隔开 */
	private String memberIds;
	
	/** 组员名称，多个,隔开 */
	private String memberNames;
	
	private BooleanEnum isAppend;
	
	private long appendRecoveryId;
	
	private String appendRecoveryName;
	
	private String appendRemark;
	
	private String recoveryName;
	
	private Date recoveryCloseTime;
	
	private Date projectCloseTime;
	
	public long getChiefLeaderId() {
		return this.chiefLeaderId;
	}
	
	public void setChiefLeaderId(long chiefLeaderId) {
		this.chiefLeaderId = chiefLeaderId;
	}
	
	public void setChiefLeaderName(String chiefLeaderName) {
		this.chiefLeaderName = chiefLeaderName;
	}
	
	public long getViceLeaderId() {
		return this.viceLeaderId;
	}
	
	public void setViceLeaderId(long viceLeaderId) {
		this.viceLeaderId = viceLeaderId;
	}
	
	public String getViceLeaderName() {
		return this.viceLeaderName;
	}
	
	public void setViceLeaderName(String viceLeaderName) {
		this.viceLeaderName = viceLeaderName;
	}
	
	public String getMemberIds() {
		return this.memberIds;
	}
	
	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}
	
	public String getMemberNames() {
		return this.memberNames;
	}
	
	public void setMemberNames(String memberNames) {
		this.memberNames = memberNames;
	}
	
	public Long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(Long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public Long getLegalManagerId() {
		return this.legalManagerId;
	}
	
	public void setLegalManagerId(Long legalManagerId) {
		this.legalManagerId = legalManagerId;
	}
	
	public String getChiefLeaderName() {
		return this.chiefLeaderName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public Long getRecoveryId() {
		return this.recoveryId;
	}
	
	public void setRecoveryId(Long recoveryId) {
		this.recoveryId = recoveryId;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getRecoveryAmount() {
		return this.recoveryAmount;
	}
	
	public void setRecoveryAmount(Money recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getLegalManagerName() {
		return this.legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public ProjectRecoveryStatusEnum getRecoveryStatus() {
		return this.recoveryStatus;
	}
	
	public void setRecoveryStatus(ProjectRecoveryStatusEnum recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}
	
	public Date getStatusUpdateTime() {
		return this.statusUpdateTime;
	}
	
	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}
	
	public BooleanEnum getIsAppend() {
		return this.isAppend;
	}
	
	public void setIsAppend(BooleanEnum isAppend) {
		this.isAppend = isAppend;
	}
	
	public long getAppendRecoveryId() {
		return this.appendRecoveryId;
	}
	
	public void setAppendRecoveryId(long appendRecoveryId) {
		this.appendRecoveryId = appendRecoveryId;
	}
	
	public String getAppendRecoveryName() {
		return this.appendRecoveryName;
	}
	
	public void setAppendRecoveryName(String appendRecoveryName) {
		this.appendRecoveryName = appendRecoveryName;
	}
	
	public String getAppendRemark() {
		return this.appendRemark;
	}
	
	public void setAppendRemark(String appendRemark) {
		this.appendRemark = appendRemark;
	}
	
	public Date getRecoveryCloseTime() {
		return this.recoveryCloseTime;
	}
	
	public void setRecoveryCloseTime(Date recoveryCloseTime) {
		this.recoveryCloseTime = recoveryCloseTime;
	}
	
	public Date getProjectCloseTime() {
		return this.projectCloseTime;
	}
	
	public void setProjectCloseTime(Date projectCloseTime) {
		this.projectCloseTime = projectCloseTime;
	}
	
	public String getRecoveryName() {
		return this.recoveryName;
	}
	
	public void setRecoveryName(String recoveryName) {
		this.recoveryName = recoveryName;
	}
	
}
