/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.assesscompany;

import java.util.List;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssetsAssessCompanyQueryOrder extends QueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9197977649827339459L;
	
	private Long id;
	
	private String companyName;
	
	private String qualityLand;
	
	private String qualityHouse;
	
	private String qualityAssets;
	
	private List<String> qualityLandList;
	
	private List<String> qualityHouseList;
	
	private List<String> qualityAssetsList;
	
	private String countryCode;
	
	private String countryName;
	
	private String cityCode;
	
	private String city;
	
	private String provinceCode;
	
	private String provinceName;
	
	private String status;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
	public String getCityCode() {
		return cityCode;
	}
	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public List<String> getQualityLandList() {
		return qualityLandList;
	}
	
	public void setQualityLandList(List<String> qualityLandList) {
		this.qualityLandList = qualityLandList;
	}
	
	public List<String> getQualityHouseList() {
		return qualityHouseList;
	}
	
	public void setQualityHouseList(List<String> qualityHouseList) {
		this.qualityHouseList = qualityHouseList;
	}
	
	public List<String> getQualityAssetsList() {
		return qualityAssetsList;
	}
	
	public void setQualityAssetsList(List<String> qualityAssetsList) {
		this.qualityAssetsList = qualityAssetsList;
	}
	
}
