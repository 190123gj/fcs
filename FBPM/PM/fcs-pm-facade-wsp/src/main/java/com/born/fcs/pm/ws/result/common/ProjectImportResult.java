package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 项目导入结果
 *
 * @author wuzj
 *
 */
public class ProjectImportResult extends FormBaseResult {
	
	private static final long serialVersionUID = -4216028734417959442L;
	
	/** 导入后项目编号 */
	private String projectCode;
	/** 导入后项目名称 */
	private String projectName;
	/** 导入后客户名称 */
	private String customerName;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
