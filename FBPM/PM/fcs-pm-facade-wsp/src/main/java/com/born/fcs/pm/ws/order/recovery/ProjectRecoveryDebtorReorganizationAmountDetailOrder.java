/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/***
 * 
 * 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationAmountDetailOrder extends ProcessOrder {
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
	 * 回收金额 Money
	 */
	private String recoveryAmount;
	
	/**
	 * 时间 Date
	 */
	private String recoveryTime;
	
	/**
	 * 标的物
	 */
	private String recoveryGoods;
	
	//========== getters and setters ==========
	
	public void setProjectRecoveryLitigationExecuteId(long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
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
	
	public String getRecoveryAmount() {
		return this.recoveryAmount;
	}
	
	public void setRecoveryAmount(String recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}
	
	public String getRecoveryTime() {
		return this.recoveryTime;
	}
	
	public void setRecoveryTime(String recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	
	public String getRecoveryGoods() {
		return recoveryGoods;
	}
	
	public ProjectRecoveryTypeEnum getProjectRecoveryType() {
		return this.projectRecoveryType;
	}
	
	public void setProjectRecoveryType(ProjectRecoveryTypeEnum projectRecoveryType) {
		this.projectRecoveryType = projectRecoveryType;
	}
	
	public void setRecoveryGoods(String recoveryGoods) {
		this.recoveryGoods = recoveryGoods;
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
