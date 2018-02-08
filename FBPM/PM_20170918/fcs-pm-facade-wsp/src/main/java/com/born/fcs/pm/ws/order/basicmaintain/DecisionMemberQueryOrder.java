/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 决策机构人员Order
 *
 * @author jil
 *
 */
public class DecisionMemberQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -8838878665687276623L;
	
	private Long memberId;
	
	private Long userId;
	
	private String userName;
	
	private Long institutionId;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getInstitutionId() {
		return this.institutionId;
	}
	
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
}
