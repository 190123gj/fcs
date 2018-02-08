/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.PledgeAssetManagementModeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationPledgeOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 追偿跟踪表 - 债务人重整或破产清算表主键
	 */
	private Long projectRecoveryDebtorReorganizationId;
	
	/**
	 * 追偿执行表主键
	 */
	private Long projectRecoveryLitigationExecuteId;
	
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
	 * 抵债金额 Money
	 */
	private String pledgeAmount;
	
	/**
	 * 抵债资产管理方式
	 */
	private PledgeAssetManagementModeEnum pledgeAssetManagementMode;
	
	//========== getters and setters ==========
	
	// 附件开始 
	
	/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件 **/
	private String recoveryReorganizationPledgeSoldOutUrl;
	
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	private String recoveryLitigationExecutePledgeSoldOutMemoUrl;
	
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
	
	public Long getProjectRecoveryDebtorReorganizationId() {
		return this.projectRecoveryDebtorReorganizationId;
	}
	
	public void setProjectRecoveryDebtorReorganizationId(Long projectRecoveryDebtorReorganizationId) {
		this.projectRecoveryDebtorReorganizationId = projectRecoveryDebtorReorganizationId;
	}
	
	public Long getProjectRecoveryLitigationExecuteId() {
		return this.projectRecoveryLitigationExecuteId;
	}
	
	public void setProjectRecoveryLitigationExecuteId(Long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
	}
	
	public String getRecoveryLitigationExecutePledgeSoldOutMemoUrl() {
		return this.recoveryLitigationExecutePledgeSoldOutMemoUrl;
	}
	
	public void setRecoveryLitigationExecutePledgeSoldOutMemoUrl(	String recoveryLitigationExecutePledgeSoldOutMemoUrl) {
		this.recoveryLitigationExecutePledgeSoldOutMemoUrl = recoveryLitigationExecutePledgeSoldOutMemoUrl;
	}
	
	public String getRecoveryReorganizationPledgeSoldOutUrl() {
		return this.recoveryReorganizationPledgeSoldOutUrl;
	}
	
	public void setRecoveryReorganizationPledgeSoldOutUrl(	String recoveryReorganizationPledgeSoldOutUrl) {
		this.recoveryReorganizationPledgeSoldOutUrl = recoveryReorganizationPledgeSoldOutUrl;
	}
	
	public String getPledgeName() {
		return pledgeName;
	}
	
	public String getAssetId() {
		return this.assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	
	public String getPledgeAmount() {
		return this.pledgeAmount;
	}
	
	public void setPledgeAmount(String pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}
	
	public ProjectRecoveryTypeEnum getProjectRecoveryType() {
		return this.projectRecoveryType;
	}
	
	public void setProjectRecoveryType(ProjectRecoveryTypeEnum projectRecoveryType) {
		this.projectRecoveryType = projectRecoveryType;
	}
	
	public PledgeAssetManagementModeEnum getPledgeAssetManagementMode() {
		return this.pledgeAssetManagementMode;
	}
	
	public void setPledgeAssetManagementMode(PledgeAssetManagementModeEnum pledgeAssetManagementMode) {
		this.pledgeAssetManagementMode = pledgeAssetManagementMode;
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
