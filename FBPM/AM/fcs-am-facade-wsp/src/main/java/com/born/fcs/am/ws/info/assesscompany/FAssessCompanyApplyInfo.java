package com.born.fcs.am.ws.info.assesscompany;

import java.util.Date;

import com.born.fcs.am.ws.enums.AppointWayEnum;
import com.born.fcs.am.ws.enums.AssessCompanyApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

public class FAssessCompanyApplyInfo extends FormVOInfo {
	
	/**
	 * 评估公司申请单
	 */
	private static final long serialVersionUID = -6991489383740384259L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private CustomerTypeEnum customerType;
	
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
	
	private AppointWayEnum appointWay;
	
	private String appointRemark;
	
	private String companyId;
	
	private String companyName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private long riskManagerId;
	
	private String riskManagerName;
	
	private long busiManagerId;
	
	private String busiManagerName;
	
	private AssessCompanyApplyStatusEnum applyStatus;
	
	private long beReplacedId;
	
	private long replacedId;
	
	private String isEvaluate;// 用来判断当前登录人是否已经评价过
	
	private String isExistEvaluate;// 用来当前申请单是否  已有评价过
	
	private String isBeforeMeet;// 用来判断是否上会前
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
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
	
	public AppointWayEnum getAppointWay() {
		return appointWay;
	}
	
	public void setAppointWay(AppointWayEnum appointWay) {
		this.appointWay = appointWay;
	}
	
	public String getAppointRemark() {
		return appointRemark;
	}
	
	public void setAppointRemark(String appointRemark) {
		this.appointRemark = appointRemark;
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
	
	public long getRiskManagerId() {
		return riskManagerId;
	}
	
	public void setRiskManagerId(long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}
	
	public String getRiskManagerName() {
		return riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getIsEvaluate() {
		return isEvaluate;
	}
	
	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}
	
	public AssessCompanyApplyStatusEnum getApplyStatus() {
		return applyStatus;
	}
	
	public void setApplyStatus(AssessCompanyApplyStatusEnum applyStatus) {
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
	
	public String getIsBeforeMeet() {
		return isBeforeMeet;
	}
	
	public void setIsBeforeMeet(String isBeforeMeet) {
		this.isBeforeMeet = isBeforeMeet;
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
	
	public String getIsExistEvaluate() {
		return isExistEvaluate;
	}
	
	public void setIsExistEvaluate(String isExistEvaluate) {
		this.isExistEvaluate = isExistEvaluate;
	}
	
}
