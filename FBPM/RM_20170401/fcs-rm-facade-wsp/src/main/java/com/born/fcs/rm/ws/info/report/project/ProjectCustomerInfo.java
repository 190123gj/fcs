package com.born.fcs.rm.ws.info.report.project;

import java.util.Date;

import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 客户信息
 * 
 * @author lirz
 * 
 * 2016-8-9 下午5:50:04
 */
public class ProjectCustomerInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 7911986148067523679L;
	
	/** 主键 */
	private long projectCustomerId;
	/** 报告ID */
	private long reportId;
	/** 报送年 */
	private int reportYear;
	/** 报送月 */
	private int reportMonth;
	/** 对应客户ID */
	private String customerId;
	/** 对应客户名称 */
	private String customerName;
	/** 营业执照号 */
	private String busiLicenseNo;
	/** 行业编码 */
	private String industryCode;
	/** 所属行业名字 */
	private String industryName;
	/** 是否地方政府融资平台企业 */
	private String isLocalGovPlatform;
	/** 是否外向型经济客户 */
	private String isExportOrientedEconomy;
	/** 企业性质 */
	private String enterpriseType;
	/** 注册资本 */
	private Money registerCapital;
	/** 成立时间 */
	private Date establishedTime;
	/** 所属区域 - 国家名称 */
	private String countryName;
	/** 所属区域 - 国家编码 */
	private String countryCode;
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
	/** 地址 */
	private String address;
	/** 企业地址 */
	private String companyAddress;
	/** 企业规模 */
	private String scale;
	/** 员工人数 */
	private long staffNum;
	/** 总资产 */
	private Money totalAsset;
	/** 净资产 */
	private Money netAsset;
	/** 资产负债率 */
	private double assetLiabilityRatio;
	/** 流动比率 */
	private double liquidityRatio;
	/** 速动比率 */
	private double quickRatio;
	/** 去年销售收入 */
	private Money salesProceedsLastYear;
	/** 去年利润总额 */
	private Money totalProfitLastYear;
	/** 今年销售收入 */
	private Money salesProceedsThisYear;
	/** 今年利润总额 */
	private Money totalProfitThisYear;
	/** 客户首次入库时间 */
	private Date storageDate;
	/** 是否本年新增客户 */
	private String newAdd;
	/** 是否在保客户 */
	private String releasable;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getProjectCustomerId() {
		return projectCustomerId;
	}
	
	public void setProjectCustomerId(long projectCustomerId) {
		this.projectCustomerId = projectCustomerId;
	}
	
	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public int getReportYear() {
		return reportYear;
	}
	
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	
	public int getReportMonth() {
		return reportMonth;
	}
	
	public void setReportMonth(int reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiLicenseNo() {
		return busiLicenseNo;
	}
	
	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}
	
	public String getIndustryCode() {
		return industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public String getIsLocalGovPlatform() {
		return isLocalGovPlatform;
	}
	
	public void setIsLocalGovPlatform(String isLocalGovPlatform) {
		this.isLocalGovPlatform = isLocalGovPlatform;
	}
	
	public String getIsExportOrientedEconomy() {
		return isExportOrientedEconomy;
	}
	
	public void setIsExportOrientedEconomy(String isExportOrientedEconomy) {
		this.isExportOrientedEconomy = isExportOrientedEconomy;
	}
	
	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public Money getRegisterCapital() {
		return registerCapital;
	}
	
	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
	}
	
	public Date getEstablishedTime() {
		return establishedTime;
	}
	
	public void setEstablishedTime(Date establishedTime) {
		this.establishedTime = establishedTime;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCountyCode() {
		return countyCode;
	}
	
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	public String getCountyName() {
		return countyName;
	}
	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getScale() {
		return scale;
	}
	
	public void setScale(String scale) {
		this.scale = scale;
	}
	
	public long getStaffNum() {
		return staffNum;
	}
	
	public void setStaffNum(long staffNum) {
		this.staffNum = staffNum;
	}
	
	public Money getTotalAsset() {
		return totalAsset;
	}
	
	public void setTotalAsset(Money totalAsset) {
		this.totalAsset = totalAsset;
	}
	
	public Money getNetAsset() {
		return netAsset;
	}
	
	public void setNetAsset(Money netAsset) {
		this.netAsset = netAsset;
	}
	
	public double getAssetLiabilityRatio() {
		return assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(double assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}
	
	public double getLiquidityRatio() {
		return liquidityRatio;
	}
	
	public void setLiquidityRatio(double liquidityRatio) {
		this.liquidityRatio = liquidityRatio;
	}
	
	public double getQuickRatio() {
		return quickRatio;
	}
	
	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}
	
	public Money getSalesProceedsLastYear() {
		return salesProceedsLastYear;
	}
	
	public void setSalesProceedsLastYear(Money salesProceedsLastYear) {
		this.salesProceedsLastYear = salesProceedsLastYear;
	}
	
	public Money getTotalProfitLastYear() {
		return totalProfitLastYear;
	}
	
	public void setTotalProfitLastYear(Money totalProfitLastYear) {
		this.totalProfitLastYear = totalProfitLastYear;
	}
	
	public Money getSalesProceedsThisYear() {
		return salesProceedsThisYear;
	}
	
	public void setSalesProceedsThisYear(Money salesProceedsThisYear) {
		this.salesProceedsThisYear = salesProceedsThisYear;
	}
	
	public Money getTotalProfitThisYear() {
		return totalProfitThisYear;
	}
	
	public void setTotalProfitThisYear(Money totalProfitThisYear) {
		this.totalProfitThisYear = totalProfitThisYear;
	}
	
	public Date getStorageDate() {
		return storageDate;
	}
	
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	
	public String getNewAdd() {
		return newAdd;
	}
	
	public void setNewAdd(String newAdd) {
		this.newAdd = newAdd;
	}
	
	public String getReleasable() {
		return releasable;
	}
	
	public void setReleasable(String releasable) {
		this.releasable = releasable;
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
	
}
