package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 客户主体评价
 * 
 * @author lirz
 * 
 * 2016-3-10 上午10:28:27
 */
public class FInvestigationMainlyReviewInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -5691007077930233284L;
	
	private long MReviewId; //评价ID
	private Date establishedTime; //成立时间
	private String operatingTerm; //经营期限
	private String legalPersion; //法定代表人
	private String orgCode; //组织机构代码证
	private String livingAddress; //住址
	private String actualControlPerson; //实际控制人
	private EnterpriseNatureEnum enterpriseType; //企业类型
	private String workAddress; //办公地址
	private BooleanEnum isOneCert; //是否三证合一
	private String busiLicenseNo; //营业执照号
	private String taxCertificateNo; //税务登记证号
	private String localTaxNo;;
	private String loanCardNo; //贷款卡号
	private String lastCheckYear; //最后年检年度
	private String busiScope; //业务范围
	//已获得的资质证书
	private List<FInvestigationMainlyReviewCertificateInfo> certificates;
	//开户情况
	private List<FInvestigationMainlyReviewBankInfo> banks;
	private String customerDevEvolution; //客户发展沿革（背景）及重大机构变革
	//主要股东情况
	private List<FInvestigationMainlyReviewStockholderInfo> holders;
	//实际控制人或自然人股东个人资产、负债状况（非国有必填）
	private List<FInvestigationMainlyReviewAssetAndLiabilityInfo> assetAndLiabilities;
	//客户下属公司、全资和控股子公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyInfo> subsidiaries;
	//客户主要参股公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyInfo> participations;
	//客户其它关联公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyInfo> correlations;
	private String relatedTrade; //关联交易情况
	private String relatedGuarantee; //关联担保情况
	private String relatedCapitalTieup; //关联资金占用
	private String busiQualification; //主营业务范围及资质情况
	private String busiPlace; //经营场所调查情况
	private Date queryTime; //客户信用状况（查询时间：
	
	//客户信用
	private List<FInvestigationMainlyReviewCreditStatusInfo> customerCredits;
	//(客户)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。
	private String loanRepaySituationCustomer;
	//客户对外担保的情况
	private List<FInvestigationMainlyReviewExternalGuaranteeInfo> customerGuarantees;
	
	//个人信用
	private List<FInvestigationMainlyReviewCreditStatusInfo> personalCredits;
	//(个人)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。
	private String loanRepaySituationPersional;
	//个人对外担保的情况
	private List<FInvestigationMainlyReviewExternalGuaranteeInfo> personalGuarantees;
	//其他信用信息（工商、税务、诉讼、环保等信息）
	private List<FInvestigationMainlyReviewCreditInfo> siteStatus;
	private String other; //其他情况
	
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
	
	public EnterpriseNatureEnum getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(EnterpriseNatureEnum enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getWorkAddress() {
		return workAddress;
	}
	
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	
	public BooleanEnum getIsOneCert() {
		return isOneCert;
	}

	public void setIsOneCert(BooleanEnum isOneCert) {
		this.isOneCert = isOneCert;
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
	
	public String getLocalTaxNo() {
		return this.localTaxNo;
	}

	public void setLocalTaxNo(String localTaxNo) {
		this.localTaxNo = localTaxNo;
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
	
	public List<FInvestigationMainlyReviewCertificateInfo> getCertificates() {
		return certificates;
	}
	
	public void setCertificates(List<FInvestigationMainlyReviewCertificateInfo> certificates) {
		this.certificates = certificates;
	}
	
	public List<FInvestigationMainlyReviewBankInfo> getBanks() {
		return banks;
	}
	
	public void setBanks(List<FInvestigationMainlyReviewBankInfo> banks) {
		this.banks = banks;
	}
	
	public String getCustomerDevEvolution() {
		return customerDevEvolution;
	}
	
	public void setCustomerDevEvolution(String customerDevEvolution) {
		this.customerDevEvolution = customerDevEvolution;
	}
	
	public List<FInvestigationMainlyReviewStockholderInfo> getHolders() {
		return holders;
	}
	
	public void setHolders(List<FInvestigationMainlyReviewStockholderInfo> holders) {
		this.holders = holders;
	}
	
	public List<FInvestigationMainlyReviewAssetAndLiabilityInfo> getAssetAndLiabilities() {
		return assetAndLiabilities;
	}
	
	public void setAssetAndLiabilities(	List<FInvestigationMainlyReviewAssetAndLiabilityInfo> assetAndLiabilities) {
		this.assetAndLiabilities = assetAndLiabilities;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyInfo> getSubsidiaries() {
		return subsidiaries;
	}
	
	public void setSubsidiaries(List<FInvestigationMainlyReviewRelatedCompanyInfo> subsidiaries) {
		this.subsidiaries = subsidiaries;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyInfo> getParticipations() {
		return participations;
	}
	
	public void setParticipations(List<FInvestigationMainlyReviewRelatedCompanyInfo> participations) {
		this.participations = participations;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyInfo> getCorrelations() {
		return correlations;
	}
	
	public void setCorrelations(List<FInvestigationMainlyReviewRelatedCompanyInfo> correlations) {
		this.correlations = correlations;
	}
	
	public String getRelatedTrade() {
		return relatedTrade;
	}
	
	public void setRelatedTrade(String relatedTrade) {
		this.relatedTrade = relatedTrade;
	}
	
	public String getRelatedGuarantee() {
		return relatedGuarantee;
	}
	
	public void setRelatedGuarantee(String relatedGuarantee) {
		this.relatedGuarantee = relatedGuarantee;
	}
	
	public String getRelatedCapitalTieup() {
		return relatedCapitalTieup;
	}
	
	public void setRelatedCapitalTieup(String relatedCapitalTieup) {
		this.relatedCapitalTieup = relatedCapitalTieup;
	}
	
	public String getBusiQualification() {
		return busiQualification;
	}
	
	public void setBusiQualification(String busiQualification) {
		this.busiQualification = busiQualification;
	}
	
	public String getBusiPlace() {
		return busiPlace;
	}
	
	public void setBusiPlace(String busiPlace) {
		this.busiPlace = busiPlace;
	}
	
	public List<FInvestigationMainlyReviewCreditStatusInfo> getCustomerCredits() {
		return customerCredits;
	}
	
	public void setCustomerCredits(List<FInvestigationMainlyReviewCreditStatusInfo> customerCredits) {
		this.customerCredits = customerCredits;
	}
	
	public String getLoanRepaySituationCustomer() {
		return loanRepaySituationCustomer;
	}
	
	public void setLoanRepaySituationCustomer(String loanRepaySituationCustomer) {
		this.loanRepaySituationCustomer = loanRepaySituationCustomer;
	}
	
	public List<FInvestigationMainlyReviewExternalGuaranteeInfo> getCustomerGuarantees() {
		return customerGuarantees;
	}
	
	public void setCustomerGuarantees(	List<FInvestigationMainlyReviewExternalGuaranteeInfo> customerGuarantees) {
		this.customerGuarantees = customerGuarantees;
	}
	
	public List<FInvestigationMainlyReviewCreditStatusInfo> getPersonalCredits() {
		return personalCredits;
	}
	
	public void setPersonalCredits(List<FInvestigationMainlyReviewCreditStatusInfo> personalCredits) {
		this.personalCredits = personalCredits;
	}
	
	public String getLoanRepaySituationPersional() {
		return loanRepaySituationPersional;
	}
	
	public void setLoanRepaySituationPersional(String loanRepaySituationPersional) {
		this.loanRepaySituationPersional = loanRepaySituationPersional;
	}
	
	public List<FInvestigationMainlyReviewExternalGuaranteeInfo> getPersonalGuarantees() {
		return personalGuarantees;
	}
	
	public void setPersonalGuarantees(	List<FInvestigationMainlyReviewExternalGuaranteeInfo> personalGuarantees) {
		this.personalGuarantees = personalGuarantees;
	}
	
	public List<FInvestigationMainlyReviewCreditInfo> getSiteStatus() {
		return siteStatus;
	}
	
	public void setSiteStatus(List<FInvestigationMainlyReviewCreditInfo> siteStatus) {
		this.siteStatus = siteStatus;
	}
	
	public String getOther() {
		return other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
