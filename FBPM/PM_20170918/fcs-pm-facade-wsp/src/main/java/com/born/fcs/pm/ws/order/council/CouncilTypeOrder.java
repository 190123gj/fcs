/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 会议类型Order
 * 
 * @author jil
 * 
 */
public class CouncilTypeOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long typeId;
	
	private String typeCode;
	
	private String typeName;
	
	private Long decisionInstitutionId;
	
	private String decisionInstitutionName;
	
	private String applyDeptId;
	
	private String applyCompany;
	
	private int majorNum;
	
	private int lessNum;
	
	private String ifVote;
	
	private String voteRuleType;
	
	private int passNum;
	
	private int indeterminateNum;
	
	private double passRate;
	
	private double indeterminateRate;
	
	private String summaryCodePrefix;
	
	private CompanyNameEnum companyName = CompanyNameEnum.NORMAL;
	
	public Long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public CompanyNameEnum getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(CompanyNameEnum companyName) {
		this.companyName = companyName;
	}
	
	public Long getDecisionInstitutionId() {
		return decisionInstitutionId;
	}
	
	public void setDecisionInstitutionId(Long decisionInstitutionId) {
		this.decisionInstitutionId = decisionInstitutionId;
	}
	
	public String getDecisionInstitutionName() {
		return decisionInstitutionName;
	}
	
	public void setDecisionInstitutionName(String decisionInstitutionName) {
		this.decisionInstitutionName = decisionInstitutionName;
	}
	
	public int getMajorNum() {
		return majorNum;
	}
	
	public void setMajorNum(int majorNum) {
		this.majorNum = majorNum;
	}
	
	public int getLessNum() {
		return lessNum;
	}
	
	public void setLessNum(int lessNum) {
		this.lessNum = lessNum;
	}
	
	public String getIfVote() {
		return ifVote;
	}
	
	public void setIfVote(String ifVote) {
		this.ifVote = ifVote;
	}
	
	public String getVoteRuleType() {
		return voteRuleType;
	}
	
	public void setVoteRuleType(String voteRuleType) {
		this.voteRuleType = voteRuleType;
	}
	
	public int getPassNum() {
		return passNum;
	}
	
	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}
	
	public int getIndeterminateNum() {
		return indeterminateNum;
	}
	
	public void setIndeterminateNum(int indeterminateNum) {
		this.indeterminateNum = indeterminateNum;
	}
	
	public double getPassRate() {
		return passRate;
	}
	
	public void setPassRate(double passRate) {
		this.passRate = passRate;
	}
	
	public double getIndeterminateRate() {
		return indeterminateRate;
	}
	
	public void setIndeterminateRate(double indeterminateRate) {
		this.indeterminateRate = indeterminateRate;
	}
	
	public String getSummaryCodePrefix() {
		return summaryCodePrefix;
	}
	
	public void setSummaryCodePrefix(String summaryCodePrefix) {
		this.summaryCodePrefix = summaryCodePrefix;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getApplyCompany() {
		return applyCompany;
	}
	
	public void setApplyCompany(String applyCompany) {
		this.applyCompany = applyCompany;
	}
	
	public String getApplyDeptId() {
		return applyDeptId;
	}
	
	public void setApplyDeptId(String applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
}
