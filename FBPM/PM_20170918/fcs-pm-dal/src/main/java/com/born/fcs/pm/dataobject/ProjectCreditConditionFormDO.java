/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.io.Serializable;
// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.dal.dataobject.ProjectDO;

public class ProjectCreditConditionFormDO extends ProjectDO implements Serializable {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	private String projectCode;
	
	private long itemId;
	
	private String itemDesc;
	
	private String type;
	
	private String isConfirm;
	
	private String confirmManId;
	
	private String confirmManAccount;
	
	private String confirmManName;
	
	private Date confirmDate;
	
	private String contractNo;
	
	private String contractAgreementUrl;
	
	private String rightVouche;
	
	private String remark;
	
	private String status;
	
	private String releaseStatus;
	
	private long releaseId;
	
	private String releaseReason;
	
	private String releaseGist;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询条件部分
	private String projectCodeOrName;
	
	List<Long> deptIdList;
	
	List<String> statusList;
	
	long loginUserId;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String getProjectCode() {
		return projectCode;
	}
	
	@Override
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getIsConfirm() {
		return isConfirm;
	}
	
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	public String getConfirmManId() {
		return confirmManId;
	}
	
	public void setConfirmManId(String confirmManId) {
		this.confirmManId = confirmManId;
	}
	
	public String getConfirmManAccount() {
		return confirmManAccount;
	}
	
	public void setConfirmManAccount(String confirmManAccount) {
		this.confirmManAccount = confirmManAccount;
	}
	
	public String getConfirmManName() {
		return confirmManName;
	}
	
	public void setConfirmManName(String confirmManName) {
		this.confirmManName = confirmManName;
	}
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	@Override
	public String getContractNo() {
		return contractNo;
	}
	
	@Override
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getRightVouche() {
		return rightVouche;
	}
	
	public void setRightVouche(String rightVouche) {
		this.rightVouche = rightVouche;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String getStatus() {
		return status;
	}
	
	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReleaseStatus() {
		return releaseStatus;
	}
	
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}
	
	public String getReleaseReason() {
		return releaseReason;
	}
	
	public void setReleaseReason(String releaseReason) {
		this.releaseReason = releaseReason;
	}
	
	public String getReleaseGist() {
		return releaseGist;
	}
	
	public void setReleaseGist(String releaseGist) {
		this.releaseGist = releaseGist;
	}
	
	@Override
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	@Override
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	@Override
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	@Override
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getStatusList() {
		return statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public long getLoginUserId() {
		return loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public String getContractAgreementUrl() {
		return contractAgreementUrl;
	}
	
	public void setContractAgreementUrl(String contractAgreementUrl) {
		this.contractAgreementUrl = contractAgreementUrl;
	}
	
	public String getProjectCodeOrName() {
		return this.projectCodeOrName;
	}
	
	public void setProjectCodeOrName(String projectCodeOrName) {
		this.projectCodeOrName = projectCodeOrName;
	}
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
