package com.born.fcs.am.ws.order.assesscompany;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryFormBase;

/**
 * 评估公司申请单查询Order
 * 
 * @author Ji
 */
public class FAssessCompanyApplyQueryOrder extends QueryFormBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2056222389553176079L;

	private long id;

	private String projectCode;

	private String projectName;

	private long customerId;

	private String customerName;

	private String customerType;

	private String customerAddr;

	private String countryName;

	private String countryCode;

	private String provinceCode;

	private String provinceName;

	private String cityCode;

	private String cityName;

	private String countyCode;

	private String countyName;

	private String qualityLand;

	private String qualityHouse;

	private String qualityAssets;

	private long appointPerson;

	private String appointPersonAccount;

	private String appointPersonName;

	private String appointWay;

	private String companyId;

	private String companyName;

	private String applyStatus;

	private long beReplacedId;

	private long replacedId;

	/**
	 * 客户经理
	 */
	private long busiManagerId;

	private String busiManagerName;

	private Date submitTimeStart;

	private Date submitTimeEnd;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerAddr() {
		return customerAddr;
	}

	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
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

	public String getQualityLand() {
		return qualityLand;
	}

	public void setQualityLand(String qualityLand) {
		this.qualityLand = qualityLand;
	}

	public String getQualityHouse() {
		return qualityHouse;
	}

	public void setQualityHouse(String qualityHouse) {
		this.qualityHouse = qualityHouse;
	}

	public String getQualityAssets() {
		return qualityAssets;
	}

	public void setQualityAssets(String qualityAssets) {
		this.qualityAssets = qualityAssets;
	}

	public long getAppointPerson() {
		return appointPerson;
	}

	public void setAppointPerson(long appointPerson) {
		this.appointPerson = appointPerson;
	}

	public String getAppointPersonAccount() {
		return appointPersonAccount;
	}

	public void setAppointPersonAccount(String appointPersonAccount) {
		this.appointPersonAccount = appointPersonAccount;
	}

	public String getAppointPersonName() {
		return appointPersonName;
	}

	public void setAppointPersonName(String appointPersonName) {
		this.appointPersonName = appointPersonName;
	}

	public String getAppointWay() {
		return appointWay;
	}

	public void setAppointWay(String appointWay) {
		this.appointWay = appointWay;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getBusiManagerId() {
		return busiManagerId;
	}

	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}

	public String getBusiManagerName() {
		return busiManagerName;
	}

	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}

	public Date getSubmitTimeStart() {
		return submitTimeStart;
	}

	public void setSubmitTimeStart(Date submitTimeStart) {
		this.submitTimeStart = submitTimeStart;
	}

	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}

	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public long getBeReplacedId() {
		return beReplacedId;
	}

	public void setBeReplacedId(long beReplacedId) {
		this.beReplacedId = beReplacedId;
	}

	public long getReplacedId() {
		return replacedId;
	}

	public void setReplacedId(long replacedId) {
		this.replacedId = replacedId;
	}

}
