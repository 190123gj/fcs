package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 评价基本信息
 * 
 * @author lirz
 * 
 * 2016-3-10 上午10:28:27
 */
public class FInvestigationReviewBaseInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7889022540998137637L;
	
	private long MReviewId; //评价ID
	private Date establishedTime; //成立时间
	private String operatingTerm; //经营期限
	private String legalPersion; //法定代表人
	private String orgCode; //组织机构代码证
	private String livingAddress; //住址
	private String actualControlPerson; //实际控制人
	private String enterpriseType; //企业类型
	private String workAddress; //办公地址
	private String busiLicenseNo; //营业执照号
	private String taxCertificateNo; //税务登记证号
	private String loanCardNo; //贷款卡号
	private String lastCheckYear; //最后年检年度
	private String busiScope; //业务范围
	//领导人整体评价（包括主要领导人简历、管理层的稳定性）
	private String leaderAppraise;
	
	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long mReviewId) {
		MReviewId = mReviewId;
	}
	
	public Date getEstablishedTime() {
		return establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public String getOperatingTerm() {
		return operatingTerm;
	}
	
	public void setOperatingTerm(String operatingTerm) {
		this.operatingTerm = operatingTerm;
	}
	
	public String getLegalPersion() {
		return legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getLivingAddress() {
		return livingAddress;
	}
	
	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}
	
	public String getActualControlPerson() {
		return actualControlPerson;
	}
	
	public void setActualControlPerson(String actualControlPerson) {
		this.actualControlPerson = actualControlPerson;
	}
	
	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getWorkAddress() {
		return workAddress;
	}
	
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	
	public String getBusiLicenseNo() {
		return busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getTaxCertificateNo() {
		return taxCertificateNo;
	}
	
	public void setTaxCertificateNo(String taxCertificateNo) {
		this.taxCertificateNo = taxCertificateNo;
	}
	
	public String getLoanCardNo() {
		return loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	
	public String getLastCheckYear() {
		return lastCheckYear;
	}
	
	public void setLastCheckYear(String lastCheckYear) {
		this.lastCheckYear = lastCheckYear;
	}
	
	public String getBusiScope() {
		return busiScope;
	}
	
	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}
	
	public String getLeaderAppraise() {
		return leaderAppraise;
	}

	public void setLeaderAppraise(String leaderAppraise) {
		this.leaderAppraise = leaderAppraise;
	}

}
