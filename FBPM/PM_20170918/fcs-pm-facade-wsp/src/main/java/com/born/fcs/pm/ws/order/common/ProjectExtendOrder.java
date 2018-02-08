package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 项目扩展信息
 *
 *
 * @author wuzj
 *
 */
public class ProjectExtendOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 4236444408362642371L;
	
	private Long extendId;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目属性 */
	private ProjectExtendPropertyEnum property;
	
	private String propertyValue;
	
	private String remark;
	
	/** 是否更新已经存在的 ，不存在则新增，存在则更新 */
	private boolean isUpdateOld;
	
	/** 是否只允许同样的一个,不存在则新增，存在则抛出异常 */
	private boolean isOnlyOne;
	
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
	
	public boolean isUpdateOld() {
		return this.isUpdateOld;
	}
	
	public void setUpdateOld(boolean isUpdateOld) {
		this.isUpdateOld = isUpdateOld;
	}
	
	public boolean isOnlyOne() {
		return this.isOnlyOne;
	}
	
	public void setOnlyOne(boolean isOnlyOne) {
		this.isOnlyOne = isOnlyOne;
	}
	
}
