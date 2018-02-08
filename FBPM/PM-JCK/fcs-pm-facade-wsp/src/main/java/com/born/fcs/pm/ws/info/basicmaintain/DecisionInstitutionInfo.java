package com.born.fcs.pm.ws.info.basicmaintain;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class DecisionInstitutionInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5952335172714139506L;
	private long institutionId;
	
	private String institutionName;
	
	private String institutionMembers;
	
	/** 决策机构下的决策人员 */
	private List<DecisionMemberInfo> decisionMembers;
	private String decisionMemberIds;
	private String decisionMemberAccounts;
	private String decisionMemberNames;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
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
	
	public List<DecisionMemberInfo> getDecisionMembers() {
		return this.decisionMembers;
	}
	
	public void setDecisionMembers(List<DecisionMemberInfo> decisionMembers) {
		
		this.decisionMembers = decisionMembers;
		if (this.decisionMembers.size() > 0) {
			StringBuffer nameStrb = new StringBuffer();
			StringBuffer acountStrb = new StringBuffer();
			StringBuffer idStrb = new StringBuffer();
			for (DecisionMemberInfo i : this.decisionMembers) {
				nameStrb.append(",");
				nameStrb.append(i.getUserName());
				acountStrb.append(",");
				acountStrb.append(i.getUserAccount());
				idStrb.append(",");
				idStrb.append(i.getUserId());
			}
			this.decisionMemberIds = idStrb.toString().replaceFirst(",", "");
			this.decisionMemberNames = nameStrb.toString().replaceFirst(",", "");
			this.decisionMemberAccounts = acountStrb.toString().replaceFirst(",", "");
		}
		
	}
	
	public String getDecisionMemberIds() {
		return this.decisionMemberIds;
	}
	
	public void setDecisionMemberIds(String decisionMemberIds) {
		this.decisionMemberIds = decisionMemberIds;
	}
	
	public String getDecisionMemberAccounts() {
		return this.decisionMemberAccounts;
	}
	
	public void setDecisionMemberAccounts(String decisionMemberAccounts) {
		this.decisionMemberAccounts = decisionMemberAccounts;
	}
	
	public String getDecisionMemberNames() {
		return this.decisionMemberNames;
	}
	
	public void setDecisionMemberNames(String decisionMemberNames) {
		this.decisionMemberNames = decisionMemberNames;
	}
	
}
