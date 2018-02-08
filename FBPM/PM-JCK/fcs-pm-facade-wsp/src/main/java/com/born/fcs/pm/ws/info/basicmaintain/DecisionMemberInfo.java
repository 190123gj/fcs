package com.born.fcs.pm.ws.info.basicmaintain;


import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class DecisionMemberInfo extends BaseToStringInfo {

	private static final long serialVersionUID = -6886583771671819392L;

	private long memberId;

	private long institutionId;

	private long userId;

	private String userAccount;

	private String userName;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
