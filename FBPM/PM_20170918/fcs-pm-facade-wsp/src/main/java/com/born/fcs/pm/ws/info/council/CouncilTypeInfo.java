package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.VoteRuleTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class CouncilTypeInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590757875565246952L;
	
	private long typeId;
	
	private CouncilTypeEnum typeCode;
	
	private String typeName;
	
	private long decisionInstitutionId;
	
	private String decisionInstitutionName;
	
	private String applyDeptId;
	
	private String applyCompany;
	
	private int majorNum;
	
	private int lessNum;
	
	private BooleanEnum ifVote;
	
	private VoteRuleTypeEnum voteRuleType;
	
	private int passNum;
	
	private int indeterminateNum;
	
	private double passRate;
	
	private double indeterminateRate;
	
	private String summaryCodePrefix;
	
	private CompanyNameEnum companyName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public CompanyNameEnum getCompanyName() {
		return this.companyName;
	}
	
	public void setCompanyName(CompanyNameEnum companyName) {
		this.companyName = companyName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public long getDecisionInstitutionId() {
		return decisionInstitutionId;
	}
	
	public void setDecisionInstitutionId(long decisionInstitutionId) {
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
	
	public BooleanEnum getIfVote() {
		return ifVote;
	}
	
	public void setIfVote(BooleanEnum ifVote) {
		this.ifVote = ifVote;
	}
	
	public VoteRuleTypeEnum getVoteRuleType() {
		return voteRuleType;
	}
	
	public void setVoteRuleType(VoteRuleTypeEnum voteRuleType) {
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public CouncilTypeEnum getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(CouncilTypeEnum typeCode) {
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
