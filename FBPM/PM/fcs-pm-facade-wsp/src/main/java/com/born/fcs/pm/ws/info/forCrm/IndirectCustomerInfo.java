package com.born.fcs.pm.ws.info.forCrm;

import java.io.Serializable;

/** 历史间接客户信息 */
public class IndirectCustomerInfo implements Serializable {
	
	private static final long serialVersionUID = -1984518861430564460L;
	
	/** 客户名 */
	private String indirectCustomerName;
	/** 项目中的角色 */
	private String customerRole;
	
	private String phases;
	
	private String projectCode;
	
	private String projectName;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getIndirectCustomerName() {
		return this.indirectCustomerName;
	}
	
	public void setIndirectCustomerName(String indirectCustomerName) {
		this.indirectCustomerName = indirectCustomerName;
	}
	
	public String getCustomerRole() {
		return this.customerRole;
	}
	
	public void setCustomerRole(String customerRole) {
		this.customerRole = customerRole;
	}
	
	public String getPhases() {
		return this.phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndirectCustomerInfo [indirectCustomerName=");
		builder.append(indirectCustomerName);
		builder.append(", customerRole=");
		builder.append(customerRole);
		builder.append(", phases=");
		builder.append(phases);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append("]");
		return builder.toString();
	}
	
}
