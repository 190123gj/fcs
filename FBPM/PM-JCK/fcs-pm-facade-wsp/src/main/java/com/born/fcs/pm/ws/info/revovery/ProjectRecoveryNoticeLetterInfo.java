/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ProjectRecoveryLetterStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLetterTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 追偿通知函
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryNoticeLetterInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表id
	 */
	private long projectRecoveryId;
	
	/**
	 * 函件类型
	 */
	private ProjectRecoveryLetterTypeEnum letterType;
	
	/**
	 * 内容[可填写]
	 */
	private String content;
	
	/**
	 * 内容 [不可填写]
	 */
	private String contentMessage;
	
	/**
	 * 函件状态
	 */
	private ProjectRecoveryLetterStatusEnum letterStatus;
	
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContentMessage() {
		return contentMessage;
	}
	
	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	
	public ProjectRecoveryLetterTypeEnum getLetterType() {
		return this.letterType;
	}
	
	public void setLetterType(ProjectRecoveryLetterTypeEnum letterType) {
		this.letterType = letterType;
	}
	
	public ProjectRecoveryLetterStatusEnum getLetterStatus() {
		return this.letterStatus;
	}
	
	public void setLetterStatus(ProjectRecoveryLetterStatusEnum letterStatus) {
		this.letterStatus = letterStatus;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectRecoveryNoticeLetterInfo [id=");
		builder.append(id);
		builder.append(", projectRecoveryId=");
		builder.append(projectRecoveryId);
		builder.append(", letterType=");
		builder.append(letterType);
		builder.append(", letterStatus=");
		builder.append(letterStatus);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append("]");
		return builder.toString();
	}
	
}
