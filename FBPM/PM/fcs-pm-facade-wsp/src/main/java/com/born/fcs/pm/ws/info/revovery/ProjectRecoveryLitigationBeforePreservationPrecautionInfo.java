/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLitigationTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryPreservationTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 追偿跟踪表 - 诉讼-诉前保全-保全措施
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationBeforePreservationPrecautionInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 诉前保全表主键
	 */
	private long projectRecoveryLitigationBeforePreservationId;
	
	/**
	 * 诉讼保全表主键
	 */
	private long projectRecoveryLitigationPreservationId;
	
	/**
	 * 保全措施类型
	 */
	private ProjectRecoveryLitigationTypeEnum projectRecoveryLitigationType;
	
	/**
	 * 保全种类
	 */
	private ProjectRecoveryPreservationTypeEnum projectRecoveryPreservationType;
	
	/**
	 * 保全内容
	 */
	private String preservationContent;
	
	/**
	 * 保全期限（起时间）
	 */
	private Date preservationTimeStart;
	
	/**
	 * 保全期限（止时间）
	 */
	private Date preservationTimeEnd;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/** 是否停止通知 */
	private BooleanEnum endNotice;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public BooleanEnum getEndNotice() {
		return this.endNotice;
	}
	
	public void setEndNotice(BooleanEnum endNotice) {
		this.endNotice = endNotice;
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
	
	public long getProjectRecoveryLitigationBeforePreservationId() {
		return projectRecoveryLitigationBeforePreservationId;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationId(	long projectRecoveryLitigationBeforePreservationId) {
		this.projectRecoveryLitigationBeforePreservationId = projectRecoveryLitigationBeforePreservationId;
	}
	
	public long getProjectRecoveryLitigationPreservationId() {
		return projectRecoveryLitigationPreservationId;
	}
	
	public void setProjectRecoveryLitigationPreservationId(	long projectRecoveryLitigationPreservationId) {
		this.projectRecoveryLitigationPreservationId = projectRecoveryLitigationPreservationId;
	}
	
	public ProjectRecoveryPreservationTypeEnum getProjectRecoveryPreservationType() {
		return this.projectRecoveryPreservationType;
	}
	
	public void setProjectRecoveryPreservationType(	ProjectRecoveryPreservationTypeEnum projectRecoveryPreservationType) {
		this.projectRecoveryPreservationType = projectRecoveryPreservationType;
	}
	
	public String getPreservationContent() {
		return preservationContent;
	}
	
	public void setPreservationContent(String preservationContent) {
		this.preservationContent = preservationContent;
	}
	
	public Date getPreservationTimeStart() {
		return preservationTimeStart;
	}
	
	public void setPreservationTimeStart(Date preservationTimeStart) {
		this.preservationTimeStart = preservationTimeStart;
	}
	
	public Date getPreservationTimeEnd() {
		return preservationTimeEnd;
	}
	
	public void setPreservationTimeEnd(Date preservationTimeEnd) {
		this.preservationTimeEnd = preservationTimeEnd;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
	
	public ProjectRecoveryLitigationTypeEnum getProjectRecoveryLitigationType() {
		return this.projectRecoveryLitigationType;
	}
	
	public void setProjectRecoveryLitigationType(	ProjectRecoveryLitigationTypeEnum projectRecoveryLitigationType) {
		this.projectRecoveryLitigationType = projectRecoveryLitigationType;
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
