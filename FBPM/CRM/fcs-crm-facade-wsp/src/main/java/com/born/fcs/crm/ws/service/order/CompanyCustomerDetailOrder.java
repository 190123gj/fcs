package com.born.fcs.crm.ws.service.order;

import java.util.Date;
import java.util.List;

import com.born.fcs.crm.ws.service.info.CompanyOwnershipStructureInfo;
import com.born.fcs.crm.ws.service.info.CompanyQualificationInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * @des:公司客户详情 add Order
 * **/
public class CompanyCustomerDetailOrder extends CustomerBaseOrder {
	
	private static final long serialVersionUID = 4415595010878936397L;
	/** 获得的资质证书 */
	List<CompanyQualificationInfo> companyQualification;
	/** 公司股权结构 */
	List<CompanyOwnershipStructureInfo> companyOwnershipStructure;
	/** 贷款卡号 */
	private String loanCardNo;
	/** 最后年检年度 */
	private String finalYearCheck;
	/** 行业编码 */
	private String industryCode;
	/** 所属行业名字 */
	private String industryName;
	/** 行业全称 */
	private String industryAllName;
	/** 是否地方政府融资平台企业 */
	private String isLocalGovPlatform;
	/** 是否外向型经济客户 */
	private String isExportOrientedEconomy;
	/** 是否集团客户 */
	private String isGroup;
	/** 是否上市公司 */
	private String isListedCompany;
	/** 企业性质 */
	private String enterpriseType;
	/** 注册资本 */
	private Money registerCapital;
	/** 币种 */
	private String moneyType;
	/** 币种名 */
	private String moneyTypeName;
	/** 实收资本 */
	private Money actualCapital;
	/** 成立时间 */
	private Date establishedTime;
	/** 地址 */
	private String address;
	/** 隶属关系 */
	private String subordinateRelationship;
	/** 企业地址 */
	private String companyAddress;
	/** 企业规模 */
	private String scale;
	/** 员工人数 */
	private long staffNum;
	/** 联系人 */
	private String contactMan;
	/** 联系电话 */
	private String contactNo;
	/** 经营范围 */
	private String busiScope;
	/** 主导产品 */
	private String majorProduct;
	/** 客户与我公司关系 */
	private String relation;
	/** 法定代表人 */
	private String legalPersion;
	/** 法定代表人身份证号码 */
	private String legalPersionCertNo;
	/** 法定代表人地址 */
	private String legalPersionAddress;
	/** 实际控制人 */
	private String actualControlMan;
	/** 实际控制人身份证 */
	private String actualControlManCertNo;
	/** 实际控制人证件类型 */
	private String actualControlManCertType;
	/** 实际控制人地址 */
	private String actualControlManAddress;
	/** 基本账户开户行 */
	private String bankAccount;
	/** 账号 */
	private String accountNo;
	/** 主要结算账户开户行1 */
	private String settleBankAccount1;
	/** 账号 */
	private String settleAccountNo1;
	/** 主要结算账户开户行2 */
	private String settleBankAccount2;
	/** 账号 */
	private String settleAccountNo2;
	/** 其他结算账户开户行 */
	private String settleBankAccount3;
	/** 账号 */
	private String settleAccountNo3;
	/** 总资产 */
	private Money totalAsset = Money.zero();
	/** 净资产 */
	private Money netAsset = Money.zero();
	/** 资产负债率 */
	private double assetLiabilityRatio = 0.00;
	/** 去年总资产 */
	private Money totalAssetLastYear = new Money(0, 0);
	/** 去年净资产 */
	private Money netAssetLastYear = new Money(0, 0);
	/** 去年资产负债率 */
	private double assetLiabilityRatioLastYear = 0.00;
	/** 流动比率 */
	private double liquidityRatio = 0.00;
	/** 速动比率 */
	private double quickRatio = 0.00;
	/** 去年销售收入 */
	private Money salesProceedsLastYear = Money.zero();
	/** 去年利润总额 */
	private Money totalProfitLastYear = Money.zero();
	/** 今年销售收入 */
	private Money salesProceedsThisYear = Money.zero();
	/** 今年利润总额 */
	private Money totalProfitThisYear = Money.zero();
	/** 营业执照号 */
	private String busiLicenseNo;
	/** 营业执照图片 */
	private String busiLicenseUrl;
	/** 是否三证合一 */
	private String isOneCert;
	/** 组织机构代码 */
	private String orgCode;
	/** 组织机构图片 */
	private String orgCodeUrl;
	/** 税务登记证 */
	private String taxCertificateNo;
	/** 税务登记证图片 */
	private String taxCertificateUrl;
	/** 地税证 */
	private String localTaxCertNo;
	/** 地税证拍照 */
	private String localTaxCertUrl;
	/** 客户申请书扫描件 */
	private String applyScanningUrl;
	/** 地方财政情况 */
	private String localFinance;
	/** 备用字段1 */
	private String info1;
	/** 备用字段2 */
	private String info2;
	/** 备用字段3 */
	private String info3;
	/** 创建时间 */
	private Date rawAddTime;
	
	public String getActualControlManCertType() {
		return actualControlManCertType;
	}
	
	public void setActualControlManCertType(String actualControlManCertType) {
		this.actualControlManCertType = actualControlManCertType;
	}
	
	public String getLocalTaxCertNo() {
		return localTaxCertNo;
	}
	
	public void setLocalTaxCertNo(String localTaxCertNo) {
		this.localTaxCertNo = localTaxCertNo;
	}
	
	public String getLocalTaxCertUrl() {
		return localTaxCertUrl;
	}
	
	public void setLocalTaxCertUrl(String localTaxCertUrl) {
		this.localTaxCertUrl = localTaxCertUrl;
	}
	
	public String getLoanCardNo() {
		return this.loanCardNo;
	}
	
	public void setLoanCardNo(String loanCardNo) {
		this.loanCardNo = loanCardNo;
	}
	
	public String getFinalYearCheck() {
		return this.finalYearCheck;
	}
	
	public void setFinalYearCheck(String finalYearCheck) {
		this.finalYearCheck = finalYearCheck;
	}
	
	@Override
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	@Override
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public String getIsLocalGovPlatform() {
		return this.isLocalGovPlatform;
	}
	
	public void setIsLocalGovPlatform(String isLocalGovPlatform) {
		this.isLocalGovPlatform = isLocalGovPlatform;
	}
	
	public String getIsExportOrientedEconomy() {
		return this.isExportOrientedEconomy;
	}
	
	public void setIsExportOrientedEconomy(String isExportOrientedEconomy) {
		this.isExportOrientedEconomy = isExportOrientedEconomy;
	}
	
	public String getIsListedCompany() {
		return this.isListedCompany;
	}
	
	public void setIsListedCompany(String isListedCompany) {
		this.isListedCompany = isListedCompany;
	}
	
	@Override
	public String getEnterpriseType() {
		return this.enterpriseType;
	}
	
	@Override
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public Money getRegisterCapital() {
		return this.registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	public Date getEstablishedTime() {
		return this.establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCompanyAddress() {
		return this.companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getScale() {
		return this.scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public long getStaffNum() {
		return this.staffNum;
	}
	
	public void setStaffNum(long staffNum) {
		this.staffNum = staffNum;
	}
	
	public String getContactMan() {
		return this.contactMan;
	}
	
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	
	public String getContactNo() {
		return this.contactNo;
	}
	
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getBusiScope() {
		return this.busiScope;
	}
	
	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}
	
	public String getMajorProduct() {
		return this.majorProduct;
	}
	
	public void setMajorProduct(String majorProduct) {
		this.majorProduct = majorProduct;
	}
	
	public String getRelation() {
		return this.relation;
	}
	
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getLegalPersion() {
		return this.legalPersion;
	}
	
	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}
	
	public String getLegalPersionCertNo() {
		return this.legalPersionCertNo;
	}
	
	public void setLegalPersionCertNo(String legalPersionCertNo) {
		this.legalPersionCertNo = legalPersionCertNo;
	}
	
	public String getLegalPersionAddress() {
		return this.legalPersionAddress;
	}
	
	public void setLegalPersionAddress(String legalPersionAddress) {
		this.legalPersionAddress = legalPersionAddress;
	}
	
	public String getActualControlMan() {
		return this.actualControlMan;
	}
	
	public void setActualControlMan(String actualControlMan) {
		this.actualControlMan = actualControlMan;
	}
	
	public String getActualControlManCertNo() {
		return this.actualControlManCertNo;
	}
	
	public void setActualControlManCertNo(String actualControlManCertNo) {
		this.actualControlManCertNo = actualControlManCertNo;
	}
	
	public String getActualControlManAddress() {
		return this.actualControlManAddress;
	}
	
	public void setActualControlManAddress(String actualControlManAddress) {
		this.actualControlManAddress = actualControlManAddress;
	}
	
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getMoneyType() {
		return this.moneyType;
	}
	
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	
	public String getMoneyTypeName() {
		return this.moneyTypeName;
	}
	
	public void setMoneyTypeName(String moneyTypeName) {
		this.moneyTypeName = moneyTypeName;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getSettleBankAccount1() {
		return this.settleBankAccount1;
	}
	
	public void setSettleBankAccount1(String settleBankAccount1) {
		this.settleBankAccount1 = settleBankAccount1;
	}
	
	public String getSettleAccountNo1() {
		return this.settleAccountNo1;
	}
	
	public void setSettleAccountNo1(String settleAccountNo1) {
		this.settleAccountNo1 = settleAccountNo1;
	}
	
	public String getSettleBankAccount2() {
		return this.settleBankAccount2;
	}
	
	public void setSettleBankAccount2(String settleBankAccount2) {
		this.settleBankAccount2 = settleBankAccount2;
	}
	
	public String getSettleAccountNo2() {
		return this.settleAccountNo2;
	}
	
	public void setSettleAccountNo2(String settleAccountNo2) {
		this.settleAccountNo2 = settleAccountNo2;
	}
	
	public String getSettleBankAccount3() {
		return this.settleBankAccount3;
	}
	
	public void setSettleBankAccount3(String settleBankAccount3) {
		this.settleBankAccount3 = settleBankAccount3;
	}
	
	public String getSettleAccountNo3() {
		return this.settleAccountNo3;
	}
	
	public void setSettleAccountNo3(String settleAccountNo3) {
		this.settleAccountNo3 = settleAccountNo3;
	}
	
	public Money getTotalAsset() {
		return this.totalAsset;
	}
	
	public void setTotalAsset(Money totalAsset) {
		this.totalAsset = totalAsset;
	}
	
	public Money getNetAsset() {
		return this.netAsset;
	}
	
	public void setNetAsset(Money netAsset) {
		this.netAsset = netAsset;
	}
	
	public double getAssetLiabilityRatio() {
		return this.assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(double assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}
	
	public double getLiquidityRatio() {
		return this.liquidityRatio;
	}
	
	public void setLiquidityRatio(double liquidityRatio) {
		this.liquidityRatio = liquidityRatio;
	}
	
	public double getQuickRatio() {
		return this.quickRatio;
	}
	
	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}
	
	public Money getSalesProceedsLastYear() {
		return this.salesProceedsLastYear;
	}
	
	public void setSalesProceedsLastYear(Money salesProceedsLastYear) {
		this.salesProceedsLastYear = salesProceedsLastYear;
	}
	
	public Money getTotalProfitLastYear() {
		return this.totalProfitLastYear;
	}
	
	public void setTotalProfitLastYear(Money totalProfitLastYear) {
		this.totalProfitLastYear = totalProfitLastYear;
	}
	
	public Money getSalesProceedsThisYear() {
		return this.salesProceedsThisYear;
	}
	
	public void setSalesProceedsThisYear(Money salesProceedsThisYear) {
		this.salesProceedsThisYear = salesProceedsThisYear;
	}
	
	public Money getTotalProfitThisYear() {
		return this.totalProfitThisYear;
	}
	
	public void setTotalProfitThisYear(Money totalProfitThisYear) {
		this.totalProfitThisYear = totalProfitThisYear;
	}
	
	@Override
	public String getBusiLicenseNo() {
		return this.busiLicenseNo;
	}
	
	@Override
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getBusiLicenseUrl() {
		return this.busiLicenseUrl;
	}
	
	public void setBusiLicenseUrl(String busiLicenseUrl) {
		this.busiLicenseUrl = busiLicenseUrl;
	}
	
	public String getIsOneCert() {
		return this.isOneCert;
	}
	
	public void setIsOneCert(String isOneCert) {
		this.isOneCert = isOneCert;
	}
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getOrgCodeUrl() {
		return this.orgCodeUrl;
	}
	
	public void setOrgCodeUrl(String orgCodeUrl) {
		this.orgCodeUrl = orgCodeUrl;
	}
	
	public String getTaxCertificateNo() {
		return this.taxCertificateNo;
	}
	
	public void setTaxCertificateNo(String taxCertificateNo) {
		this.taxCertificateNo = taxCertificateNo;
	}
	
	public String getTaxCertificateUrl() {
		return this.taxCertificateUrl;
	}
	
	public void setTaxCertificateUrl(String taxCertificateUrl) {
		this.taxCertificateUrl = taxCertificateUrl;
	}
	
	public String getApplyScanningUrl() {
		return this.applyScanningUrl;
	}
	
	public void setApplyScanningUrl(String applyScanningUrl) {
		this.applyScanningUrl = applyScanningUrl;
	}
	
	public String getLocalFinance() {
		return this.localFinance;
	}
	
	public void setLocalFinance(String localFinance) {
		this.localFinance = localFinance;
	}
	
	public String getInfo1() {
		return this.info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getInfo2() {
		return this.info2;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public String getInfo3() {
		return this.info3;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public Money getTotalAssetLastYear() {
		return this.totalAssetLastYear;
	}
	
	public void setTotalAssetLastYear(Money totalAssetLastYear) {
		this.totalAssetLastYear = totalAssetLastYear;
	}
	
	public Money getNetAssetLastYear() {
		return this.netAssetLastYear;
	}
	
	public void setNetAssetLastYear(Money netAssetLastYear) {
		this.netAssetLastYear = netAssetLastYear;
	}
	
	public double getAssetLiabilityRatioLastYear() {
		return this.assetLiabilityRatioLastYear;
	}
	
	public void setAssetLiabilityRatioLastYear(double assetLiabilityRatioLastYear) {
		this.assetLiabilityRatioLastYear = assetLiabilityRatioLastYear;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public List<CompanyQualificationInfo> getCompanyQualification() {
		return this.companyQualification;
	}
	
	public String getIsGroup() {
		return this.isGroup;
	}
	
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	
	public void setCompanyQualification(List<CompanyQualificationInfo> companyQualification) {
		this.companyQualification = companyQualification;
	}
	
	public List<CompanyOwnershipStructureInfo> getCompanyOwnershipStructure() {
		return this.companyOwnershipStructure;
	}
	
	public void setCompanyOwnershipStructure(	List<CompanyOwnershipStructureInfo> companyOwnershipStructure) {
		this.companyOwnershipStructure = companyOwnershipStructure;
	}
	
	public Money getActualCapital() {
		return this.actualCapital;
	}
	
	public void setActualCapital(Money actualCapital) {
		this.actualCapital = actualCapital;
	}
	
	public String getSubordinateRelationship() {
		return this.subordinateRelationship;
	}
	
	public void setSubordinateRelationship(String subordinateRelationship) {
		this.subordinateRelationship = subordinateRelationship;
	}
	
	public String getIndustryAllName() {
		return this.industryAllName;
	}
	
	public void setIndustryAllName(String industryAllName) {
		this.industryAllName = industryAllName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyCustomerDetailOrder [companyQualification=");
		builder.append(companyQualification);
		builder.append(", companyOwnershipStructure=");
		builder.append(companyOwnershipStructure);
		builder.append(", loanCardNo=");
		builder.append(loanCardNo);
		builder.append(", finalYearCheck=");
		builder.append(finalYearCheck);
		builder.append(", industryCode=");
		builder.append(industryCode);
		builder.append(", industryName=");
		builder.append(industryName);
		builder.append(", industryAllName=");
		builder.append(industryAllName);
		builder.append(", isLocalGovPlatform=");
		builder.append(isLocalGovPlatform);
		builder.append(", isExportOrientedEconomy=");
		builder.append(isExportOrientedEconomy);
		builder.append(", isGroup=");
		builder.append(isGroup);
		builder.append(", isListedCompany=");
		builder.append(isListedCompany);
		builder.append(", enterpriseType=");
		builder.append(enterpriseType);
		builder.append(", registerCapital=");
		builder.append(registerCapital);
		builder.append(", moneyType=");
		builder.append(moneyType);
		builder.append(", moneyTypeName=");
		builder.append(moneyTypeName);
		builder.append(", actualCapital=");
		builder.append(actualCapital);
		builder.append(", establishedTime=");
		builder.append(establishedTime);
		builder.append(", address=");
		builder.append(address);
		builder.append(", subordinateRelationship=");
		builder.append(subordinateRelationship);
		builder.append(", companyAddress=");
		builder.append(companyAddress);
		builder.append(", scale=");
		builder.append(scale);
		builder.append(", staffNum=");
		builder.append(staffNum);
		builder.append(", contactMan=");
		builder.append(contactMan);
		builder.append(", contactNo=");
		builder.append(contactNo);
		builder.append(", busiScope=");
		builder.append(busiScope);
		builder.append(", majorProduct=");
		builder.append(majorProduct);
		builder.append(", relation=");
		builder.append(relation);
		builder.append(", legalPersion=");
		builder.append(legalPersion);
		builder.append(", legalPersionCertNo=");
		builder.append(legalPersionCertNo);
		builder.append(", legalPersionAddress=");
		builder.append(legalPersionAddress);
		builder.append(", actualControlMan=");
		builder.append(actualControlMan);
		builder.append(", actualControlManCertNo=");
		builder.append(actualControlManCertNo);
		builder.append(", actualControlManCertType=");
		builder.append(actualControlManCertType);
		builder.append(", actualControlManAddress=");
		builder.append(actualControlManAddress);
		builder.append(", bankAccount=");
		builder.append(bankAccount);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", settleBankAccount1=");
		builder.append(settleBankAccount1);
		builder.append(", settleAccountNo1=");
		builder.append(settleAccountNo1);
		builder.append(", settleBankAccount2=");
		builder.append(settleBankAccount2);
		builder.append(", settleAccountNo2=");
		builder.append(settleAccountNo2);
		builder.append(", settleBankAccount3=");
		builder.append(settleBankAccount3);
		builder.append(", settleAccountNo3=");
		builder.append(settleAccountNo3);
		builder.append(", totalAsset=");
		builder.append(totalAsset);
		builder.append(", netAsset=");
		builder.append(netAsset);
		builder.append(", assetLiabilityRatio=");
		builder.append(assetLiabilityRatio);
		builder.append(", totalAssetLastYear=");
		builder.append(totalAssetLastYear);
		builder.append(", netAssetLastYear=");
		builder.append(netAssetLastYear);
		builder.append(", assetLiabilityRatioLastYear=");
		builder.append(assetLiabilityRatioLastYear);
		builder.append(", liquidityRatio=");
		builder.append(liquidityRatio);
		builder.append(", quickRatio=");
		builder.append(quickRatio);
		builder.append(", salesProceedsLastYear=");
		builder.append(salesProceedsLastYear);
		builder.append(", totalProfitLastYear=");
		builder.append(totalProfitLastYear);
		builder.append(", salesProceedsThisYear=");
		builder.append(salesProceedsThisYear);
		builder.append(", totalProfitThisYear=");
		builder.append(totalProfitThisYear);
		builder.append(", busiLicenseNo=");
		builder.append(busiLicenseNo);
		builder.append(", busiLicenseUrl=");
		builder.append(busiLicenseUrl);
		builder.append(", isOneCert=");
		builder.append(isOneCert);
		builder.append(", orgCode=");
		builder.append(orgCode);
		builder.append(", orgCodeUrl=");
		builder.append(orgCodeUrl);
		builder.append(", taxCertificateNo=");
		builder.append(taxCertificateNo);
		builder.append(", taxCertificateUrl=");
		builder.append(taxCertificateUrl);
		builder.append(", localTaxCertNo=");
		builder.append(localTaxCertNo);
		builder.append(", localTaxCertUrl=");
		builder.append(localTaxCertUrl);
		builder.append(", applyScanningUrl=");
		builder.append(applyScanningUrl);
		builder.append(", localFinance=");
		builder.append(localFinance);
		builder.append(", info1=");
		builder.append(info1);
		builder.append(", info2=");
		builder.append(info2);
		builder.append(", info3=");
		builder.append(info3);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append("]");
		return builder.toString();
	}
	
}
