/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationAmountDetailInfo extends BaseToStringInfo {
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
	 * 回收金额
	 */
	private Money recoveryAmount = new Money(0, 0);
	
	/**
	 * 时间
	 */
	private Date recoveryTime;
	
	/**
	 * 标的物
	 */
	private String recoveryGoods;
	
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
	
	public long getProjectRecoveryLitigationExecuteId() {
		return projectRecoveryLitigationExecuteId;
	}
	
	public ProjectRecoveryTypeEnum getProjectRecoveryType() {
		return this.projectRecoveryType;
	}
	
	public void setProjectRecoveryType(ProjectRecoveryTypeEnum projectRecoveryType) {
		this.projectRecoveryType = projectRecoveryType;
	}
	
	public void setProjectRecoveryLitigationExecuteId(long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
	}
	
	public Money getRecoveryAmount() {
		return recoveryAmount;
	}
	
	public void setRecoveryAmount(Money recoveryAmount) {
		if (recoveryAmount == null) {
			this.recoveryAmount = new Money(0, 0);
		} else {
			this.recoveryAmount = recoveryAmount;
		}
	}
	
	public Date getRecoveryTime() {
		return recoveryTime;
	}
	
	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	
	public String getRecoveryGoods() {
		return recoveryGoods;
	}
	
	public void setRecoveryGoods(String recoveryGoods) {
		this.recoveryGoods = recoveryGoods;
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
