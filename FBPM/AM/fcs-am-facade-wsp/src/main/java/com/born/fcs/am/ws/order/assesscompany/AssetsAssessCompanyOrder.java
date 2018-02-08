package com.born.fcs.am.ws.order.assesscompany;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssetsAssessCompanyOrder extends ProcessOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5261768715640324198L;

	private Long id;

	private String companyName;

	private String qualityLand;

	private String qualityHouse;

	private String qualityAssets;

	private String cityCode;

	private String city;

	private String countryCode;

	private String countryName;

	private String provinceCode;

	private String provinceName;

	private String countyName;

	private String countyCode;

	private String contactAddr;

	private Money registeredCapital = new Money(0, 0);

	private String status;

	private String attach;

	private String remark;

	private double workSituation = 5.0;

	private double attachment = 5.0;

	private double technicalLevel = 5.0;

	private double evaluationEfficiency = 5.0;

	private double cooperationSituation = 5.0;

	private double serviceAttitude = 5.0;

	private List<AssessCompanyContactOrder> contactOrders;

	private List<AssessCompanyBusinessScopeOrder> scopeOrders;

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

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public Money getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Money registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<AssessCompanyContactOrder> getContactOrders() {
		return contactOrders;
	}

	public void setContactOrders(List<AssessCompanyContactOrder> contactOrders) {
		this.contactOrders = contactOrders;
	}

	public List<AssessCompanyBusinessScopeOrder> getScopeOrders() {
		return scopeOrders;
	}

	public void setScopeOrders(List<AssessCompanyBusinessScopeOrder> scopeOrders) {
		this.scopeOrders = scopeOrders;
	}

	public double getWorkSituation() {
		return workSituation;
	}

	public void setWorkSituation(double workSituation) {
		this.workSituation = workSituation;
	}

	public double getAttachment() {
		return attachment;
	}

	public void setAttachment(double attachment) {
		this.attachment = attachment;
	}

	public double getTechnicalLevel() {
		return technicalLevel;
	}

	public void setTechnicalLevel(double technicalLevel) {
		this.technicalLevel = technicalLevel;
	}

	public double getEvaluationEfficiency() {
		return evaluationEfficiency;
	}

	public void setEvaluationEfficiency(double evaluationEfficiency) {
		this.evaluationEfficiency = evaluationEfficiency;
	}

	public double getCooperationSituation() {
		return cooperationSituation;
	}

	public void setCooperationSituation(double cooperationSituation) {
		this.cooperationSituation = cooperationSituation;
	}

	public double getServiceAttitude() {
		return serviceAttitude;
	}

	public void setServiceAttitude(double serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}

}
