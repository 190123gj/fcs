package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.born.fcs.pm.ws.order.financialkpi.FinancialKpiOrder;
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
public class FInvestigationCsRationalityReviewGuarantorOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 3672080152617657559L;
	
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
	
	private String bankLoanAmountStr;
	
	private String folkLoanAmountStr;
	
	private String consumerLoanBank;
	
	private String consumerLoanAmountStr;
	
	private String consumerLoanStartDate;
	
	private String consumerLoanEndDate;
	
	private String businesLoanBank;
	
	private String businesLoanAmountStr;
	
	private String businesLoanStartDate;
	
	private String businesLoanEndDate;
	
	private String mortgageLoanBank;
	
	private String mortgageLoanAmountStr;
	
	private String mortgageLoanStartDate;
	
	private String mortgageLoanEndDate;
	
	private int sortOrder;
	//已获得的资质证书
	private List<FInvestigationMainlyReviewCertificateOrder> certificates;
	//客户主要高管人员
	private List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams;
	//保证人主要财务指标
	private List<FinancialKpiOrder> kpies;
	
	public boolean isNull() {
		return isNull(guarantorName) && isNull(establishedTime) && isNull(operatingTerm)
				&& isNull(legalPersion) && isNull(orgCode) && isNull(livingAddress)
				&& isNull(actualControlPerson) && isNull(enterpriseType) && isNull(workAddress)
				&& isNull(isOneCert) && isNull(busiLicenseNo) && isNull(taxCertificateNo)
				&& isNull(localTaxNo) && isNull(loanCardNo) && isNull(lastCheckYear)
				&& isNull(busiScope) && isNull(leaderReview) && isNull(eventDesc)
				&& isNull(guarantorCertType) && isNull(guarantorSex) && isNull(guarantorCertNo)
				&& isNull(guarantorContactNo) && isNull(guarantorAddress) && isNull(maritalStatus)
				&& isNull(spouseName) && isNull(spouseCertType) && isNull(spouseCertNo)
				&& isNull(spouseContactNo) && isNull(name) && isNull(sex) && isNull(certNo)
				&& isNull(houseNum) && isNull(carNum) && isNull(investAmount)
				&& isNull(depositAmount) && isNull(marriage) && isNull(mateName)
				&& isNull(mateCertType) && isNull(mateCertNo) && isNull(mateContactNo)
				&& isNull(spouseAddress) && isNull(spouseImmovableProperty)
				&& isNull(spouseMovableProperty) && isNull(hasBankLoan) && isNull(hasFolkLoan)
				&& isNull(bankLoanAmountStr) && isNull(folkLoanAmountStr)
				&& isNull(consumerLoanBank) && isNull(consumerLoanAmountStr)
				&& isNull(consumerLoanStartDate) && isNull(consumerLoanEndDate)
				&& isNull(businesLoanBank) && isNull(businesLoanAmountStr)
				&& isNull(businesLoanStartDate) && isNull(businesLoanEndDate)
				&& isNull(mortgageLoanBank) && isNull(mortgageLoanAmountStr)
				&& isNull(mortgageLoanStartDate) && isNull(mortgageLoanEndDate)
				&& isNull1(certificates) && isNull2(leadingTeams) && isNull3(kpies);
	}
	
	private boolean isNull1(List<FInvestigationMainlyReviewCertificateOrder> list) {
		if (null == list || list.size() <= 0) {
			return true;
		}
		
		for (FInvestigationMainlyReviewCertificateOrder order : list) {
			if (!order.isNull()) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isNull2(List<FInvestigationMabilityReviewLeadingTeamOrder> list) {
		if (null == list || list.size() <= 0) {
			return true;
		}
		
		for (FInvestigationMabilityReviewLeadingTeamOrder order : list) {
			if (!order.isNull()) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isNull3(List<FinancialKpiOrder> list) {
		if (null == list || list.size() <= 0) {
			return true;
		}
		
		for (FinancialKpiOrder order : list) {
			if (!order.isNull()) {
				return false;
			}
		}
		
		return true;
	}
	
	//========== getters and setters ==========
	
	public long getGuarantorId() {
		return guarantorId;
	}
	
	public void setGuarantorId(long guarantorId) {
		this.guarantorId = guarantorId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getGuarantorType() {
		return guarantorType;
	}
	
	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	
	public String getGuarantorName() {
		return guarantorName;
	}
	
	public void setGuarantorName(String guarantorName) {
		this.guarantorName = guarantorName;
	}
	
	public String getEstablishedTime() {
		return establishedTime;
	}
	
	public void setEstablishedTime(String establishedTime) {
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
	
	public String getIsOneCert() {
		return isOneCert;
	}
	
	public void setIsOneCert(String isOneCert) {
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
		return localTaxNo;
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
	
	public String getLeaderReview() {
		return leaderReview;
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
	
	public void setEventDesc2(String eventDesc2) {
		this.eventDesc = eventDesc2;
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
		return maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getSpouseName() {
		return spouseName;
	}
	
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	public String getSpouseCertType() {
		return spouseCertType;
	}
	
	public void setSpouseCertType(String spouseCertType) {
		this.spouseCertType = spouseCertType;
	}
	
	public String getSpouseCertNo() {
		return spouseCertNo;
	}
	
	public void setSpouseCertNo(String spouseCertNo) {
		this.spouseCertNo = spouseCertNo;
	}
	
	public String getSpouseContactNo() {
		return spouseContactNo;
	}
	
	public void setSpouseContactNo(String spouseContactNo) {
		this.spouseContactNo = spouseContactNo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public String getHouseNum() {
		return houseNum;
	}
	
	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}
	
	public String getCarNum() {
		return carNum;
	}
	
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	public String getInvestAmount() {
		return investAmount;
	}
	
	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}
	
	public String getDepositAmount() {
		return depositAmount;
	}
	
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public String getMarriage() {
		return marriage;
	}
	
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	public String getMateName() {
		return mateName;
	}
	
	public void setMateName(String mateName) {
		this.mateName = mateName;
	}
	
	public String getMateCertType() {
		return mateCertType;
	}
	
	public void setMateCertType(String mateCertType) {
		this.mateCertType = mateCertType;
	}
	
	public String getMateCertNo() {
		return mateCertNo;
	}
	
	public void setMateCertNo(String mateCertNo) {
		this.mateCertNo = mateCertNo;
	}
	
	public String getMateContactNo() {
		return mateContactNo;
	}
	
	public void setMateContactNo(String mateContactNo) {
		this.mateContactNo = mateContactNo;
	}
	
	public String getSpouseAddress() {
		return spouseAddress;
	}
	
	public void setSpouseAddress(String spouseAddress) {
		this.spouseAddress = spouseAddress;
	}
	
	public String getSpouseImmovableProperty() {
		return spouseImmovableProperty;
	}
	
	public void setSpouseImmovableProperty(String spouseImmovableProperty) {
		this.spouseImmovableProperty = spouseImmovableProperty;
	}
	
	public String getSpouseMovableProperty() {
		return spouseMovableProperty;
	}
	
	public void setSpouseMovableProperty(String spouseMovableProperty) {
		this.spouseMovableProperty = spouseMovableProperty;
	}
	
	public String getHasBankLoan() {
		return hasBankLoan;
	}
	
	public void setHasBankLoan(String hasBankLoan) {
		this.hasBankLoan = hasBankLoan;
	}
	
	public String getHasFolkLoan() {
		return hasFolkLoan;
	}
	
	public void setHasFolkLoan(String hasFolkLoan) {
		this.hasFolkLoan = hasFolkLoan;
	}
	
	public Money getBankLoanAmount() {
		if (isNull(bankLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(bankLoanAmountStr);
	}
	
	public void setBankLoanAmountStr(String bankLoanAmountStr) {
		this.bankLoanAmountStr = bankLoanAmountStr;
	}
	
	public Money getFolkLoanAmount() {
		if (isNull(folkLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(folkLoanAmountStr);
	}
	
	public void setFolkLoanAmountStr(String folkLoanAmountStr) {
		this.folkLoanAmountStr = folkLoanAmountStr;
	}
	
	public String getConsumerLoanBank() {
		return consumerLoanBank;
	}
	
	public void setConsumerLoanBank(String consumerLoanBank) {
		this.consumerLoanBank = consumerLoanBank;
	}
	
	public Money getConsumerLoanAmount() {
		if (isNull(consumerLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(consumerLoanAmountStr);
	}
	
	public void setConsumerLoanAmountStr(String consumerLoanAmountStr) {
		this.consumerLoanAmountStr = consumerLoanAmountStr;
	}
	
	public String getConsumerLoanStartDate() {
		return consumerLoanStartDate;
	}
	
	public void setConsumerLoanStartDate(String consumerLoanStartDate) {
		this.consumerLoanStartDate = consumerLoanStartDate;
	}
	
	public String getConsumerLoanEndDate() {
		return consumerLoanEndDate;
	}
	
	public void setConsumerLoanEndDate(String consumerLoanEndDate) {
		this.consumerLoanEndDate = consumerLoanEndDate;
	}
	
	public String getBusinesLoanBank() {
		return businesLoanBank;
	}
	
	public void setBusinesLoanBank(String businesLoanBank) {
		this.businesLoanBank = businesLoanBank;
	}
	
	public Money getBusinesLoanAmount() {
		if (isNull(businesLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(businesLoanAmountStr);
	}
	
	public void setBusinesLoanAmountStr(String businesLoanAmountStr) {
		this.businesLoanAmountStr = businesLoanAmountStr;
	}
	
	public String getBusinesLoanStartDate() {
		return businesLoanStartDate;
	}
	
	public void setBusinesLoanStartDate(String businesLoanStartDate) {
		this.businesLoanStartDate = businesLoanStartDate;
	}
	
	public String getBusinesLoanEndDate() {
		return businesLoanEndDate;
	}
	
	public void setBusinesLoanEndDate(String businesLoanEndDate) {
		this.businesLoanEndDate = businesLoanEndDate;
	}
	
	public String getMortgageLoanBank() {
		return mortgageLoanBank;
	}
	
	public void setMortgageLoanBank(String mortgageLoanBank) {
		this.mortgageLoanBank = mortgageLoanBank;
	}
	
	public Money getMortgageLoanAmount() {
		if (isNull(mortgageLoanAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(mortgageLoanAmountStr);
	}
	
	public void setMortgageLoanAmountStr(String mortgageLoanAmountStr) {
		this.mortgageLoanAmountStr = mortgageLoanAmountStr;
	}
	
	public String getMortgageLoanStartDate() {
		return mortgageLoanStartDate;
	}
	
	public void setMortgageLoanStartDate(String mortgageLoanStartDate) {
		this.mortgageLoanStartDate = mortgageLoanStartDate;
	}
	
	public String getMortgageLoanEndDate() {
		return mortgageLoanEndDate;
	}
	
	public void setMortgageLoanEndDate(String mortgageLoanEndDate) {
		this.mortgageLoanEndDate = mortgageLoanEndDate;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public List<FInvestigationMainlyReviewCertificateOrder> getCertificates() {
		return this.certificates;
	}
	
	public void setCertificates(List<FInvestigationMainlyReviewCertificateOrder> certificates) {
		this.certificates = certificates;
	}
	
	public List<FInvestigationMabilityReviewLeadingTeamOrder> getLeadingTeams() {
		return this.leadingTeams;
	}
	
	public void setLeadingTeams(List<FInvestigationMabilityReviewLeadingTeamOrder> leadingTeams) {
		this.leadingTeams = leadingTeams;
	}
	
	public List<FinancialKpiOrder> getKpies() {
		return this.kpies;
	}
	
	public void setKpies(List<FinancialKpiOrder> kpies) {
		this.kpies = kpies;
	}
	
	public String getBankLoanAmountStr() {
		return this.bankLoanAmountStr;
	}

	public String getFolkLoanAmountStr() {
		return this.folkLoanAmountStr;
	}

	public String getConsumerLoanAmountStr() {
		return this.consumerLoanAmountStr;
	}

	public String getBusinesLoanAmountStr() {
		return this.businesLoanAmountStr;
	}

	public String getMortgageLoanAmountStr() {
		return this.mortgageLoanAmountStr;
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
