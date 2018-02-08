package com.born.fcs.pm.ws.order.forCrm;

import com.born.fcs.pm.ws.base.QueryPageBase;

/** 查询历史间接客户 */
public class IndirectCustomerQueryOrder extends QueryPageBase {
	private static final long serialVersionUID = 4288798450101631735L;
	
	private String projectCode;
	/** 客户名 */
	private String indirectCustomerName;
	/** 项目中的角色 */
	private String customerRole;
	
	private String phases;
	
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryIndirectCustomer [projectCode=");
		builder.append(projectCode);
		builder.append(", indirectCustomerName=");
		builder.append(indirectCustomerName);
		builder.append(", customerRole=");
		builder.append(customerRole);
		builder.append(", phases=");
		builder.append(phases);
		builder.append("]");
		return builder.toString();
	}
	
}
