/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import java.util.List;

import com.born.fcs.pm.ws.info.basicmaintain.DecisionMemberInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 决策机构名称Order
 *
 * @author jil
 *
 */
public class DecisionInstitutionOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -3148116459776953166L;
	
	private Long institutionId;
	
	private String institutionName;
	
	private String institutionMembers;
	
	private List<DecisionMemberInfo> decisionMembers;
	
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
	
	public List<DecisionMemberInfo> getDecisionMembers() {
		return decisionMembers;
	}
	
	public void setDecisionMembers(List<DecisionMemberInfo> decisionMembers) {
		this.decisionMembers = decisionMembers;
	}
	
}
