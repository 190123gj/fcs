package com.born.fcs.pm.ws.info.projectcreditcondition;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;

/**
 * 授信条件落实情况
 *
 * @author Ji
 *
 */
public class FCreditConditionConfirmInfo extends FormProjectInfo {
	
	private static final long serialVersionUID = 2807170855350255828L;
	
	private long confirmId;
	
	private String contractNo;
	
	private long institutionId;
	
	private String institutionName;
	
	private BooleanEnum isMargin;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<ProjectCreditConditionInfo> projectCreditConditionInfos;
	
	private ProjectCreditMarginInfo projectCreditMarginInfo;//保证金
	
	//========== getters and setters ==========
	public long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(long confirmId) {
		this.confirmId = confirmId;
	}
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	
	public List<ProjectCreditConditionInfo> getProjectCreditConditionInfos() {
		return projectCreditConditionInfos;
	}
	
	public void setProjectCreditConditionInfos(	List<ProjectCreditConditionInfo> projectCreditConditionInfos) {
		this.projectCreditConditionInfos = projectCreditConditionInfos;
	}
	
	public BooleanEnum getIsMargin() {
		return isMargin;
	}
	
	public void setIsMargin(BooleanEnum isMargin) {
		this.isMargin = isMargin;
	}
	
	public ProjectCreditMarginInfo getProjectCreditMarginInfo() {
		return projectCreditMarginInfo;
	}
	
	public void setProjectCreditMarginInfo(ProjectCreditMarginInfo projectCreditMarginInfo) {
		this.projectCreditMarginInfo = projectCreditMarginInfo;
	}
	
}
