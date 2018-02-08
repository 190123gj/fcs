/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 会议类型Order
 * 
 * @author wuzj
 * 
 */
public class CouncilTypeQueryOrder extends FcsQueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long typeId;
	private String typeCode;
	private String typeName;
	
	/** 是否需要投票表决 */
	private String ifVote;
	
	private int decisionInstitutionId;
	
	private String decisionInstitutionName;
	
	private String applyCompany;
	
	/**
	 * 用于判定查询哪些类型的数据
	 */
	private List<String> councilTypeCodes;
	
	/**
	 * 用于判断母公司还是信汇
	 */
	private List<String> companyNames;
	
	public List<String> getCompanyNames() {
		return this.companyNames;
	}
	
	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}
	
	public List<String> getCouncilTypeCodes() {
		return this.councilTypeCodes;
	}
	
	public void setCouncilTypeCodes(List<String> councilTypeCodes) {
		this.councilTypeCodes = councilTypeCodes;
	}
	
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
	
	public String getIfVote() {
		return ifVote;
	}
	
	public void setIfVote(String ifVote) {
		this.ifVote = ifVote;
	}
	
	public int getDecisionInstitutionId() {
		return decisionInstitutionId;
	}
	
	public void setDecisionInstitutionId(int decisionInstitutionId) {
		this.decisionInstitutionId = decisionInstitutionId;
	}
	
	public String getDecisionInstitutionName() {
		return decisionInstitutionName;
	}
	
	public void setDecisionInstitutionName(String decisionInstitutionName) {
		this.decisionInstitutionName = decisionInstitutionName;
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
	
}
