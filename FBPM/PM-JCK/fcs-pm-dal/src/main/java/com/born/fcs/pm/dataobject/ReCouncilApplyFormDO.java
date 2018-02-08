/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 复议申请
 *
 * @author wuzj
 */
public class ReCouncilApplyFormDO extends SimpleFormProjectDO {
	
	private static final long serialVersionUID = -8933402330207121998L;
	
	private long applyId;
	
	private long oldSpId;
	
	private String oldSpCode;
	
	private String contentReason;
	
	private String overview;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private long investigateId; //尽职调查ID
	
	private long investigateFormId;
	
	//IS : 尽职复议但未填写 ，  YES：复议  ， NO：非复议
	private String investigateReview;
	
	//查询条件部分
	List<Long> deptIdList;
	
	List<String> statusList;
	
	long loginUserId;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getOldSpId() {
		return this.oldSpId;
	}
	
	public void setOldSpId(long oldSpId) {
		this.oldSpId = oldSpId;
	}
	
	public String getOldSpCode() {
		return this.oldSpCode;
	}
	
	public void setOldSpCode(String oldSpCode) {
		this.oldSpCode = oldSpCode;
	}
	
	public String getContentReason() {
		return this.contentReason;
	}
	
	public void setContentReason(String contentReason) {
		this.contentReason = contentReason;
	}
	
	public String getOverview() {
		return this.overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
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
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public long getInvestigateId() {
		return this.investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}
	
	public long getInvestigateFormId() {
		return this.investigateFormId;
	}
	
	public void setInvestigateFormId(long investigateFormId) {
		this.investigateFormId = investigateFormId;
	}
	
	public String getInvestigateReview() {
		return this.investigateReview;
	}
	
	public void setInvestigateReview(String investigateReview) {
		this.investigateReview = investigateReview;
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
