package com.born.fcs.pm.ws.order.transfer;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectTransferTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目移交明细Order
 * @author wuzj
 */
public class ProjectTransferDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2320890106284173793L;
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 业务类型 */
	private String busiType;
	private String busiTypeName;
	/** 移交时项目阶段 */
	private ProjectPhasesEnum projectPhase;
	/** 移交时项目状态 */
	private ProjectStatusEnum projectStatus;
	/** 项目授信金额 */
	private Money projectAmount = new Money(0, 0);
	/** 移交时在保余额 */
	private Money projectBalance = new Money(0, 0);
	/** 移交申请人ID */
	private long applyUserId;
	/** 移交申请人账号 */
	private String applyUserAccount;
	/** 移交申请人名称 */
	private String applyUserName;
	/** 项目移交状态 */
	private ProjectTransferStatusEnum transferStatus;
	/** 移交类型 */
	private ProjectTransferTypeEnum transferType;
	/** 移交完成时间 */
	private Date transferTime;
	/** 原始人员 */
	private long originalUserId;
	private String originalUserAccount;
	private String originalUserName;
	/** 原始人员部门 */
	private long originalUserDeptId;
	private String originalUserDeptName;
	/** 原始人员B角 */
	private long originalUserbId;
	private String originalUserbAccount;
	private String originalUserbName;
	/** 原始人员B角部门 */
	private long originalUserbDeptId;
	private String originalUserbDeptName;
	/** 接受人员 */
	private long acceptUserId;
	private String acceptUserAccount;
	private String acceptUserName;
	/** 接受人员部门 */
	private long acceptUserDeptId;
	private String acceptUserDeptName;
	
	/** 接受人员B门 */
	private long acceptUserbId;
	private String acceptUserbAccount;
	private String acceptUserbName;
	/** 接受人员B部门 */
	private long acceptUserbDeptId;
	private String acceptUserbDeptName;
	
	/** 移交文件（附件） */
	private String transferFile;
	/** 决策依据（签报formId） */
	private String formChange;
	/** 决策依据（风险处置会议纪要-formId,spId,风险处置会纪要编号） */
	private String riskCouncilSummary;
	/** 决策依据（项目批复-项目编号） */
	private String projectApproval;
	/** 备注 */
	private String remark;
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
	}
	
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
	
	public ProjectPhasesEnum getProjectPhase() {
		return this.projectPhase;
	}
	
	public void setProjectPhase(ProjectPhasesEnum projectPhase) {
		this.projectPhase = projectPhase;
	}
	
	public ProjectStatusEnum getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public Money getProjectAmount() {
		return this.projectAmount;
	}
	
	public void setProjectAmount(Money projectAmount) {
		this.projectAmount = projectAmount;
	}
	
	public Money getProjectBalance() {
		return this.projectBalance;
	}
	
	public void setProjectBalance(Money projectBalance) {
		this.projectBalance = projectBalance;
	}
	
	public long getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return this.applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return this.applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public ProjectTransferStatusEnum getTransferStatus() {
		return this.transferStatus;
	}
	
	public void setTransferStatus(ProjectTransferStatusEnum transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	public ProjectTransferTypeEnum getTransferType() {
		return this.transferType;
	}
	
	public void setTransferType(ProjectTransferTypeEnum transferType) {
		this.transferType = transferType;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public long getOriginalUserId() {
		return this.originalUserId;
	}
	
	public void setOriginalUserId(long originalUserId) {
		this.originalUserId = originalUserId;
	}
	
	public String getOriginalUserAccount() {
		return this.originalUserAccount;
	}
	
	public void setOriginalUserAccount(String originalUserAccount) {
		this.originalUserAccount = originalUserAccount;
	}
	
	public String getOriginalUserName() {
		return this.originalUserName;
	}
	
	public void setOriginalUserName(String originalUserName) {
		this.originalUserName = originalUserName;
	}
	
	public long getOriginalUserDeptId() {
		return this.originalUserDeptId;
	}
	
	public void setOriginalUserDeptId(long originalUserDeptId) {
		this.originalUserDeptId = originalUserDeptId;
	}
	
	public String getOriginalUserDeptName() {
		return this.originalUserDeptName;
	}
	
	public void setOriginalUserDeptName(String originalUserDeptName) {
		this.originalUserDeptName = originalUserDeptName;
	}
	
	public long getOriginalUserbId() {
		return this.originalUserbId;
	}
	
	public void setOriginalUserbId(long originalUserbId) {
		this.originalUserbId = originalUserbId;
	}
	
	public String getOriginalUserbAccount() {
		return this.originalUserbAccount;
	}
	
	public void setOriginalUserbAccount(String originalUserbAccount) {
		this.originalUserbAccount = originalUserbAccount;
	}
	
	public String getOriginalUserbName() {
		return this.originalUserbName;
	}
	
	public void setOriginalUserbName(String originalUserbName) {
		this.originalUserbName = originalUserbName;
	}
	
	public long getOriginalUserbDeptId() {
		return this.originalUserbDeptId;
	}
	
	public void setOriginalUserbDeptId(long originalUserbDeptId) {
		this.originalUserbDeptId = originalUserbDeptId;
	}
	
	public String getOriginalUserbDeptName() {
		return this.originalUserbDeptName;
	}
	
	public void setOriginalUserbDeptName(String originalUserbDeptName) {
		this.originalUserbDeptName = originalUserbDeptName;
	}
	
	public long getAcceptUserId() {
		return this.acceptUserId;
	}
	
	public void setAcceptUserId(long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	
	public String getAcceptUserAccount() {
		return this.acceptUserAccount;
	}
	
	public void setAcceptUserAccount(String acceptUserAccount) {
		this.acceptUserAccount = acceptUserAccount;
	}
	
	public String getAcceptUserName() {
		return this.acceptUserName;
	}
	
	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}
	
	public long getAcceptUserDeptId() {
		return this.acceptUserDeptId;
	}
	
	public void setAcceptUserDeptId(long acceptUserDeptId) {
		this.acceptUserDeptId = acceptUserDeptId;
	}
	
	public String getAcceptUserDeptName() {
		return this.acceptUserDeptName;
	}
	
	public void setAcceptUserDeptName(String acceptUserDeptName) {
		this.acceptUserDeptName = acceptUserDeptName;
	}
	
	public long getAcceptUserbId() {
		return this.acceptUserbId;
	}
	
	public void setAcceptUserbId(long acceptUserbId) {
		this.acceptUserbId = acceptUserbId;
	}
	
	public String getAcceptUserbAccount() {
		return this.acceptUserbAccount;
	}
	
	public void setAcceptUserbAccount(String acceptUserbAccount) {
		this.acceptUserbAccount = acceptUserbAccount;
	}
	
	public String getAcceptUserbName() {
		return this.acceptUserbName;
	}
	
	public void setAcceptUserbName(String acceptUserbName) {
		this.acceptUserbName = acceptUserbName;
	}
	
	public long getAcceptUserbDeptId() {
		return this.acceptUserbDeptId;
	}
	
	public void setAcceptUserbDeptId(long acceptUserbDeptId) {
		this.acceptUserbDeptId = acceptUserbDeptId;
	}
	
	public String getAcceptUserbDeptName() {
		return this.acceptUserbDeptName;
	}
	
	public void setAcceptUserbDeptName(String acceptUserbDeptName) {
		this.acceptUserbDeptName = acceptUserbDeptName;
	}
	
	public String getTransferFile() {
		return this.transferFile;
	}
	
	public void setTransferFile(String transferFile) {
		this.transferFile = transferFile;
	}
	
	public String getFormChange() {
		return this.formChange;
	}
	
	public void setFormChange(String formChange) {
		this.formChange = formChange;
	}
	
	public String getRiskCouncilSummary() {
		return this.riskCouncilSummary;
	}
	
	public void setRiskCouncilSummary(String riskCouncilSummary) {
		this.riskCouncilSummary = riskCouncilSummary;
	}
	
	public String getProjectApproval() {
		return this.projectApproval;
	}
	
	public void setProjectApproval(String projectApproval) {
		this.projectApproval = projectApproval;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
