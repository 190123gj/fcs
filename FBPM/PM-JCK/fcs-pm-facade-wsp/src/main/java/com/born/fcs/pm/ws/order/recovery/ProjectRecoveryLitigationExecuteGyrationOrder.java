/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-执行回转
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteGyrationOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	
	/*** 诉讼-执行回转-默认附件-附件 **/
	private String recoveryLitigationExecuteGyrationMemoUrl;
	
	//========== getters and setters ==========
	
	public String getRecoveryLitigationExecuteGyrationMemoUrl() {
		return this.recoveryLitigationExecuteGyrationMemoUrl;
	}
	
	public void setRecoveryLitigationExecuteGyrationMemoUrl(String recoveryLitigationExecuteGyrationMemoUrl) {
		this.recoveryLitigationExecuteGyrationMemoUrl = recoveryLitigationExecuteGyrationMemoUrl;
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
