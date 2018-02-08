package com.born.fcs.pm.ws.info.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class FRefundApplicationInfo extends FormVOInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5971105849978549612L;
	/**
	 * 表单信息
	 */
	private long formId;
	
	private FormCodeEnum formCode;
	
	private String formName;
	
	private FormStatusEnum formStatus;
	
	private String detailStatus; //流程步骤状态
	
	private long actInstId; //流程实例ID
	
	private long runId;
	
	private long taskId;
	
	private long formUserId;
	
	private String formUserAccount;
	
	private String formUserName;
	
	private long formDeptId;
	
	private String formDeptName;
	
	private String checkStatus;
	
	private String checkName;
	
	private Date formAddTime;
	
	private Date formUpdateTime;
	
	private Date submitTime;
	
	private Date finishTime;
	//
	private long refundId;
	
	private String projectCode;
	
	private String projectName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money refundAmount = new Money(0, 0);
	
	private String attach;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FRefundApplicationFeeInfo> feeInfos;
	
	public FormCodeEnum getFormCode() {
		return formCode;
	}
	
	public void setFormCode(FormCodeEnum formCode) {
		this.formCode = formCode;
	}
	
	public String getFormName() {
		return formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public FormStatusEnum getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public String getDetailStatus() {
		return detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public long getActInstId() {
		return actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public long getRunId() {
		return runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getFormUserId() {
		return formUserId;
	}
	
	public void setFormUserId(long formUserId) {
		this.formUserId = formUserId;
	}
	
	public String getFormUserAccount() {
		return formUserAccount;
	}
	
	public void setFormUserAccount(String formUserAccount) {
		this.formUserAccount = formUserAccount;
	}
	
	public String getFormUserName() {
		return formUserName;
	}
	
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	
	public long getFormDeptId() {
		return formDeptId;
	}
	
	public void setFormDeptId(long formDeptId) {
		this.formDeptId = formDeptId;
	}
	
	public String getFormDeptName() {
		return formDeptName;
	}
	
	public void setFormDeptName(String formDeptName) {
		this.formDeptName = formDeptName;
	}
	
	public String getCheckStatus() {
		return checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getCheckName() {
		return checkName;
	}
	
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	public Date getFormAddTime() {
		return formAddTime;
	}
	
	public void setFormAddTime(Date formAddTime) {
		this.formAddTime = formAddTime;
	}
	
	public Date getFormUpdateTime() {
		return formUpdateTime;
	}
	
	public void setFormUpdateTime(Date formUpdateTime) {
		this.formUpdateTime = formUpdateTime;
	}
	
	public Date getSubmitTime() {
		return submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public Date getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public long getRefundId() {
		return refundId;
	}
	
	public void setRefundId(long refundId) {
		this.refundId = refundId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
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
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<FRefundApplicationFeeInfo> getFeeInfos() {
		return feeInfos;
	}
	
	public void setFeeInfos(List<FRefundApplicationFeeInfo> feeInfos) {
		this.feeInfos = feeInfos;
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

	public Money getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
	}
	
}
