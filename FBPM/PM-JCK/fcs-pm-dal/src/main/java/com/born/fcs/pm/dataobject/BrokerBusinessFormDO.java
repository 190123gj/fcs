package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

/**
 * 自己写的sql对应的经纪业务
 *
 * @author wuzj
 */
public class BrokerBusinessFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -8105235754332396935L;
	
	private long id;
	
	private String projectCode;
	
	private String customerName;
	
	private String summary;
	
	private String isNeedCouncil;
	
	private String contractUrl;
	
	private String status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String isSelForCharge;
	
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
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getIsNeedCouncil() {
		return isNeedCouncil;
	}
	
	public void setIsNeedCouncil(String isNeedCouncil) {
		this.isNeedCouncil = isNeedCouncil;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public long getLoginUserId() {
		return loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
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
	
	public String getIsSelForCharge() {
		return isSelForCharge;
	}
	
	public void setIsSelForCharge(String isSelForCharge) {
		this.isSelForCharge = isSelForCharge;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
}
