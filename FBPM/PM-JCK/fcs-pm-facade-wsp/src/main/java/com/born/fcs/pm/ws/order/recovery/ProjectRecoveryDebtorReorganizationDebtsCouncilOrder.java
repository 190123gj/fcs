/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryDebtorReorganizationDebtsCouncilOrder extends ProcessOrder {
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
	 * 债权人会议描述
	 */
	private String debtsCouncilDescribe;
	
	/**
	 * 债权人会议日期 Date
	 */
	private String debtsCouncilTime;
	
	//========== getters and setters ==========
	
	public String getDebtsCouncilDescribe() {
		return debtsCouncilDescribe;
	}
	
	public void setDebtsCouncilDescribe(String debtsCouncilDescribe) {
		this.debtsCouncilDescribe = debtsCouncilDescribe;
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
	
	public String getDebtsCouncilTime() {
		return this.debtsCouncilTime;
	}
	
	public void setDebtsCouncilTime(String debtsCouncilTime) {
		this.debtsCouncilTime = debtsCouncilTime;
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
