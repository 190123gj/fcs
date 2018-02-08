package com.born.fcs.am.ws.info.assesscompany;

import java.util.Date;
import java.util.List;

import com.born.fcs.am.ws.enums.AssessCompanyStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class AssetsAssessCompanyInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6890033722519197381L;
	
	private long id;
	
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
	
	private AssessCompanyStatusEnum status;
	
	private String attach;
	
	private String remark;
	
	private Date rawAddTime;
	
	private double workSituation;
	
	private double attachment;
	
	private double technicalLevel;
	
	private double evaluationEfficiency;
	
	private double cooperationSituation;
	
	private double serviceAttitude;
	
	private List<AssessCompanyContactInfo> contactInfos;
	
	private List<AssessCompanyBusinessScopeInfo> scopeInfos;
	
	private String contactName; // 联系人
	
	private String contactNumber;// 联系人电话
	//本次评价信息
	private int workSituationEvaluate;
	
	private int attachmentEvaluate;
	
	private int technicalLevelEvaluate;
	
	private int evaluationEfficiencyEvaluate;
	
	private int cooperationSituationEvaluate;
	
	private int serviceAttitudeEvaluate;
	
	private String isReviewEvaluate;
	
	private String remarkEvaluate;
	
	// ========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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
		if (registeredCapital == null) {
			this.registeredCapital = new Money(0, 0);
		} else {
			this.registeredCapital = registeredCapital;
		}
	}
	
	public AssessCompanyStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(AssessCompanyStatusEnum status) {
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
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public List<AssessCompanyContactInfo> getContactInfos() {
		return contactInfos;
	}
	
	public void setContactInfos(List<AssessCompanyContactInfo> contactInfos) {
		this.contactInfos = contactInfos;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public List<AssessCompanyBusinessScopeInfo> getScopeInfos() {
		return scopeInfos;
	}
	
	public void setScopeInfos(List<AssessCompanyBusinessScopeInfo> scopeInfos) {
		this.scopeInfos = scopeInfos;
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
	
	public int getWorkSituationEvaluate() {
		return workSituationEvaluate;
	}
	
	public void setWorkSituationEvaluate(int workSituationEvaluate) {
		this.workSituationEvaluate = workSituationEvaluate;
	}
	
	public int getAttachmentEvaluate() {
		return attachmentEvaluate;
	}
	
	public void setAttachmentEvaluate(int attachmentEvaluate) {
		this.attachmentEvaluate = attachmentEvaluate;
	}
	
	public int getTechnicalLevelEvaluate() {
		return technicalLevelEvaluate;
	}
	
	public void setTechnicalLevelEvaluate(int technicalLevelEvaluate) {
		this.technicalLevelEvaluate = technicalLevelEvaluate;
	}
	
	public int getEvaluationEfficiencyEvaluate() {
		return evaluationEfficiencyEvaluate;
	}
	
	public void setEvaluationEfficiencyEvaluate(int evaluationEfficiencyEvaluate) {
		this.evaluationEfficiencyEvaluate = evaluationEfficiencyEvaluate;
	}
	
	public int getCooperationSituationEvaluate() {
		return cooperationSituationEvaluate;
	}
	
	public void setCooperationSituationEvaluate(int cooperationSituationEvaluate) {
		this.cooperationSituationEvaluate = cooperationSituationEvaluate;
	}
	
	public int getServiceAttitudeEvaluate() {
		return serviceAttitudeEvaluate;
	}
	
	public void setServiceAttitudeEvaluate(int serviceAttitudeEvaluate) {
		this.serviceAttitudeEvaluate = serviceAttitudeEvaluate;
	}
	
	public String getIsReviewEvaluate() {
		return isReviewEvaluate;
	}
	
	public void setIsReviewEvaluate(String isReviewEvaluate) {
		this.isReviewEvaluate = isReviewEvaluate;
	}
	
	public String getRemarkEvaluate() {
		return remarkEvaluate;
	}
	
	public void setRemarkEvaluate(String remarkEvaluate) {
		this.remarkEvaluate = remarkEvaluate;
	}
	
}
