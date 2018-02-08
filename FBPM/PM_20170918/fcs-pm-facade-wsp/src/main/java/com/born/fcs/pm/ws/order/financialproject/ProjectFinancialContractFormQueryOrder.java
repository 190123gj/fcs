package com.born.fcs.pm.ws.order.financialproject;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 理财项目合同查询
 * @author wuzj
 */
public class ProjectFinancialContractFormQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 6166841882939641506L;
	
	private long contractId;
	
	private long formId;
	
	private String contractStatus;
	
	private String projectCode;
	
	private String productName;
	
	private String issueInstitution;
	
	private String formStatus;
	
	private List<String> formStatusList;
	
	public long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getContractStatus() {
		return this.contractStatus;
	}
	
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
}
