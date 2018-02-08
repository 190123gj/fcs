/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryDescribeTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-执行-执行内容
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteStuffOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 追偿执行表主键
	 */
	private Long projectRecoveryLitigationExecuteId;
	
	/**
	 * 描述
	 */
	private ProjectRecoveryDescribeTypeEnum describeType;
	
	/**
	 * 内容值
	 */
	private String valueStuff;
	
	// 附件  开始  
	
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	private String recoveryLitigationExecuteStuffMemoUrl;
	
	/** 诉讼顺序 */
	private int litigationIndex;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationExecuteStuffMemoUrl() {
		return this.recoveryLitigationExecuteStuffMemoUrl;
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
	
	public Long getProjectRecoveryLitigationExecuteId() {
		return this.projectRecoveryLitigationExecuteId;
	}
	
	public void setProjectRecoveryLitigationExecuteId(Long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
	}
	
	public void setRecoveryLitigationExecuteStuffMemoUrl(	String recoveryLitigationExecuteStuffMemoUrl) {
		this.recoveryLitigationExecuteStuffMemoUrl = recoveryLitigationExecuteStuffMemoUrl;
	}
	
	public ProjectRecoveryDescribeTypeEnum getDescribeType() {
		return this.describeType;
	}
	
	public void setDescribeType(ProjectRecoveryDescribeTypeEnum describeType) {
		this.describeType = describeType;
	}
	
	public String getValueStuff() {
		return valueStuff;
	}
	
	public void setValueStuff(String valueStuff) {
		this.valueStuff = valueStuff;
	}
	
	public int getLitigationIndex() {
		return this.litigationIndex;
	}
	
	public void setLitigationIndex(int litigationIndex) {
		this.litigationIndex = litigationIndex;
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
