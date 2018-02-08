/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 决策机构人员Order
 *
 * @author jil
 *
 */
public class DecisionMemberOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 8566522807467897502L;
	
	private Long memberId;
	
	private Long institutionId;
	
	private Long userId;
	
	private String userAccount;
	
	private String userName;
	
	private Integer sortOrder;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public Long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
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
	
	public Integer getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
