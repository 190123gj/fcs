/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.PledgeAssetManagementModeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.yjf.common.lang.util.money.Money;

/***
 * 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationPledgeInfo extends BaseToStringInfo {
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
	 * 追偿执行表主键
	 */
	private long projectRecoveryLitigationExecuteId;
	
	/**
	 * 追偿类型
	 */
	private ProjectRecoveryTypeEnum projectRecoveryType;
	
	/**
	 * 资金表id
	 */
	private String assetId;
	
	/**
	 * 名称
	 */
	private String pledgeName;
	
	/**
	 * 抵债金额
	 */
	private Money pledgeAmount = new Money(0, 0);
	
	/**
	 * 抵债资产管理方式
	 */
	private PledgeAssetManagementModeEnum pledgeAssetManagementMode;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件开始 
	
	/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件 **/
	private String recoveryReorganizationPledgeSoldOutUrl;
	/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件s **/
	private List<CommonAttachmentInfo> recoveryReorganizationPledgeSoldOut;
	
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	private String recoveryLitigationExecutePledgeSoldOutMemoUrl;
	/*** 诉讼-抵质押资产抵债-默认附件-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecutePledgeSoldOutMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public String getRecoveryLitigationExecutePledgeSoldOutMemoUrl() {
		return this.recoveryLitigationExecutePledgeSoldOutMemoUrl;
	}
	
	public void setRecoveryLitigationExecutePledgeSoldOutMemoUrl(	String recoveryLitigationExecutePledgeSoldOutMemoUrl) {
		this.recoveryLitigationExecutePledgeSoldOutMemoUrl = recoveryLitigationExecutePledgeSoldOutMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecutePledgeSoldOutMemo() {
		return this.recoveryLitigationExecutePledgeSoldOutMemo;
	}
	
	public void setRecoveryLitigationExecutePledgeSoldOutMemo(	List<CommonAttachmentInfo> recoveryLitigationExecutePledgeSoldOutMemo) {
		this.recoveryLitigationExecutePledgeSoldOutMemo = recoveryLitigationExecutePledgeSoldOutMemo;
	}
	
	public String getAssetId() {
		return this.assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public String getRecoveryReorganizationPledgeSoldOutUrl() {
		return this.recoveryReorganizationPledgeSoldOutUrl;
	}
	
	public void setRecoveryReorganizationPledgeSoldOutUrl(	String recoveryReorganizationPledgeSoldOutUrl) {
		this.recoveryReorganizationPledgeSoldOutUrl = recoveryReorganizationPledgeSoldOutUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryReorganizationPledgeSoldOut() {
		return this.recoveryReorganizationPledgeSoldOut;
	}
	
	public void setRecoveryReorganizationPledgeSoldOut(	List<CommonAttachmentInfo> recoveryReorganizationPledgeSoldOut) {
		this.recoveryReorganizationPledgeSoldOut = recoveryReorganizationPledgeSoldOut;
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
	
	public long getProjectRecoveryLitigationExecuteId() {
		return projectRecoveryLitigationExecuteId;
	}
	
	public void setProjectRecoveryLitigationExecuteId(long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
	}
	
	public String getPledgeName() {
		return pledgeName;
	}
	
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	
	public Money getPledgeAmount() {
		return pledgeAmount;
	}
	
	public void setPledgeAmount(Money pledgeAmount) {
		if (pledgeAmount == null) {
			this.pledgeAmount = new Money(0, 0);
		} else {
			this.pledgeAmount = pledgeAmount;
		}
	}
	
	public PledgeAssetManagementModeEnum getPledgeAssetManagementMode() {
		return this.pledgeAssetManagementMode;
	}
	
	public ProjectRecoveryTypeEnum getProjectRecoveryType() {
		return this.projectRecoveryType;
	}
	
	public void setProjectRecoveryType(ProjectRecoveryTypeEnum projectRecoveryType) {
		this.projectRecoveryType = projectRecoveryType;
	}
	
	public void setPledgeAssetManagementMode(PledgeAssetManagementModeEnum pledgeAssetManagementMode) {
		this.pledgeAssetManagementMode = pledgeAssetManagementMode;
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
