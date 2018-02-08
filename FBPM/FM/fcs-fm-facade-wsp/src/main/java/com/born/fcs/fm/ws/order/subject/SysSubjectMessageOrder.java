/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.ws.order.subject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectTypeEnum;
import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 默认科目信息维护
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysSubjectMessageOrder extends FcsQueryPageBase {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	private SubjectTypeEnum subjectType;
	
	private SubjectCostTypeEnum subjectCostType;
	
	private String atCode;
	
	private String atName;
	
	private String adjustProject;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public SubjectTypeEnum getSubjectType() {
		return this.subjectType;
	}
	
	public void setSubjectType(SubjectTypeEnum subjectType) {
		this.subjectType = subjectType;
	}
	
	public SubjectCostTypeEnum getSubjectCostType() {
		return this.subjectCostType;
	}
	
	public void setSubjectCostType(SubjectCostTypeEnum subjectCostType) {
		this.subjectCostType = subjectCostType;
	}
	
	public String getAtCode() {
		return this.atCode;
	}
	
	public void setAtCode(String atCode) {
		this.atCode = atCode;
	}
	
	public String getAtName() {
		return this.atName;
	}
	
	public void setAtName(String atName) {
		this.atName = atName;
	}
	
	public String getAdjustProject() {
		return adjustProject;
	}
	
	public void setAdjustProject(String adjustProject) {
		this.adjustProject = adjustProject;
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
