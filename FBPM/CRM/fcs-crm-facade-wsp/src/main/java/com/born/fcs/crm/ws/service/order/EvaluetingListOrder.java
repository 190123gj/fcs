package com.born.fcs.crm.ws.service.order;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 评价结果
 * */
public class EvaluetingListOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -6222474685531514864L;
	/** 主键 */
	private long id;
	/** 审核表单Id */
	private long formId;
	/** 关联项目Id */
	private String projectCode;
	/** 客户Id */
	private String customerId;
	/** 客户名称 */
	private String customerName;
	/** 营业执照号码 */
	private String busiLicenseNo;
	/** 客户等级 */
	private String level;
	/** 评级年限:如 2016 */
	private String year;
	/** 审定日期 */
	private Date auditTime;
	/** 操作人 */
	private String operator;
	/** 评级状态 */
	private String auditStatus;
	/** 评价方式：内部/外部 */
	private String evaluetingType;
	/** 被公司提起或准备提起法律诉讼的客户 */
	private String isProsecute;
	/** 外部_评级机构 */
	private String evaluetingInstitutions;
	/** 外部_评级日期 */
	private Date evaluetingTime;
	/** 外部_评级图片 */
	private String auditImg;
	/** 审核意见1 */
	private String auditOpinion1;
	/** 审核意见2 */
	private String auditOpinion2;
	/** 审核意见3 */
	private String auditOpinion3;
	/** 审核意见4 */
	private String auditOpinion4;
	/** 审核意见5 */
	private String auditOpinion5;
	/** 审核意见6 */
	private String auditOpinion6;
	/** 创建时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 评级页签完整性 */
	private String editStatus;
	
	public String getEditStatus() {
		return this.editStatus;
	}
	
	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getAuditOpinion4() {
		return this.auditOpinion4;
	}
	
	public void setAuditOpinion4(String auditOpinion4) {
		this.auditOpinion4 = auditOpinion4;
	}
	
	public String getAuditOpinion5() {
		return this.auditOpinion5;
	}
	
	public void setAuditOpinion5(String auditOpinion5) {
		this.auditOpinion5 = auditOpinion5;
	}
	
	public String getAuditOpinion6() {
		return this.auditOpinion6;
	}
	
	public void setAuditOpinion6(String auditOpinion6) {
		this.auditOpinion6 = auditOpinion6;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public String getOperator() {
		return this.operator;
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
	
	public String getAuditOpinion1() {
		return this.auditOpinion1;
	}
	
	public void setAuditOpinion1(String auditOpinion1) {
		this.auditOpinion1 = auditOpinion1;
	}
	
	public String getAuditOpinion2() {
		return this.auditOpinion2;
	}
	
	public void setAuditOpinion2(String auditOpinion2) {
		this.auditOpinion2 = auditOpinion2;
	}
	
	public String getAuditOpinion3() {
		return this.auditOpinion3;
	}
	
	public void setAuditOpinion3(String auditOpinion3) {
		this.auditOpinion3 = auditOpinion3;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getEvaluetingInstitutions() {
		return this.evaluetingInstitutions;
	}
	
	public void setEvaluetingInstitutions(String evaluetingInstitutions) {
		this.evaluetingInstitutions = evaluetingInstitutions;
	}
	
	public Date getEvaluetingTime() {
		return this.evaluetingTime;
	}
	
	public void setEvaluetingTime(Date evaluetingTime) {
		this.evaluetingTime = evaluetingTime;
	}
	
	public String getAuditImg() {
		return this.auditImg;
	}
	
	public void setAuditImg(String auditImg) {
		this.auditImg = auditImg;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvaluetingListOrder [id=");
		builder.append(id);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", projectCode=");
		builder.append(projectCode);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", busiLicenseNo=");
		builder.append(busiLicenseNo);
		builder.append(", level=");
		builder.append(level);
		builder.append(", year=");
		builder.append(year);
		builder.append(", auditTime=");
		builder.append(auditTime);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", auditStatus=");
		builder.append(auditStatus);
		builder.append(", evaluetingType=");
		builder.append(evaluetingType);
		builder.append(", isProsecute=");
		builder.append(isProsecute);
		builder.append(", evaluetingInstitutions=");
		builder.append(evaluetingInstitutions);
		builder.append(", evaluetingTime=");
		builder.append(evaluetingTime);
		builder.append(", auditImg=");
		builder.append(auditImg);
		builder.append(", auditOpinion1=");
		builder.append(auditOpinion1);
		builder.append(", auditOpinion2=");
		builder.append(auditOpinion2);
		builder.append(", auditOpinion3=");
		builder.append(auditOpinion3);
		builder.append(", auditOpinion4=");
		builder.append(auditOpinion4);
		builder.append(", auditOpinion5=");
		builder.append(auditOpinion5);
		builder.append(", auditOpinion6=");
		builder.append(auditOpinion6);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append("]");
		return builder.toString();
	}
	
}
