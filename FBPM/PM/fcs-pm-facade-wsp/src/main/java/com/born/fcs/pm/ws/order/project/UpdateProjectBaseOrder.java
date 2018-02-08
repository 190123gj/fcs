package com.born.fcs.pm.ws.order.project;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 更新项目
 * 
 * @author lirz
 * 
 * 2016-9-26 下午4:21:15
 * 
 */
public class UpdateProjectBaseOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -7756237287681876357L;
	
	private String projectCode;
	
	public void check() {
		validateHasText(projectCode, "项目编号");
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
