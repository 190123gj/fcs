package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.financialkpi.FinancialKpiInfo;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 授信方案主要事项合理性评价 - 保证人
 * 
 * @author lirz
 * 
 * 2016-10-31 下午3:15:47
 * 
 */
public class FInvestigationCsRationalityReviewGuarantorInfo extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5641729373333861320L;
	
	private long guarantorId;
	
	private long formId;
	
	private String guarantorType;
	
	private String guarantorName;
	
	private String establishedTime;
	
	private String operatingTerm;
	
	private String legalPersion;
	
	private String orgCode;
	
	private String livingAddress;
	
	private String actualControlPerson;
	
	private String enterpriseType;
	
	private String workAddress;
	
	private String isOneCert;
	
	private String busiLicenseNo;
	
	private String taxCertificateNo;
	
	private String localTaxNo;
	
	private String loanCardNo;
	
	private String lastCheckYear;
	
	private String busiScope;
	
	private String leaderReview;
	
	private String eventDesc;
	
	private String guarantorCertType;
	
	private String guarantorSex;
	
	private String guarantorCertNo;
	
	private String guarantorContactNo;
	
	private String guarantorAddress;
	
	private String maritalStatus;
	
	private String spouseName;
	
	private String spouseCertType;
	
	private String spouseCertNo;
	
	private String spouseContactNo;
	
	private String name;
	
	private String sex;
	
	private String certNo;
	
	private String houseNum;
	
	private String carNum;
	
	private String investAmount;
	
	private String depositAmount;
	
	private String marriage;
	
	private String mateName;
	
	private String mateCertType;
	
	private String mateCertNo;
	
	private String mateContactNo;
	
	private String spouseAddress;
	
	private String spouseImmovableProperty;
	
	private String spouseMovableProperty;
	
	private String hasBankLoan;
	
	private String hasFolkLoan;
	
	private Money bankLoanAmount = new Money(0, 0);
	
	private Money folkLoanAmount = new Money(0, 0);
	
	private String consumerLoanBank;
	
	private Money consumerLoanAmount = new Money(0, 0);
	
	private String consumerLoanStartDate;
	
	private String consumerLoanEndDate;
	
	private String businesLoanBank;
	
	private Money businesLoanAmount = new Money(0, 0);
	
	private String businesLoanStartDate;
	
	private String businesLoanEndDate;
	
	private String mortgageLoanBank;
	
	private Money mortgageLoanAmount = new Money(0, 0);
	
	private String mortgageLoanStartDate;
	
	private String mortgageLoanEndDate;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//已获得的资质证书
	private List<FInvestigationMainlyReviewCertificateInfo> certificates;
	//客户主要高管人员
	private List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams;
	//保证人主要财务指标
	private List<FinancialKpiInfo> kpies;
	
	//是否有民间借贷 有:true
	public boolean hasLoan1() {
		if (!isNull(this.hasFolkLoan)) {
			return this.hasFolkLoan.contains("F");
		}
		
		return false;
	}
	
	//是否有银行借贷 有:true
	public boolean hasLoan2() {
		if (!isNull(this.hasBankLoan)) {
			return this.hasBankLoan.contains("B");
		}
		
		return false;
	}
	
	//是否有银行借贷/消费类借款 有:true
	public boolean hasLoan21() {
		if (hasLoan2()) {
			return this.hasBankLoan.contains("C");
		}
		
		return false;
	}
	
	//是否有银行借贷/个人经营性贷款 有:true
	public boolean hasLoan22() {
		if (hasLoan2()) {
			return this.hasBankLoan.contains("P");
		}
		
		return false;
	}
	
	//是否有银行借贷/按揭类贷款 有:true
	public boolean hasLoan23() {
		if (hasLoan2()) {
			return this.hasBankLoan.contains("M");
		}
		
		return false;
	}
	
	public long getGuarantorId() {
		return this.guarantorId;
	}
	
	public void setGuarantorId(long guarantorId) {
		this.guarantorId = guarantorId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getGuarantorType() {
		return this.guarantorType;
	}
	
	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	
	public String getGuarantorName() {
		return this.guarantorName;
	}
	
	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}
	
	public String getEstablishedTime() {
		return this.establishedTime;
	}
	
	public void setEstablishedTime(String establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public String getOperatingTerm() {
		return this.operatingTerm;
	}
	
	public void setOperatingTerm(String operatingTerm) {
		this.operatingTerm = operatingTerm;
	}
	
	public String getLegalPersion() {
		return this.legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getLivingAddress() {
		return this.livingAddress;
	}
	
	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}
	
	public String getActualControlPerson() {
		return this.actualControlPerson;
	}
	
	public void setActualControlPerson(String actualControlPerson) {
		this.actualControlPerson = actualControlPerson;
	}
	
	public String getEnterpriseType() {
		return this.enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getWorkAddress() {
		return this.workAddress;
	}
	
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	
	public String getIsOneCert() {
		return this.isOneCert;
	}
	
	public void setIsOneCert(String isOneCert) {
		this.isOneCert = isOneCert;
	}
	
	public String getBusiLicenseNo() {
		return this.busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getTaxCertificateNo() {
		return this.taxCertificateNo;
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
		return this.loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	
	public String getLastCheckYear() {
		return this.lastCheckYear;
	}
	
	public void setLastCheckYear(String lastCheckYear) {
		this.lastCheckYear = lastCheckYear;
	}
	
	public String getBusiScope() {
		return this.busiScope;
	}
	
	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}
	
	public String getLeaderReview() {
		return this.leaderReview;
	}
	
	public void setLeaderReview(String leaderReview) {
		this.leaderReview = leaderReview;
	}
	
	public String getEventDesc() {
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getGuarantorCertType() {
		return this.guarantorCertType;
	}
	
	public void setGuarantorCertType(String guarantorCertType) {
		this.guarantorCertType = guarantorCertType;
	}
	
	public String getGuarantorSex() {
		return this.guarantorSex;
	}
	
	public void setGuarantorSex(String guarantorSex) {
		this.guarantorSex = guarantorSex;
	}
	
	public String getGuarantorCertNo() {
		return this.guarantorCertNo;
	}
	
	public void setGuarantorCertNo(String guarantorCertNo) {
		this.guarantorCertNo = guarantorCertNo;
	}
	
	public String getGuarantorContactNo() {
		return this.guarantorContactNo;
	}
	
	public void setGuarantorContactNo(String guarantorContactNo) {
		this.guarantorContactNo = guarantorContactNo;
	}
	
	public String getGuarantorAddress() {
		return this.guarantorAddress;
	}
	
	public void setGuarantorAddress(String guarantorAddress) {
		this.guarantorAddress = guarantorAddress;
	}
	
	public String getMaritalStatus() {
		return this.maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getSpouseName() {
		return this.spouseName;
	}
	
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	public String getSpouseCertType() {
		return this.spouseCertType;
	}
	
	public void setSpouseCertType(String spouseCertType) {
		this.spouseCertType = spouseCertType;
	}
	
	public String getSpouseCertNo() {
		return this.spouseCertNo;
	}
	
	public void setSpouseCertNo(String spouseCertNo) {
		this.spouseCertNo = spouseCertNo;
	}
	
	public String getSpouseContactNo() {
		return this.spouseContactNo;
	}
	
	public void setSpouseContactNo(String spouseContactNo) {
		this.spouseContactNo = spouseContactNo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getHouseNum() {
		return this.houseNum;
	}
	
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	
	public String getCarNum() {
		return this.carNum;
	}
	
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	public String getInvestAmount() {
		return this.investAmount;
	}
	
	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}
	
	public String getDepositAmount() {
		return this.depositAmount;
	}
	
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public String getMarriage() {
		return this.marriage;
	}
	
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	public String getMateName() {
		return this.mateName;
	}
	
	public void setMateName(String mateName) {
		this.mateName = mateName;
	}
	
	public String getMateCertType() {
		return this.mateCertType;
	}
	
	public void setMateCertType(String mateCertType) {
		this.mateCertType = mateCertType;
	}
	
	public String getMateCertNo() {
		return this.mateCertNo;
	}
	
	public void setMateCertNo(String mateCertNo) {
		this.mateCertNo = mateCertNo;
	}
	
	public String getMateContactNo() {
		return this.mateContactNo;
	}
	
	public void setMateContactNo(String mateContactNo) {
		this.mateContactNo = mateContactNo;
	}
	
	public String getSpouseAddress() {
		return this.spouseAddress;
	}
	
	public void setSpouseAddress(String spouseAddress) {
		this.spouseAddress = spouseAddress;
	}
	
	public String getSpouseImmovableProperty() {
		return this.spouseImmovableProperty;
	}
	
	public void setSpouseImmovableProperty(String spouseImmovableProperty) {
		this.spouseImmovableProperty = spouseImmovableProperty;
	}
	
	public String getSpouseMovableProperty() {
		return this.spouseMovableProperty;
	}
	
	public void setSpouseMovableProperty(String spouseMovableProperty) {
		this.spouseMovableProperty = spouseMovableProperty;
	}
	
	public String getHasBankLoan() {
		return this.hasBankLoan;
	}
	
	public void setHasBankLoan(String hasBankLoan) {
		this.hasBankLoan = hasBankLoan;
	}
	
	public String getHasFolkLoan() {
		return this.hasFolkLoan;
	}
	
	public void setHasFolkLoan(String hasFolkLoan) {
		this.hasFolkLoan = hasFolkLoan;
	}
	
	public Money getBankLoanAmount() {
		return this.bankLoanAmount;
	}
	
	public void setBankLoanAmount(Money bankLoanAmount) {
		this.bankLoanAmount = bankLoanAmount;
	}
	
	public Money getFolkLoanAmount() {
		return this.folkLoanAmount;
	}
	
	public void setFolkLoanAmount(Money folkLoanAmount) {
		this.folkLoanAmount = folkLoanAmount;
	}
	
	public String getConsumerLoanBank() {
		return this.consumerLoanBank;
	}
	
	public void setConsumerLoanBank(String consumerLoanBank) {
		this.consumerLoanBank = consumerLoanBank;
	}
	
	public Money getConsumerLoanAmount() {
		return this.consumerLoanAmount;
	}
	
	public void setConsumerLoanAmount(Money consumerLoanAmount) {
		this.consumerLoanAmount = consumerLoanAmount;
	}
	
	public String getConsumerLoanStartDate() {
		return this.consumerLoanStartDate;
	}
	
	public void setConsumerLoanStartDate(String consumerLoanStartDate) {
		this.consumerLoanStartDate = consumerLoanStartDate;
	}
	
	public String getConsumerLoanEndDate() {
		return this.consumerLoanEndDate;
	}
	
	public void setConsumerLoanEndDate(String consumerLoanEndDate) {
		this.consumerLoanEndDate = consumerLoanEndDate;
	}
	
	public String getBusinesLoanBank() {
		return this.businesLoanBank;
	}
	
	public void setBusinesLoanBank(String businesLoanBank) {
		this.businesLoanBank = businesLoanBank;
	}
	
	public Money getBusinesLoanAmount() {
		return this.businesLoanAmount;
	}
	
	public void setBusinesLoanAmount(Money businesLoanAmount) {
		this.businesLoanAmount = businesLoanAmount;
	}
	
	public String getBusinesLoanStartDate() {
		return this.businesLoanStartDate;
	}
	
	public void setBusinesLoanStartDate(String businesLoanStartDate) {
		this.businesLoanStartDate = businesLoanStartDate;
	}
	
	public String getBusinesLoanEndDate() {
		return this.businesLoanEndDate;
	}
	
	public void setBusinesLoanEndDate(String businesLoanEndDate) {
		this.businesLoanEndDate = businesLoanEndDate;
	}
	
	public String getMortgageLoanBank() {
		return this.mortgageLoanBank;
	}
	
	public void setMortgageLoanBank(String mortgageLoanBank) {
		this.mortgageLoanBank = mortgageLoanBank;
	}
	
	public Money getMortgageLoanAmount() {
		return this.mortgageLoanAmount;
	}
	
	public void setMortgageLoanAmount(Money mortgageLoanAmount) {
		this.mortgageLoanAmount = mortgageLoanAmount;
	}
	
	public String getMortgageLoanStartDate() {
		return this.mortgageLoanStartDate;
	}
	
	public void setMortgageLoanStartDate(String mortgageLoanStartDate) {
		this.mortgageLoanStartDate = mortgageLoanStartDate;
	}
	
	public String getMortgageLoanEndDate() {
		return this.mortgageLoanEndDate;
	}
	
	public void setMortgageLoanEndDate(String mortgageLoanEndDate) {
		this.mortgageLoanEndDate = mortgageLoanEndDate;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<FInvestigationMainlyReviewCertificateInfo> getCertificates() {
		return this.certificates;
	}
	
	public void setCertificates(List<FInvestigationMainlyReviewCertificateInfo> certificates) {
		this.certificates = certificates;
	}
	
	public List<FInvestigationMabilityReviewLeadingTeamInfo> getLeadingTeams() {
		return this.leadingTeams;
	}
	
	public void setLeadingTeams(List<FInvestigationMabilityReviewLeadingTeamInfo> leadingTeams) {
		this.leadingTeams = leadingTeams;
	}
	
	public List<FinancialKpiInfo> getKpies() {
		return this.kpies;
	}
	
	public void setKpies(List<FinancialKpiInfo> kpies) {
		this.kpies = kpies;
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
