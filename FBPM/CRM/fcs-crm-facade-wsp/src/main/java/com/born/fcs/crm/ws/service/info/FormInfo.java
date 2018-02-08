package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;

public class FormInfo implements Serializable {
	private static final long serialVersionUID = -3230590546230414977L;
	
	private long formId;
	
	private String formCode;
	
	private String formName;
	
	private String formUrl;
	
	private long actInstId;
	
	private String actDefId;
	
	private long defId;
	
	private long runId;
	
	private long taskId;
	
	private String status;
	
	private String detailStatus;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String userMobile;
	
	private String userEmail;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	private String checkStatus;
	
	private Date submitTime;
	
	private Date finishTime;
	
	private String relatedProjectCode;
	
	private String taskUserData;
	
	private String trace;
	
	private String remark;
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getFormCode() {
		return this.formCode;
	}
	
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	
	public String getFormName() {
		return this.formName;
	}
	
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public String getFormUrl() {
		return this.formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public long getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(long actInstId) {
		this.actInstId = actInstId;
	}
	
	public String getActDefId() {
		return this.actDefId;
	}
	
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}
	
	public long getDefId() {
		return this.defId;
	}
	
	public void setDefId(long defId) {
		this.defId = defId;
	}
	
	public long getRunId() {
		return this.runId;
	}
	
	public void setRunId(long runId) {
		this.runId = runId;
	}
	
	public long getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDetailStatus() {
		return this.detailStatus;
	}
	
	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public Date getSubmitTime() {
		return this.submitTime;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
	
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getRelatedProjectCode() {
		return this.relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public String getTaskUserData() {
		return this.taskUserData;
	}
	
	public void setTaskUserData(String taskUserData) {
		this.taskUserData = taskUserData;
	}
	
	public String getTrace() {
		return this.trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormInfo [formId=");
		builder.append(formId);
		builder.append(", formCode=");
		builder.append(formCode);
		builder.append(", formName=");
		builder.append(formName);
		builder.append(", formUrl=");
		builder.append(formUrl);
		builder.append(", actInstId=");
		builder.append(actInstId);
		builder.append(", actDefId=");
		builder.append(actDefId);
		builder.append(", defId=");
		builder.append(defId);
		builder.append(", runId=");
		builder.append(runId);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", detailStatus=");
		builder.append(detailStatus);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userAccount=");
		builder.append(userAccount);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userMobile=");
		builder.append(userMobile);
		builder.append(", userEmail=");
		builder.append(userEmail);
		builder.append(", deptId=");
		builder.append(deptId);
		builder.append(", deptCode=");
		builder.append(deptCode);
		builder.append(", deptName=");
		builder.append(deptName);
		builder.append(", deptPath=");
		builder.append(deptPath);
		builder.append(", deptPathName=");
		builder.append(deptPathName);
		builder.append(", checkStatus=");
		builder.append(checkStatus);
		builder.append(", submitTime=");
		builder.append(submitTime);
		builder.append(", finishTime=");
		builder.append(finishTime);
		builder.append(", relatedProjectCode=");
		builder.append(relatedProjectCode);
		builder.append(", taskUserData=");
		builder.append(taskUserData);
		builder.append(", trace=");
		builder.append(trace);
		builder.append(", remark=");
		builder.append(remark);
		builder.append("]");
		return builder.toString();
	}
	
}
