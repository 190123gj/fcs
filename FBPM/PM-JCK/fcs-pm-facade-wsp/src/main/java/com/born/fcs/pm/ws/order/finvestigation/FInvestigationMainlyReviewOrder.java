package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.DateUtil;

/**
 * 客户主体评价
 * 
 * @author lirz
 * 
 * 2016-3-10 上午10:28:27
 */
public class FInvestigationMainlyReviewOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -6840452986974372044L;
	private long MReviewId; //评价ID
	private Date establishedTime; //成立时间
	private String operatingTerm; //经营期限
	private String legalPersion; //法定代表人
	private String orgCode; //组织机构代码证
	private String livingAddress; //住址
	private String actualControlPerson; //实际控制人
	private String enterpriseType; //企业类型
	private String workAddress; //办公地址
	private String isOneCert; //是否三证合一
	private String busiLicenseNo; //营业执照号
	private String taxCertificateNo; //税务登记证号
	private String localTaxNo; // 地税号
	private String loanCardNo; //贷款卡号
	private String lastCheckYear; //最后年检年度
	private String busiScope; //业务范围
	//已获得的资质证书
	private List<FInvestigationMainlyReviewCertificateOrder> certificates;
	//开户情况
	private List<FInvestigationMainlyReviewBankInfoOrder> banks;
	private String customerDevEvolution; //客户发展沿革（背景）及重大机构变革
	//主要股东情况
	private List<FInvestigationMainlyReviewStockholderOrder> holders;
	//实际控制人或自然人股东个人资产、负债状况（非国有必填）
	private List<FInvestigationMainlyReviewAssetAndLiabilityOrder> assetAndLiabilities;
	//客户下属公司、全资和控股子公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyOrder> subsidiaries;
	//客户主要参股公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyOrder> participations;
	//客户其它关联公司情况
	private List<FInvestigationMainlyReviewRelatedCompanyOrder> correlations;
	private String relatedTrade; //关联交易情况
	private String relatedGuarantee; //关联担保情况
	private String relatedCapitalTieup; //关联资金占用
	private String busiQualification; //主营业务范围及资质情况
	private String busiPlace; //经营场所调查情况
	private Date queryTime; //客户信用状况（查询时间：
	
	//客户信用
	private List<FInvestigationMainlyReviewCreditStatusOrder> customerCredits;
	//(客户)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。
	private String loanRepaySituationCustomer;
	//客户对外担保的情况
	private List<FInvestigationMainlyReviewExternalGuaranteeOrder> customerGuarantees;
	
	//个人信用
	private List<FInvestigationMainlyReviewCreditStatusOrder> personalCredits;
	//(个人)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。
	private String loanRepaySituationPersional;
	//个人对外担保的情况
	private List<FInvestigationMainlyReviewExternalGuaranteeOrder> personalGuarantees;
	//其他信用信息（工商、税务、诉讼、环保等信息）
	private List<FInvestigationMainlyReviewCreditInfoOrder> siteStatus;
	private String other; //其他情况
	
	@Override
	public void check() {
		super.check();
	}
	
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
	
	public void setEstablishedTimeStr(String establishedTimeStr) {
		this.establishedTime = DateUtil.strToDtSimpleFormat(establishedTimeStr);
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
	
	public String getIsOneCert() {
		return isOneCert;
	}

	public void setIsOneCert(String isOneCert) {
		this.isOneCert = isOneCert;
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
	
	public List<FInvestigationMainlyReviewCertificateOrder> getCertificates() {
		return certificates;
	}
	
	public void setCertificates(List<FInvestigationMainlyReviewCertificateOrder> certificates) {
		this.certificates = certificates;
	}
	
	public List<FInvestigationMainlyReviewBankInfoOrder> getBanks() {
		return banks;
	}
	
	public void setBanks(List<FInvestigationMainlyReviewBankInfoOrder> banks) {
		this.banks = banks;
	}
	
	public String getCustomerDevEvolution() {
		return customerDevEvolution;
	}
	
	public void setCustomerDevEvolution(String customerDevEvolution) {
		this.customerDevEvolution = customerDevEvolution;
	}
	
	public List<FInvestigationMainlyReviewStockholderOrder> getHolders() {
		return holders;
	}
	
	public void setHolders(List<FInvestigationMainlyReviewStockholderOrder> holders) {
		this.holders = holders;
	}
	
	public List<FInvestigationMainlyReviewAssetAndLiabilityOrder> getAssetAndLiabilities() {
		return assetAndLiabilities;
	}
	
	public void setAssetAndLiabilities(	List<FInvestigationMainlyReviewAssetAndLiabilityOrder> assetAndLiabilities) {
		this.assetAndLiabilities = assetAndLiabilities;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyOrder> getSubsidiaries() {
		return subsidiaries;
	}
	
	public void setSubsidiaries(List<FInvestigationMainlyReviewRelatedCompanyOrder> subsidiaries) {
		this.subsidiaries = subsidiaries;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyOrder> getParticipations() {
		return participations;
	}
	
	public void setParticipations(List<FInvestigationMainlyReviewRelatedCompanyOrder> participations) {
		this.participations = participations;
	}
	
	public List<FInvestigationMainlyReviewRelatedCompanyOrder> getCorrelations() {
		return correlations;
	}
	
	public void setCorrelations(List<FInvestigationMainlyReviewRelatedCompanyOrder> correlations) {
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
	
	public List<FInvestigationMainlyReviewCreditStatusOrder> getCustomerCredits() {
		return customerCredits;
	}
	
	public void setCustomerCredits(List<FInvestigationMainlyReviewCreditStatusOrder> customerCredits) {
		this.customerCredits = customerCredits;
	}
	
	public String getLoanRepaySituationCustomer() {
		return loanRepaySituationCustomer;
	}
	
	public void setLoanRepaySituationCustomer(String loanRepaySituationCustomer) {
		this.loanRepaySituationCustomer = loanRepaySituationCustomer;
	}
	
	public List<FInvestigationMainlyReviewExternalGuaranteeOrder> getCustomerGuarantees() {
		return customerGuarantees;
	}
	
	public void setCustomerGuarantees(	List<FInvestigationMainlyReviewExternalGuaranteeOrder> customerGuarantees) {
		this.customerGuarantees = customerGuarantees;
	}
	
	public List<FInvestigationMainlyReviewCreditStatusOrder> getPersonalCredits() {
		return personalCredits;
	}
	
	public void setPersonalCredits(List<FInvestigationMainlyReviewCreditStatusOrder> personalCredits) {
		this.personalCredits = personalCredits;
	}
	
	public String getLoanRepaySituationPersional() {
		return loanRepaySituationPersional;
	}
	
	public void setLoanRepaySituationPersional(String loanRepaySituationPersional) {
		this.loanRepaySituationPersional = loanRepaySituationPersional;
	}
	
	public List<FInvestigationMainlyReviewExternalGuaranteeOrder> getPersonalGuarantees() {
		return personalGuarantees;
	}
	
	public void setPersonalGuarantees(	List<FInvestigationMainlyReviewExternalGuaranteeOrder> personalGuarantees) {
		this.personalGuarantees = personalGuarantees;
	}
	
	public List<FInvestigationMainlyReviewCreditInfoOrder> getSiteStatus() {
		return siteStatus;
	}
	
	public void setSiteStatus(List<FInvestigationMainlyReviewCreditInfoOrder> siteStatus) {
		this.siteStatus = siteStatus;
	}
	
	public String getOther() {
		return other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
	
	public void setQueryTimeStr(String queryTimeStr) {
		this.queryTime = DateUtil.strToDtSimpleFormat(queryTimeStr);
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
