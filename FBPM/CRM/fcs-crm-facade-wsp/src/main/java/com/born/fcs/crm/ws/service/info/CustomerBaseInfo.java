package com.born.fcs.crm.ws.service.info;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 客户基本信息
 * **/
public class CustomerBaseInfo implements Serializable {

	private static final long serialVersionUID = 613590568963127960L;

	/** 评级暂存客户信息 */
	private CustomerInfoForEvalue evalueInfo;
	/** 移交分配关系Id */
	private long relationId;
	/** 用户Id */
	private long userId;
	/** 客户Id */
	private String customerId;
	/** 姓名/企业名称 */
	private String customerName;
	/** 性别：M:男；W女；X：未知 */
	private String sex;
	/** 客户来源 */
	private String resoursFrom;
	/** 证件类型 */
	private String certType;
	/** 营业执照号 */
	private String busiLicenseNo;
	/** 证件号码 */
	private String certNo;
	/** 是否分配 */
	private String isDistribution = BooleanEnum.NO.code();
	/** 客户经理 */
	private String customerManager;
	/** 客户经理ID */
	private long customerManagerId;
	/** 客户经理ID列表 */
	private List<Long> customerManagerIds;
	/** 业务总监 */
	private String director;
	/** 部门Id */
	private long depId;
	/** 部门名 */
	private String depName;
	/** 全部门 */
	private String depPath;
	/** 业务总监ID */
	private long directorId;
	/** 录入人 */
	private String inputPerson;
	/** 录入人ID */
	private long inputPersonId;
	/** 客户评级 */
	private String customerLevel;
	/** 行业编码 */
	private String industryCode;
	/** 所属区域 - 国家编码 */
	private String countryCode;
	/** 所属区域 - 国家名 */
	private String countryName;
	/** 所属区域 - 省编码 */
	private String provinceCode;
	/** 所属区域 - 省编名 */
	private String provinceName;
	/** 所属区域 - 市编码 */
	private String cityCode;
	/** 所属区域 - 市编名 */
	private String cityName;
	/** 所属区域 - 区域编码 */
	private String countyCode;
	/** 所属区域 - 区域名 */
	private String countyName;
	/** 渠道id */
	private long channalId;
	/** 渠道名 */
	private String channalName;
	/** 渠道类型 */
	private String channalType;
	/** 企业类型/性质 */
	private String enterpriseType;
	/** 用户状态 */
	private String status;
	/** 是否暂存数据 */
	private String isTemporary;
	/** 用户类型 */
	private String customerType;
	/** 立项状态 */
	private String projectStatus;
	/** 是否处在签报中 */
	private String changeStatus;
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 创建时间 */
	private Date rawAddTime;
	/** 评价状态 */
	private String evalueStatus;
	/** 联系电话 个人/企业 */
	private String contactMobile;

	public String getContactMobile() {
		return this.contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getRawAddTime() {
		return this.rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public String getChangeStatus() {
		return this.changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getEvalueStatus() {
		return this.evalueStatus;
	}

	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}

	public void setEvalueStatus(String evalueStatus) {
		this.evalueStatus = evalueStatus;
	}

	public CustomerInfoForEvalue getEvalueInfo() {
		return this.evalueInfo;
	}

	public void setEvalueInfo(CustomerInfoForEvalue evalueInfo) {
		this.evalueInfo = evalueInfo;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getResoursFrom() {
		return this.resoursFrom;
	}

	public void setResoursFrom(String resoursFrom) {
		this.resoursFrom = resoursFrom;
	}

	public String getCertType() {
		return this.certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getBusiLicenseNo() {
		return this.busiLicenseNo;
	}

	public void setBusiLicenseNo(String busiLicenseNo) {
		this.busiLicenseNo = busiLicenseNo;
	}

	public String getCertNo() {
		return this.certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCustomerManager() {
		return this.customerManager;
	}

	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}

	public long getCustomerManagerId() {
		return this.customerManagerId;
	}

	public void setCustomerManagerId(long customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public List<Long> getCustomerManagerIds() {
		return customerManagerIds;
	}

	public void setCustomerManagerIds(List<Long> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public long getDepId() {
		return this.depId;
	}

	public void setDepId(long depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public long getDirectorId() {
		return this.directorId;
	}

	public void setDirectorId(long directorId) {
		this.directorId = directorId;
	}

	public String getInputPerson() {
		return this.inputPerson;
	}

	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}

	public long getInputPersonId() {
		return this.inputPersonId;
	}

	public void setInputPersonId(long inputPersonId) {
		this.inputPersonId = inputPersonId;
	}

	public String getCustomerLevel() {
		return this.customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getIndustryCode() {
		return this.industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
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

	public long getChannalId() {
		return this.channalId;
	}

	public void setChannalId(long channalId) {
		this.channalId = channalId;
	}

	public String getChannalName() {
		return this.channalName;
	}

	public void setChannalName(String channalName) {
		this.channalName = channalName;
	}

	public String getChannalType() {
		return this.channalType;
	}

	public void setChannalType(String channalType) {
		this.channalType = channalType;
	}

	public String getEnterpriseType() {
		return this.enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsTemporary() {
		return this.isTemporary;
	}

	public void setIsTemporary(String isTemporary) {
		this.isTemporary = isTemporary;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getProjectStatus() {
		return this.projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	public String getIsDistribution() {
		return this.isDistribution;
	}

	public String getDepPath() {
		return this.depPath;
	}

	public void setDepPath(String depPath) {
		this.depPath = depPath;
	}

	public long getRelationId() {
		return this.relationId;
	}

	public void setRelationId(long relationId) {
		this.relationId = relationId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerBaseInfo [evalueInfo=");
		builder.append(evalueInfo);
		builder.append(", relationId=");
		builder.append(relationId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", resoursFrom=");
		builder.append(resoursFrom);
		builder.append(", certType=");
		builder.append(certType);
		builder.append(", busiLicenseNo=");
		builder.append(busiLicenseNo);
		builder.append(", certNo=");
		builder.append(certNo);
		builder.append(", isDistribution=");
		builder.append(isDistribution);
		builder.append(", customerManager=");
		builder.append(customerManager);
		builder.append(", customerManagerId=");
		builder.append(customerManagerId);
		builder.append(", director=");
		builder.append(director);
		builder.append(", depId=");
		builder.append(depId);
		builder.append(", depName=");
		builder.append(depName);
		builder.append(", depPath=");
		builder.append(depPath);
		builder.append(", directorId=");
		builder.append(directorId);
		builder.append(", inputPerson=");
		builder.append(inputPerson);
		builder.append(", inputPersonId=");
		builder.append(inputPersonId);
		builder.append(", customerLevel=");
		builder.append(customerLevel);
		builder.append(", industryCode=");
		builder.append(industryCode);
		builder.append(", countryCode=");
		builder.append(countryCode);
		builder.append(", countryName=");
		builder.append(countryName);
		builder.append(", provinceCode=");
		builder.append(provinceCode);
		builder.append(", provinceName=");
		builder.append(provinceName);
		builder.append(", cityCode=");
		builder.append(cityCode);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", countyCode=");
		builder.append(countyCode);
		builder.append(", countyName=");
		builder.append(countyName);
		builder.append(", channalId=");
		builder.append(channalId);
		builder.append(", channalName=");
		builder.append(channalName);
		builder.append(", channalType=");
		builder.append(channalType);
		builder.append(", enterpriseType=");
		builder.append(enterpriseType);
		builder.append(", status=");
		builder.append(status);
		builder.append(", isTemporary=");
		builder.append(isTemporary);
		builder.append(", customerType=");
		builder.append(customerType);
		builder.append(", projectStatus=");
		builder.append(projectStatus);
		builder.append(", changeStatus=");
		builder.append(changeStatus);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", evalueStatus=");
		builder.append(evalueStatus);
		builder.append(", contactMobile=");
		builder.append(contactMobile);
		builder.append("]");
		return builder.toString();
	}
}
