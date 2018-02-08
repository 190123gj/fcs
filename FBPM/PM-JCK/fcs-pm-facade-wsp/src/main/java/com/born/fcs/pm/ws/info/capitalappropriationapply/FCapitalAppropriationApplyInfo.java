package com.born.fcs.pm.ws.info.capitalappropriationapply;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyInfo extends SimpleFormProjectInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7625043908514094914L;
	
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
	
	private String relatedProjectCode;
	
	private String taskUserData;
	//
	private long applyId;
	
	private long outBizNo;
	
	private String projectCode;
	
	private String projectName;
	
	private FCapitalAppropriationApplyTypeEnum projectType;
	
	private Money appropriateAmount = new Money(0, 0);
	
	private String attach;
	
	private BooleanEnum isReceipt;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfos;
	
	private FCapitalAppropriationApplyPayeeInfo payeeInfo;//收款方信息
	
	private FCapitalAppropriationApplyReceiptInfo receiptInfo;//回执单信息
	
	//========== getters and setters ==========
	
	@Override
	public long getFormId() {
		return formId;
	}
	
	@Override
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
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
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	@Override
	public String getProjectCode() {
		return projectCode;
	}
	
	@Override
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Override
	public String getProjectName() {
		return projectName;
	}
	
	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public FCapitalAppropriationApplyTypeEnum getProjectType() {
		return projectType;
	}
	
	public void setProjectType(FCapitalAppropriationApplyTypeEnum projectType) {
		this.projectType = projectType;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
	
	public List<FCapitalAppropriationApplyFeeInfo> getfCapitalAppropriationApplyFeeInfos() {
		return fCapitalAppropriationApplyFeeInfos;
	}
	
	public void setfCapitalAppropriationApplyFeeInfos(	List<FCapitalAppropriationApplyFeeInfo> fCapitalAppropriationApplyFeeInfos) {
		this.fCapitalAppropriationApplyFeeInfos = fCapitalAppropriationApplyFeeInfos;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Money getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Money appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}
	
	public String getRelatedProjectCode() {
		return relatedProjectCode;
	}
	
	public void setRelatedProjectCode(String relatedProjectCode) {
		this.relatedProjectCode = relatedProjectCode;
	}
	
	public String getTaskUserData() {
		return taskUserData;
	}
	
	public void setTaskUserData(String taskUserData) {
		this.taskUserData = taskUserData;
	}
	
	public long getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(long outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public FCapitalAppropriationApplyPayeeInfo getPayeeInfo() {
		return payeeInfo;
	}
	
	public void setPayeeInfo(FCapitalAppropriationApplyPayeeInfo payeeInfo) {
		this.payeeInfo = payeeInfo;
	}
	
	public FCapitalAppropriationApplyReceiptInfo getReceiptInfo() {
		return receiptInfo;
	}
	
	public void setReceiptInfo(FCapitalAppropriationApplyReceiptInfo receiptInfo) {
		this.receiptInfo = receiptInfo;
	}
	
	public BooleanEnum getIsReceipt() {
		return isReceipt;
	}
	
	public void setIsReceipt(BooleanEnum isReceipt) {
		this.isReceipt = isReceipt;
	}
	
}
