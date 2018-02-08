package com.born.fcs.pm.ws.info.expireproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class ExpireProjectInfo extends BaseToStringInfo{
	
	private static final long serialVersionUID = 3797613838267180137L;
	
	private long id;
	private String projectCode;
	private String projectName;
	private Date expireDate;
	private ExpireProjectStatusEnum status;
	private String repayCertificate;
	private Date rawAddTime;
	private Date rawUpdateTime;
	private String receipt;

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public long getId() {
		return id;
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

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public ExpireProjectStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(ExpireProjectStatusEnum status) {
		this.status = status;
	}

	public String getRepayCertificate() {
		return repayCertificate;
	}
	
	public void setRepayCertificate(String repayCertificate) {
		this.repayCertificate = repayCertificate;
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


}
