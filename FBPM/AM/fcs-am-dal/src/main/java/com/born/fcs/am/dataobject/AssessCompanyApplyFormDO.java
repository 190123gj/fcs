/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.dataobject;

// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssessCompanyApplyFormDO extends SimpleFormDO {
	private static final long serialVersionUID = -4282603875229233564L;
	
	// ========== properties ==========
	
	private long id;
	
	private long formId;
	
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
	
	private String appointRemark;
	
	private String companyId;
	
	private String companyName;
	
	private String applyStatus;
	
	private long beReplacedId;
	
	private long replacedId;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	// 查询条件部分
	
	List<Long> deptIdList;
	
	List<String> statusList;
	
	long loginUserId;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	// ========== getters and setters ==========
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
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
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getStatusList() {
		return statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public long getLoginUserId() {
		return loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
}
