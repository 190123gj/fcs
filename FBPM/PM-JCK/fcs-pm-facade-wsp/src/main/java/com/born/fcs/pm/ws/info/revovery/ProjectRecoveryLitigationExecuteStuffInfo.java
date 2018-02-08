/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryDescribeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 追偿跟踪表 - 诉讼-执行-执行内容
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteStuffInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 追偿执行表主键
	 */
	private long projectRecoveryLitigationExecuteId;
	
	/**
	 * 描述
	 */
	private ProjectRecoveryDescribeTypeEnum describeType;
	
	/**
	 * 内容值
	 */
	private String valueStuff;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件  开始  
	
	/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
	private String recoveryLitigationExecuteStuffMemoUrl;
	
	/*** 诉讼-抵质押资产抵债-默认附件-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteStuffMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationExecuteStuffMemoUrl() {
		return this.recoveryLitigationExecuteStuffMemoUrl;
	}
	
	public void setRecoveryLitigationExecuteStuffMemoUrl(	String recoveryLitigationExecuteStuffMemoUrl) {
		this.recoveryLitigationExecuteStuffMemoUrl = recoveryLitigationExecuteStuffMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteStuffMemo() {
		return this.recoveryLitigationExecuteStuffMemo;
	}
	
	public void setRecoveryLitigationExecuteStuffMemo(	List<CommonAttachmentInfo> recoveryLitigationExecuteStuffMemo) {
		this.recoveryLitigationExecuteStuffMemo = recoveryLitigationExecuteStuffMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public long getProjectRecoveryLitigationExecuteId() {
		return projectRecoveryLitigationExecuteId;
	}
	
	public void setProjectRecoveryLitigationExecuteId(long projectRecoveryLitigationExecuteId) {
		this.projectRecoveryLitigationExecuteId = projectRecoveryLitigationExecuteId;
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
