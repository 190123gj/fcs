package com.born.fcs.crm.ws.service.order.query;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 客户列表查询 Order(个人/公司)
 * */
public class CustomerQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 7988916625811678135L;
	/** 客户id,跨系统查询通用 */
	private long userId;
	/** 客户Id： 通用查询Id */
	private String customerId;
	/** 客户姓名 */
	private String customerName;
	/** 性别 */
	private String sex;
	/** 客户来源 */
	private String resoursFrom;
	/** 证件类型 */
	private String certType;
	/** 营业执照号 */
	private String busiLicenseNo;
	/** 身份证号码 */
	private String certNo;
	/** 营业执照号模糊查询 */
	private String likeBusiLicenseNo;
	/** 身份证号码模糊查询 */
	private String likeCertNo;
	/** 是否分配 */
	private String isDistribution;
	/** 客户评级 */
	private String customerLevel;
	/** 用户状态 */
	private String status;
	/** 用户类型 CustomerTypeEnum */
	private String customerType;
	/** 按时间段查询 */
	private Date beginDate;
	/** 按时间段查询 */
	private Date endDate;
	/** 按城市查询 */
	private List<String> cityCodeList;
	/** 按行业查询 */
	private List<String> industryCodeList;
	/** 按客户等级查询 */
	private List<String> customerLevelList;
	/** 按名字迷糊查询 */
	private String likeCustomerName;
	/** 客户经理 */
	private String customerManager;
	/** 客户经理ID */
	private long customerManagerId;
	/** 业务总监 */
	private String director;
	/** 部门Id */
	private long depId;
	/** 部门名 */
	private String depName;
	/** 业务总监ID */
	private long directorId;
	/** 录入人 */
	private String inputPerson;
	/** 录入人ID */
	private long inputPersonId;
	/** 渠道 */
	private long channalId;
	/** 渠道名 */
	private String channalName;
	/** 渠道类型 */
	private String channalType;
	
	/** 立项状态 */
	private String projectStatus;
	/** 评价状态 IS：审核中，NO：不在审核中 */
	private String evalueStatus;
	/** 按 客户名 个人身份证号 企业营业执照号 模糊查询 */
	private String likeNameOrId;
	
	/** 是否查询通讯录 ： IS 有电话号码的客户，NO 无电话号码的客户 */
	private String mobileQuery;
	
	/** 是否包含公海客户 配合customerManagerId使用 IS/NO */
	private String includePublic;
	
	public String getMobileQuery() {
		return this.mobileQuery;
	}
	
	public String getLikeBusiLicenseNo() {
		return this.likeBusiLicenseNo;
	}
	
	public void setLikeBusiLicenseNo(String likeBusiLicenseNo) {
		this.likeBusiLicenseNo = likeBusiLicenseNo;
	}
	
	public String getLikeCertNo() {
		return this.likeCertNo;
	}
	
	public void setLikeCertNo(String likeCertNo) {
		this.likeCertNo = likeCertNo;
	}
	
	public void setMobileQuery(String mobileQuery) {
		this.mobileQuery = mobileQuery;
	}
	
	public String getLikeNameOrId() {
		return this.likeNameOrId;
	}
	
	public void setLikeNameOrId(String likeNameOrId) {
		this.likeNameOrId = likeNameOrId;
	}
	
	public String getEvalueStatus() {
		return this.evalueStatus;
	}
	
	public void setEvalueStatus(String evalueStatus) {
		this.evalueStatus = evalueStatus;
	}
	
	public String getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
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
	
	public long getCustomerManagerId() {
		return this.customerManagerId;
	}
	
	public void setCustomerManagerId(long customerManagerId) {
		this.customerManagerId = customerManagerId;
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
	
	public long getInputPersonId() {
		return this.inputPersonId;
	}
	
	public void setInputPersonId(long inputPersonId) {
		this.inputPersonId = inputPersonId;
	}
	
	public String getLikeCustomerName() {
		return this.likeCustomerName;
	}
	
	public void setLikeCustomerName(String likeCustomerName) {
		this.likeCustomerName = likeCustomerName;
	}
	
	public Date getBeginDate() {
		return this.beginDate;
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public List<String> getCityCodeList() {
		return this.cityCodeList;
	}
	
	public void setCityCodeList(List<String> cityCodeList) {
		this.cityCodeList = cityCodeList;
	}
	
	public List<String> getIndustryCodeList() {
		return this.industryCodeList;
	}
	
	public void setIndustryCodeList(List<String> industryCodeList) {
		this.industryCodeList = industryCodeList;
	}
	
	public List<String> getCustomerLevelList() {
		return this.customerLevelList;
	}
	
	public void setCustomerLevelList(List<String> customerLevelList) {
		this.customerLevelList = customerLevelList;
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
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getResoursFrom() {
		return this.resoursFrom;
	}
	
	public void setResoursFrom(String resoursFrom) {
		this.resoursFrom = resoursFrom;
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
	
	public String getIsDistribution() {
		return this.isDistribution;
	}
	
	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}
	
	public String getCustomerManager() {
		return this.customerManager;
	}
	
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	public String getCustomerLevel() {
		return this.customerLevel;
	}
	
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCertType() {
		return this.certType;
	}
	
	public void setCertType(String certType) {
		this.certType = certType;
	}
	
	public String getIncludePublic() {
		return includePublic;
	}
	
	public void setIncludePublic(String includePublic) {
		this.includePublic = includePublic;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerQueryOrder [userId=");
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
		builder.append(", likeBusiLicenseNo=");
		builder.append(likeBusiLicenseNo);
		builder.append(", likeCertNo=");
		builder.append(likeCertNo);
		builder.append(", isDistribution=");
		builder.append(isDistribution);
		builder.append(", customerLevel=");
		builder.append(customerLevel);
		builder.append(", status=");
		builder.append(status);
		builder.append(", customerType=");
		builder.append(customerType);
		builder.append(", beginDate=");
		builder.append(beginDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", cityCodeList=");
		builder.append(cityCodeList);
		builder.append(", industryCodeList=");
		builder.append(industryCodeList);
		builder.append(", customerLevelList=");
		builder.append(customerLevelList);
		builder.append(", likeCustomerName=");
		builder.append(likeCustomerName);
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
		builder.append(", directorId=");
		builder.append(directorId);
		builder.append(", inputPerson=");
		builder.append(inputPerson);
		builder.append(", inputPersonId=");
		builder.append(inputPersonId);
		builder.append(", channalId=");
		builder.append(channalId);
		builder.append(", channalName=");
		builder.append(channalName);
		builder.append(", channalType=");
		builder.append(channalType);
		builder.append(", projectStatus=");
		builder.append(projectStatus);
		builder.append(", evalueStatus=");
		builder.append(evalueStatus);
		builder.append(", likeNameOrId=");
		builder.append(likeNameOrId);
		builder.append(", mobileQuery=");
		builder.append(mobileQuery);
		builder.append(", includePublic=");
		builder.append(includePublic);
		builder.append("]");
		return builder.toString();
	}
	
}
