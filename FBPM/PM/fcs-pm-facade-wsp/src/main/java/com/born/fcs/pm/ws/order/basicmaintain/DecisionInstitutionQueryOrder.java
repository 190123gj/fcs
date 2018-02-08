/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 决策机构名称Order
 *
 * @author jil
 *
 */
public class DecisionInstitutionQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7393118631671928403L;
	private Long institutionId;
	
	private String institutionName;
	
	private String institutionMembers;
	
	private Date rawUpdateTime;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public String getInstitutionMembers() {
		return institutionMembers;
	}
	
	public void setInstitutionMembers(String institutionMembers) {
		this.institutionMembers = institutionMembers;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
