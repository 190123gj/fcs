package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.dal.dataobject.FCouncilSummaryRiskHandleDO;

public class CouncilSummaryRiskHandleDO extends FCouncilSummaryRiskHandleDO {
	
	private static final long serialVersionUID = -212763029309730478L;
	
	private long summaryFormId;
	
	private long councilId;
	
	private String summaryCode;
	
	private Date councilBeginTime;
	
	private String councilSubject;
	
	//查询部分
	private long loginUserId;
	
	private long busiManagerId;
	
	private List<Long> deptIdList;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public long getSummaryFormId() {
		return this.summaryFormId;
	}
	
	public void setSummaryFormId(long summaryFormId) {
		this.summaryFormId = summaryFormId;
	}
	
	public long getCouncilId() {
		return this.councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public String getSummaryCode() {
		return this.summaryCode;
	}
	
	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}
	
	public Date getCouncilBeginTime() {
		return this.councilBeginTime;
	}
	
	public void setCouncilBeginTime(Date councilBeginTime) {
		this.councilBeginTime = councilBeginTime;
	}
	
	public String getCouncilSubject() {
		return this.councilSubject;
	}
	
	public void setCouncilSubject(String councilSubject) {
		this.councilSubject = councilSubject;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
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
