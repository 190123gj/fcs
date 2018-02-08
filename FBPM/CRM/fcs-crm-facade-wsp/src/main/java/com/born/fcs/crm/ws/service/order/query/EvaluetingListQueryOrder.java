package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/** 评价结果查询 */
public class EvaluetingListQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 8359410219605331258L;
	/** 客户ID */
	private String customerId;
	/** 客户名称 */
	private String customerName;
	/** 营业执照号码 */
	private String busiLicenseNo;
	/** 客户名称模糊 */
	private String likeCustomerName;
	/** 营业执照号码模糊 */
	private String likeBusiLicenseNo;
	/** 客户等级 */
	private String level;
	/** 操作人 */
	private String operator;
	/** 评级状态 */
	private String auditStatus;
	/** 评价方式：内部/外部 */
	private String evaluetingType;
	/** 被公司提起或准备提起法律诉讼的客户 IS/NO */
	private String isProsecute;
	/** 关联项目Id */
	private String projectCode;
	/** 表单Id */
	private long formId;
	/** 表单状态 */
	private String status;
	
	/** 评级年度 如：2016 */
	private String year;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiLicenseNo() {
		return this.busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public String getLikeCustomerName() {
		return this.likeCustomerName;
	}
	
	public void setLikeCustomerName(String likeCustomerName) {
		this.likeCustomerName = likeCustomerName;
	}
	
	public String getLikeBusiLicenseNo() {
		return this.likeBusiLicenseNo;
	}
	
	public void setLikeBusiLicenseNo(String likeBusiLicenseNo) {
		this.likeBusiLicenseNo = likeBusiLicenseNo;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getEvaluetingType() {
		return this.evaluetingType;
	}
	
	public void setEvaluetingType(String evaluetingType) {
		this.evaluetingType = evaluetingType;
	}
	
	public String getIsProsecute() {
		return this.isProsecute;
	}
	
	public void setIsProsecute(String isProsecute) {
		this.isProsecute = isProsecute;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingListQueryOrder [customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", busiLicenseNo=");
		builder.append(busiLicenseNo);
		builder.append(", likeCustomerName=");
		builder.append(likeCustomerName);
		builder.append(", likeBusiLicenseNo=");
		builder.append(likeBusiLicenseNo);
		builder.append(", level=");
		builder.append(level);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", auditStatus=");
		builder.append(auditStatus);
		builder.append(", evaluetingType=");
		builder.append(evaluetingType);
		builder.append(", isProsecute=");
		builder.append(isProsecute);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", year=");
		builder.append(year);
		builder.append("]");
		return builder.toString();
	}
	
}
