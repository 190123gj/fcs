/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 理财项目合同表单Info
 *
 * @author wuzj
 */
public class FinancialProjectContractFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -9148342418544956222L;
	
	/** 合同ID */
	private long contractId;
	/** 表单ID */
	private long formId;
	/** 项目编号 */
	private String projectCode;
	/** 合同内容 */
	private String contract;
	/** 附件 */
	private String attach;
	/** 合同状态 （待定） */
	private ContractStatusEnum contractStatus;
	/** 回传文件 */
	private String contractReturn;
	/** 备注 */
	private String remark;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	/** 立项信息 */
	FProjectFinancialInfo project;
	
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
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getContract() {
		return this.contract;
	}
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public ContractStatusEnum getContractStatus() {
		return this.contractStatus;
	}
	
	public void setContractStatus(ContractStatusEnum contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public String getContractReturn() {
		return this.contractReturn;
	}
	
	public void setContractReturn(String contractReturn) {
		this.contractReturn = contractReturn;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public FProjectFinancialInfo getProject() {
		return this.project;
	}
	
	public void setProject(FProjectFinancialInfo project) {
		this.project = project;
	}
}
