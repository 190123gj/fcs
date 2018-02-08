package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

/**
 * 自己写的sql对应的内审意见交换
 *
 * @author wuzj
 */
public class InternalOpinionExchangeFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -8105235754332396935L;
	
	private long id;
	
	private String exCode;
	
	private String exType;
	
	private String deptIds;
	
	private String deptNames;
	
	private String needFeedback;
	
	private Date feedbackTime;
	
	private String users;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExCode() {
		return this.exCode;
	}
	
	public void setExCode(String exCode) {
		this.exCode = exCode;
	}
	
	public String getExType() {
		return this.exType;
	}
	
	public void setExType(String exType) {
		this.exType = exType;
	}
	
	public String getDeptIds() {
		return this.deptIds;
	}
	
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	
	public String getDeptNames() {
		return this.deptNames;
	}
	
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	public String getNeedFeedback() {
		return this.needFeedback;
	}
	
	public void setNeedFeedback(String needFeedback) {
		this.needFeedback = needFeedback;
	}
	
	public Date getFeedbackTime() {
		return this.feedbackTime;
	}
	
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	
	public String getUsers() {
		return this.users;
	}
	
	public void setUsers(String users) {
		this.users = users;
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
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
	
}
