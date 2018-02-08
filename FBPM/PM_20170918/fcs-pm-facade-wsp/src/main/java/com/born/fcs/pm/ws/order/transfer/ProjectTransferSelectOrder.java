package com.born.fcs.pm.ws.order.transfer;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 选择和移交项目
 * @author wuzj
 */
public class ProjectTransferSelectOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -6037948966390775327L;
	
	private String projectCode; //项目编号
	private String projectName; //项目名称
	private long customerId; //客户ID
	private String customerName; //客户名称
	private String busiType;//业务品种
	private String busiTypeName;//业务品种
	/** 不包含的项目编号 */
	private List<String> excludeProjects;
	
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
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public List<String> getExcludeProjects() {
		return this.excludeProjects;
	}
	
	public void setExcludeProjects(List<String> excludeProjects) {
		this.excludeProjects = excludeProjects;
	}
}
