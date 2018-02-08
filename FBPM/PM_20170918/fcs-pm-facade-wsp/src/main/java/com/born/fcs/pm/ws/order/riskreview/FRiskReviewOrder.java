package com.born.fcs.pm.ws.order.riskreview;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 风险审查报告
 *
 *
 * @author Fei
 *
 */
public class FRiskReviewOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -1371929205037013986L;
	
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
	
	private String caseInfo;
	
	private String preserveContent;
	
	private String auditOpinion;
	
	private String synthesizeOpinion;
	
	public String getReportContent() {
		return this.reportContent;
	}
	
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
		
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
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCaseInfo() {
		return this.caseInfo;
	}
	
	public void setCaseInfo(String caseInfo) {
		this.caseInfo = caseInfo;
	}
	
	public String getPreserveContent() {
		return this.preserveContent;
	}
	
	public void setPreserveContent(String preserveContent) {
		this.preserveContent = preserveContent;
	}
	
	public String getAuditOpinion() {
		return this.auditOpinion;
	}
	
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	public String getSynthesizeOpinion() {
		return this.synthesizeOpinion;
	}
	
	public void setSynthesizeOpinion(String synthesizeOpinion) {
		this.synthesizeOpinion = synthesizeOpinion;
	}
	
}
