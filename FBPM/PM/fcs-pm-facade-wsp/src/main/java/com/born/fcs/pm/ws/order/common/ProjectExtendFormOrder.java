package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 项目扩展信息
 *
 *
 * @author wuzj
 *
 */
public class ProjectExtendFormOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 4236444408362642371L;
	
	private Long extendId;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目属性 */
	private ProjectExtendPropertyEnum property;
	
	private String propertyValue;
	
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
		validateNotNull(property, "项目属性");
		validateNotNull(propertyValue, "项目属性值");
	}
	
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
	
}
