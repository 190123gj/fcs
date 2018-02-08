package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CertTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.EnterpriseNatureEnum;
import com.born.fcs.pm.ws.enums.EnterpriseScaleEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 表单- 项目立项 - 企业基本信息
 * 
 * @author wuzj
 * 
 */
public class FProjectCustomerBaseInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -2047972115419237820L;
	/** 主键 */
	private long id;
	/** 个人证件号 */
	private String certNo;
	/** 个人证件类型、法人证件类型 */
	private CertTypeEnum certType;
	/** 行业编码 */
	private String industryCode;
	/** 行业名称 */
	private String industryName;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 用信客户ID */
	private long trueCustomerId;
	/** 用信客户名称 */
	private String trueCustomerName;
	/** 用信客户类型 */
	private CustomerTypeEnum trueCustomerType;
	/** 是否地方政府融资平台 */
	private BooleanEnum isLocalGovPlatform;
	/** 是否外向型经济客户 */
	private BooleanEnum isExportOrientedEconomy;
	/** 企业性质 */
	private EnterpriseNatureEnum enterpriseType;
	/** 所属区域 - 国家编码 */
	private String countryCode;
	/** 所属区域 - 国家名称 */
	private String countryName;
	/** 所属区域 - 省编码 */
	private String provinceCode;
	/** 所属区域 - 省名称 */
	private String provinceName;
	/** 所属区域 - 市编码 */
	private String cityCode;
	/** 所属区域 - 市名称 */
	private String cityName;
	/** 所属区域 - 地区编码 */
	private String countyCode;
	/** 所属区域 - 地区名称 */
	private String countyName;
	
	/** 地区是否选择完整 */
	private String isRegionComplete;
	/** 地址 */
	private String address;
	/** 联系人 */
	private String contactMan;
	/** 联系人电话 */
	private String contactNo;
	/** 成立时间 */
	private Date establishedTime;
	/** 企业规模 */
	private EnterpriseScaleEnum scale;
	/** 员工人数 */
	private long staffNum;
	/** 经营范围 */
	private String busiScope;
	/** 主导产品 */
	private String majorProduct;
	/** 法定代表人 */
	private String legalPersion;
	/** 法定代表人证件号 */
	private String legalPersionCertNo;
	/** 法定代表人地址 */
	private String legalPersionAddress;
	/** 实际控制人 */
	private String actualControlMan;
	/** 实际控制人证件号 */
	private String actualControlManCertNo;
	/** 实际控制人证件类型 */
	private CertTypeEnum actualControlManCertType;
	/** 实际控制人地址 */
	private String actualControlManAddress;
	/** 注册资本 */
	private Money registerCapital = new Money(0, 0);
	/** 币种 */
	private String moneyType;
	/** 币种名称 */
	private String moneyTypeName;
	/** 实收资本 */
	private Money actualCapital = new Money(0, 0);
	/** 总资产 */
	private Money totalAsset = new Money(0, 0);
	/** 净资产 */
	private Money netAsset = new Money(0, 0);
	/** 资产负债率 */
	private double assetLiabilityRatio;
	/** 流动比率 */
	private double liquidityRatio;
	/** 速动比率 */
	private double quickRatio;
	/** 去年销售收入 */
	private Money salesProceedsLastYear = new Money(0, 0);
	/** 今年销售收入 */
	private Money salesProceedsThisYear = new Money(0, 0);
	/** 去年利润总额 */
	private Money totalProfitLastYear = new Money(0, 0);
	/** 今年利润总额 */
	private Money totalProfitThisYear = new Money(0, 0);
	/** 是否三证合一 */
	private BooleanEnum isOneCert;
	/** 营业执照号 */
	private String busiLicenseNo;
	/** 营业执照拍照、身份证正面、证件照 */
	private String busiLicenseUrl;
	/** 组织机构号 */
	private String orgCode;
	/** 组织机构证件拍照、身份证反面 */
	private String orgCodeUrl;
	/** 国税登记证 */
	private String taxCertificateNo;
	/** 国税登记证拍照 */
	private String taxCertificateUrl;
	/** 地税证 */
	private String localTaxCertNo;
	/** 地税证拍照 */
	private String localTaxCertUrl;
	/** 客户申请书扫描件 */
	private String applyScanningUrl;
	/** 地方财政情况 */
	private String localFinance;
	/** 创建时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	public CertTypeEnum getCertType() {
		return this.certType;
	}
	
	public void setCertType(CertTypeEnum certType) {
		this.certType = certType;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public long getTrueCustomerId() {
		return this.trueCustomerId;
	}
	
	public void setTrueCustomerId(long trueCustomerId) {
		this.trueCustomerId = trueCustomerId;
	}
	
	public String getTrueCustomerName() {
		return this.trueCustomerName;
	}
	
	public void setTrueCustomerName(String trueCustomerName) {
		this.trueCustomerName = trueCustomerName;
	}
	
	public CustomerTypeEnum getTrueCustomerType() {
		return this.trueCustomerType;
	}
	
	public void setTrueCustomerType(CustomerTypeEnum trueCustomerType) {
		this.trueCustomerType = trueCustomerType;
	}
	
	public BooleanEnum getIsLocalGovPlatform() {
		return this.isLocalGovPlatform;
	}
	
	public void setIsLocalGovPlatform(BooleanEnum isLocalGovPlatform) {
		this.isLocalGovPlatform = isLocalGovPlatform;
	}
	
	public BooleanEnum getIsExportOrientedEconomy() {
		return this.isExportOrientedEconomy;
	}
	
	public void setIsExportOrientedEconomy(BooleanEnum isExportOrientedEconomy) {
		this.isExportOrientedEconomy = isExportOrientedEconomy;
	}
	
	public EnterpriseNatureEnum getEnterpriseType() {
		return this.enterpriseType;
	}
	
	public void setEnterpriseType(EnterpriseNatureEnum enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		return this.countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getProvinceCode() {
		return this.provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityCode() {
		return this.cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCountyCode() {
		return this.countyCode;
	}
	
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	public String getCountyName() {
		return this.countyName;
	}
	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getIsRegionComplete() {
		return this.isRegionComplete;
	}
	
	public void setIsRegionComplete(String isRegionComplete) {
		this.isRegionComplete = isRegionComplete;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
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
	
	public Date getEstablishedTime() {
		return this.establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public EnterpriseScaleEnum getScale() {
		return this.scale;
	}
	
	public void setScale(EnterpriseScaleEnum scale) {
		this.scale = scale;
	}
	
	public long getStaffNum() {
		return this.staffNum;
	}
	
	public void setStaffNum(long staffNum) {
		this.staffNum = staffNum;
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
	
	public CertTypeEnum getActualControlManCertType() {
		return this.actualControlManCertType;
	}
	
	public void setActualControlManCertType(CertTypeEnum actualControlManCertType) {
		this.actualControlManCertType = actualControlManCertType;
	}
	
	public String getActualControlManAddress() {
		return this.actualControlManAddress;
	}
	
	public void setActualControlManAddress(String actualControlManAddress) {
		this.actualControlManAddress = actualControlManAddress;
	}
	
	public Money getRegisterCapital() {
		return this.registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
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
	
	public Money getActualCapital() {
		return this.actualCapital;
	}
	
	public void setActualCapital(Money actualCapital) {
		this.actualCapital = actualCapital;
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
	
	public Money getSalesProceedsThisYear() {
		return this.salesProceedsThisYear;
	}
	
	public void setSalesProceedsThisYear(Money salesProceedsThisYear) {
		this.salesProceedsThisYear = salesProceedsThisYear;
	}
	
	public Money getTotalProfitLastYear() {
		return this.totalProfitLastYear;
	}
	
	public void setTotalProfitLastYear(Money totalProfitLastYear) {
		this.totalProfitLastYear = totalProfitLastYear;
	}
	
	public Money getTotalProfitThisYear() {
		return this.totalProfitThisYear;
	}
	
	public void setTotalProfitThisYear(Money totalProfitThisYear) {
		this.totalProfitThisYear = totalProfitThisYear;
	}
	
	public BooleanEnum getIsOneCert() {
		return this.isOneCert;
	}
	
	public void setIsOneCert(BooleanEnum isOneCert) {
		this.isOneCert = isOneCert;
	}
	
	public String getBusiLicenseNo() {
		return this.busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getBusiLicenseUrl() {
		return this.busiLicenseUrl;
	}
	
	public void setBusiLicenseUrl(String busiLicenseUrl) {
		this.busiLicenseUrl = busiLicenseUrl;
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
	
	public String getLocalTaxCertNo() {
		return this.localTaxCertNo;
	}
	
	public void setLocalTaxCertNo(String localTaxCertNo) {
		this.localTaxCertNo = localTaxCertNo;
	}
	
	public String getLocalTaxCertUrl() {
		return this.localTaxCertUrl;
	}
	
	public void setLocalTaxCertUrl(String localTaxCertUrl) {
		this.localTaxCertUrl = localTaxCertUrl;
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
	
}
