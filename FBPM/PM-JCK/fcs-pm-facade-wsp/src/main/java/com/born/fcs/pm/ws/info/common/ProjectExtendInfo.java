package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;

/**
 * 
 * 项目扩展属性
 *
 * @author wuzj
 *
 */
public class ProjectExtendInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -3969937347897743021L;
	
	private long extendId;
	
	private long formId;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目属性 */
	private ProjectExtendPropertyEnum property;
	
	private String propertyName;
	
	private String propertyValue;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getExtendId() {
		return this.extendId;
	}
	
	public void setExtendId(long extendId) {
		this.extendId = extendId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ProjectExtendPropertyEnum getProperty() {
		return this.property;
	}
	
	public void setProperty(ProjectExtendPropertyEnum property) {
		this.property = property;
	}
	
	public String getPropertyName() {
		return this.propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getPropertyValue() {
		return this.propertyValue;
	}
	
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
}
