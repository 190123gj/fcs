/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 追偿跟踪表 - 诉讼-执行回转
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteGyrationInfo extends ProjectRecoveryLitigationBaseInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	/*** 诉讼-执行回转-默认附件-附件 **/
	private String recoveryLitigationExecuteGyrationMemoUrl;
	/*** 诉讼-执行回转-默认附件-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteGyrationMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationExecuteGyrationMemoUrl() {
		return this.recoveryLitigationExecuteGyrationMemoUrl;
	}
	
	public void setRecoveryLitigationExecuteGyrationMemoUrl(String recoveryLitigationExecuteGyrationMemoUrl) {
		this.recoveryLitigationExecuteGyrationMemoUrl = recoveryLitigationExecuteGyrationMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteGyrationMemo() {
		return this.recoveryLitigationExecuteGyrationMemo;
	}
	
	public void setRecoveryLitigationExecuteGyrationMemo(	List<CommonAttachmentInfo> recoveryLitigationExecuteGyrationMemo) {
		this.recoveryLitigationExecuteGyrationMemo = recoveryLitigationExecuteGyrationMemo;
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
