package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;

/**
 * 项目扩展信息
 *
 *
 * @author wuzj
 *
 */
public class ProjectExtendQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 2556706168503992654L;
	
	private Long extendId;
	
	private Long formId;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目属性 */
	private ProjectExtendPropertyEnum property;
	
	private String propertyValue;
	
	private String remark;
	
	public Long getExtendId() {
		return this.extendId;
	}
	
	public void setExtendId(Long extendId) {
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
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
}
