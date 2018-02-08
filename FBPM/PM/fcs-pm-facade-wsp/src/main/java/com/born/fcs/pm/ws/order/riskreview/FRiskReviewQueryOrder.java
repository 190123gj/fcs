package com.born.fcs.pm.ws.order.riskreview;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

public class FRiskReviewQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 4061019352846230341L;
	
	private long id;
	/**
	 * 对应项目编号
	 */
	private String projectCode;
	/**
	 * 对应项目名称
	 */
	private String projectName;
	/**
	 * 对应客户ID
	 */
	private long customerId;
	/**
	 * 对应客户名称
	 */
	private String customerName;
	/**
	 * 风险审查报告内容
	 */
	private String reportContent;
	
	private String formStatus; // 表单状态
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public String getReportContent() {
		return this.reportContent;
	}
	
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	
	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FRiskReviewQueryOrder [id=");
		builder.append(id);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", reportContent=");
		builder.append(reportContent);
		builder.append("]");
		return builder.toString();
	}
	
}
