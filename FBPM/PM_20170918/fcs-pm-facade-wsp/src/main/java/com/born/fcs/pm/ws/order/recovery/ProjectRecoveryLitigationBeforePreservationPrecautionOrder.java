/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLitigationTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryPreservationTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-诉前保全-保全措施
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationBeforePreservationPrecautionOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 诉前保全表主键
	 */
	private Long projectRecoveryLitigationBeforePreservationId;
	
	/**
	 * 诉讼保全表主键
	 */
	private Long projectRecoveryLitigationPreservationId;
	
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
	 * 保全期限（起时间） Date
	 */
	private String preservationTimeStart;
	
	/**
	 * 保全期限（止时间）Date
	 */
	private String preservationTimeEnd;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/** 是否停止通知 */
	private BooleanEnum endNotice;
	
	//========== getters and setters ==========
	
	public ProjectRecoveryPreservationTypeEnum getProjectRecoveryPreservationType() {
		return this.projectRecoveryPreservationType;
	}
	
	public BooleanEnum getEndNotice() {
		return this.endNotice;
	}
	
	public void setEndNotice(BooleanEnum endNotice) {
		this.endNotice = endNotice;
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
	
	public Long getProjectRecoveryLitigationBeforePreservationId() {
		return this.projectRecoveryLitigationBeforePreservationId;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationId(	Long projectRecoveryLitigationBeforePreservationId) {
		this.projectRecoveryLitigationBeforePreservationId = projectRecoveryLitigationBeforePreservationId;
	}
	
	public Long getProjectRecoveryLitigationPreservationId() {
		return this.projectRecoveryLitigationPreservationId;
	}
	
	public void setProjectRecoveryLitigationPreservationId(	Long projectRecoveryLitigationPreservationId) {
		this.projectRecoveryLitigationPreservationId = projectRecoveryLitigationPreservationId;
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
	
	public ProjectRecoveryLitigationTypeEnum getProjectRecoveryLitigationType() {
		return this.projectRecoveryLitigationType;
	}
	
	public void setProjectRecoveryLitigationType(	ProjectRecoveryLitigationTypeEnum projectRecoveryLitigationType) {
		this.projectRecoveryLitigationType = projectRecoveryLitigationType;
	}
	
	public String getPreservationTimeStart() {
		return this.preservationTimeStart;
	}
	
	public void setPreservationTimeStart(String preservationTimeStart) {
		this.preservationTimeStart = preservationTimeStart;
	}
	
	public String getPreservationTimeEnd() {
		return this.preservationTimeEnd;
	}
	
	public void setPreservationTimeEnd(String preservationTimeEnd) {
		this.preservationTimeEnd = preservationTimeEnd;
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
