package com.born.fcs.pm.ws.order.fund;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryFormBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

public class LoanUseApplyQueryOrder extends QueryFormBase {
	
	private static final long serialVersionUID = -7895794175857393273L;
	
	private Long formId;
	
	private Long applyId;
	
	private Long customerId;
	
	private String formStatus;
	
	private List<String> statusList;
	
	private String projectCode;
	
	private String busiType;
	
	private String projectName;
	
	private String customerName;
	
	private BooleanEnum hasReceipt;
	
	@Override
	public String getSortCol() {
		if (super.getSortCol() == null || "".equals(super.getSortCol())) {
			return "f.form_id";
		}
		return super.getSortCol();
	}
	
	@Override
	public String getSortOrder() {
		if (super.getSortOrder() == null || "".equals(super.getSortOrder())) {
			return "DESC";
		}
		return super.getSortOrder();
	}
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
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
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public BooleanEnum getHasReceipt() {
		return this.hasReceipt;
	}
	
	public void setHasReceipt(BooleanEnum hasReceipt) {
		this.hasReceipt = hasReceipt;
	}
	
}
