package com.born.fcs.pm.ws.info.finvestigation.checking;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CheckPointEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 尽调 - 审核中修改数据
 * 
 * @author lirz
 * 
 * 2017-3-20 下午3:13:25
 * 
 */
public class InvestigationCheckingInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8706000721616992889L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private long relatedFormId;
	
	private CheckPointEnum checkPoint;
	
	private String formCode;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public long getRelatedFormId() {
		return this.relatedFormId;
	}
	
	public void setRelatedFormId(long relatedFormId) {
		this.relatedFormId = relatedFormId;
	}
	
	public CheckPointEnum getCheckPoint() {
		return this.checkPoint;
	}
	
	public void setCheckPoint(CheckPointEnum checkPoint) {
		this.checkPoint = checkPoint;
	}
	
	public String getFormCode() {
		return this.formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
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
	
}
