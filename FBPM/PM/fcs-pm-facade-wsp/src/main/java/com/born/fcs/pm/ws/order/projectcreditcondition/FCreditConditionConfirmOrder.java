package com.born.fcs.pm.ws.order.projectcreditcondition;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 授信条件落实情况Order
 *
 *
 * @author Ji
 *
 */
public class FCreditConditionConfirmOrder extends FormOrderBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7930741496298773056L;
	
	private Long confirmId;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private String contractNo;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private Double amount;
	
	private long institutionId;
	
	private String institutionName;
	
	private String isMargin;
	
	private String remark;
	
	private Date rawUpdateTime;
	
	private List<ProjectCreditConditionOrder> projectCreditConditionOrders;
	
	private ProjectCreditMarginOrder projectCreditMarginOrder;
	
	public Long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(Long confirmId) {
		this.confirmId = confirmId;
	}
	
	public Long getFormId() {
		return formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<ProjectCreditConditionOrder> getProjectCreditConditionOrders() {
		return projectCreditConditionOrders;
	}
	
	public void setProjectCreditConditionOrders(List<ProjectCreditConditionOrder> projectCreditConditionOrders) {
		this.projectCreditConditionOrders = projectCreditConditionOrders;
	}
	
	public ProjectCreditMarginOrder getProjectCreditMarginOrder() {
		return projectCreditMarginOrder;
	}
	
	public void setProjectCreditMarginOrder(ProjectCreditMarginOrder projectCreditMarginOrder) {
		this.projectCreditMarginOrder = projectCreditMarginOrder;
	}
	
	public String getIsMargin() {
		return isMargin;
	}
	
	public void setIsMargin(String isMargin) {
		this.isMargin = isMargin;
	}
	
}
