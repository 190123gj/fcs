package com.born.fcs.crm.ws.service.order;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * @des： 个人客户详情 add Order
 * */
public class PersonalCustomerDetailOrder extends CustomerBaseOrder {
	
	private static final long serialVersionUID = 7070095147986999653L;
	/** 旗下公司信息 */
	List<PersonalCompanyOrder> reqList;
	/** 姓名拼音缩写 */
	private String customerNamePx;
	/** 公民类型:境内,境外 */
	private String citizenType;
	/** 民族 */
	private String nation;
	/** 出生日期 */
	private String birthDay;
	/** 婚姻状况 */
	private String maritalStatus;
	/** 联系电话 */
	private String mobile;
	/** 电话绑定 */
	private String mobileBond;
	/** 邮箱 */
	private String email;
	/** 邮箱绑定 */
	private String emailBond;
	/** 传真 */
	private String fix;
	/** 固定住址 */
	private String address;
	/** 籍贯 */
	private String originPlace;
	/** 户口所在地 */
	private String registeredAddress;
	/** 邮政编码 */
	private String postcode;
	/** 现工作单位 */
	private String company;
	/** 职务 */
	private String job;
	/** 技术职称 */
	private String technical;
	/** 客户类型 */
	private String customerJobType;
	/** 客户与我公司关系 */
	private String relation;
	/** 配偶姓名 */
	private String spoRealName;
	/** 配偶性别 */
	private String spoSex;
	/** 配偶公民类型 */
	private String spoCitizenType;
	/** 配偶民族 */
	private String spoNation;
	/** 配偶证件类型 */
	private String spoCertType;
	/** 配偶证件号码 */
	private String spoCertNo;
	/** 配偶婚姻状况 */
	private String spoMaritalStatus;
	/** 配偶学历 */
	private String spoEducation;
	/** 配偶出生日期 */
	private String spoBirthDay;
	/** 配偶联系电话 */
	private String spoMobile;
	/** 配偶电子邮箱 */
	private String spoEmail;
	/** 配偶传真 */
	private String spoFix;
	/** 配偶固定住址 */
	private String spoAddress;
	/** 配偶籍贯 */
	private String spoOriginPlace;
	/** 配偶户口所在地 */
	private String spoRegisteredAddress;
	/** 配偶邮政编码 */
	private String spoPostcode;
	/** 配偶现工作单位 */
	private String spoCompany;
	/** 配偶职务 */
	private String spoJob;
	/** 配偶技术职称 */
	private String spoTechnical;
	/** 配偶客户类型:个人，个体工商 */
	private String spoCustomerType;
	/** 总资产 */
	private Money totalAsset = Money.zero();
	/** 家庭财产 */
	private Money familyAsset = Money.zero();
	/** 总负债 */
	private Money totalLoan = Money.zero();
	/** 年总支出 */
	private Money totalOutcome = Money.zero();
	/** 开户行 */
	private String bankAccount;
	/** 账号 */
	private String accountNo;
	/** 开户人 */
	private String accountHolder;
	/** 本人工资代发账户:开户行 */
	private String bankAccountWages;
	/** 本人工资代发账户:账号 */
	private String accountNoWages;
	/** 本人工资代发账户:开户人 */
	private String accountHolderWages;
	/** 配偶工资代发账户:开户行 */
	private String spoBankAccountWages;
	/** 配偶工资代发账户:账号 */
	private String spoAccountNoWages;
	/** 配偶工资代发账户:开户人 */
	private String spoAccountHolderWages;
	/** 房产 */
	private String house;
	/** 车辆 */
	private String car;
	/** 备注 */
	private String memo;
	/** 备用字段1 */
	private String info1;
	/** 备用字段2 */
	private String info2;
	/** 备用字段3 */
	private String info3;
	/** 证件照 */
	private String certImg;
	/** 证件照正面 */
	private String certImgFont;
	/** 证件照反面 */
	private String certImgBack;
	/** 创建时间 */
	private Date rawAddTime;
	/** 区域编号 */
	private String countyCode;
	/** 区域名 */
	private String countyName;
	
	@Override
	public String getCountyCode() {
		return this.countyCode;
	}
	
	@Override
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	@Override
	public String getCountyName() {
		return this.countyName;
	}
	
	@Override
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	public String getSpoSex() {
		return this.spoSex;
	}
	
	public void setSpoSex(String spoSex) {
		this.spoSex = spoSex;
	}
	
	public String getSpoCitizenType() {
		return this.spoCitizenType;
	}
	
	public void setSpoCitizenType(String spoCitizenType) {
		this.spoCitizenType = spoCitizenType;
	}
	
	public String getSpoNation() {
		return this.spoNation;
	}
	
	public String getCertImg() {
		return this.certImg;
	}
	
	public void setCertImg(String certImg) {
		this.certImg = certImg;
	}
	
	public String getCertImgFont() {
		return this.certImgFont;
	}
	
	public void setCertImgFont(String certImgFont) {
		this.certImgFont = certImgFont;
	}
	
	public String getCertImgBack() {
		return this.certImgBack;
	}
	
	public void setCertImgBack(String certImgBack) {
		this.certImgBack = certImgBack;
	}
	
	public void setSpoNation(String spoNation) {
		this.spoNation = spoNation;
	}
	
	public String getSpoCertType() {
		return this.spoCertType;
	}
	
	public void setSpoCertType(String spoCertType) {
		this.spoCertType = spoCertType;
	}
	
	public String getSpoCertNo() {
		return this.spoCertNo;
	}
	
	public void setSpoCertNo(String spoCertNo) {
		this.spoCertNo = spoCertNo;
	}
	
	public String getSpoMaritalStatus() {
		return this.spoMaritalStatus;
	}
	
	public void setSpoMaritalStatus(String spoMaritalStatus) {
		this.spoMaritalStatus = spoMaritalStatus;
	}
	
	public String getSpoEducation() {
		return this.spoEducation;
	}
	
	public void setSpoEducation(String spoEducation) {
		this.spoEducation = spoEducation;
	}
	
	public String getSpoBirthDay() {
		return this.spoBirthDay;
	}
	
	public void setSpoBirthDay(String spoBirthDay) {
		this.spoBirthDay = spoBirthDay;
	}
	
	public String getSpoMobile() {
		return this.spoMobile;
	}
	
	public void setSpoMobile(String spoMobile) {
		this.spoMobile = spoMobile;
	}
	
	public String getSpoEmail() {
		return this.spoEmail;
	}
	
	public void setSpoEmail(String spoEmail) {
		this.spoEmail = spoEmail;
	}
	
	public String getSpoFix() {
		return this.spoFix;
	}
	
	public void setSpoFix(String spoFix) {
		this.spoFix = spoFix;
	}
	
	public String getSpoAddress() {
		return this.spoAddress;
	}
	
	public void setSpoAddress(String spoAddress) {
		this.spoAddress = spoAddress;
	}
	
	public String getSpoOriginPlace() {
		return this.spoOriginPlace;
	}
	
	public void setSpoOriginPlace(String spoOriginPlace) {
		this.spoOriginPlace = spoOriginPlace;
	}
	
	public String getSpoRegisteredAddress() {
		return this.spoRegisteredAddress;
	}
	
	public void setSpoRegisteredAddress(String spoRegisteredAddress) {
		this.spoRegisteredAddress = spoRegisteredAddress;
	}
	
	public String getSpoPostcode() {
		return this.spoPostcode;
	}
	
	public void setSpoPostcode(String spoPostcode) {
		this.spoPostcode = spoPostcode;
	}
	
	public String getSpoCompany() {
		return this.spoCompany;
	}
	
	public void setSpoCompany(String spoCompany) {
		this.spoCompany = spoCompany;
	}
	
	public String getSpoJob() {
		return this.spoJob;
	}
	
	public void setSpoJob(String spoJob) {
		this.spoJob = spoJob;
	}
	
	public String getSpoTechnical() {
		return this.spoTechnical;
	}
	
	public void setSpoTechnical(String spoTechnical) {
		this.spoTechnical = spoTechnical;
	}
	
	public String getSpoCustomerType() {
		return this.spoCustomerType;
	}
	
	public void setSpoCustomerType(String spoCustomerType) {
		this.spoCustomerType = spoCustomerType;
	}
	
	public Money getTotalAsset() {
		return this.totalAsset;
	}
	
	public void setTotalAsset(Money totalAsset) {
		this.totalAsset = totalAsset;
	}
	
	public Money getFamilyAsset() {
		return this.familyAsset;
	}
	
	public void setFamilyAsset(Money familyAsset) {
		this.familyAsset = familyAsset;
	}
	
	public Money getTotalLoan() {
		return this.totalLoan;
	}
	
	public void setTotalLoan(Money totalLoan) {
		this.totalLoan = totalLoan;
	}
	
	public Money getTotalOutcome() {
		return this.totalOutcome;
	}
	
	public void setTotalOutcome(Money totalOutcome) {
		this.totalOutcome = totalOutcome;
	}
	
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountHolder() {
		return this.accountHolder;
	}
	
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	public String getBankAccountWages() {
		return this.bankAccountWages;
	}
	
	public void setBankAccountWages(String bankAccountWages) {
		this.bankAccountWages = bankAccountWages;
	}
	
	public String getAccountNoWages() {
		return this.accountNoWages;
	}
	
	public void setAccountNoWages(String accountNoWages) {
		this.accountNoWages = accountNoWages;
	}
	
	public String getAccountHolderWages() {
		return this.accountHolderWages;
	}
	
	public void setAccountHolderWages(String accountHolderWages) {
		this.accountHolderWages = accountHolderWages;
	}
	
	public String getSpoBankAccountWages() {
		return this.spoBankAccountWages;
	}
	
	public void setSpoBankAccountWages(String spoBankAccountWages) {
		this.spoBankAccountWages = spoBankAccountWages;
	}
	
	public String getSpoAccountNoWages() {
		return this.spoAccountNoWages;
	}
	
	public void setSpoAccountNoWages(String spoAccountNoWages) {
		this.spoAccountNoWages = spoAccountNoWages;
	}
	
	public String getSpoAccountHolderWages() {
		return this.spoAccountHolderWages;
	}
	
	public void setSpoAccountHolderWages(String spoAccountHolderWages) {
		this.spoAccountHolderWages = spoAccountHolderWages;
	}
	
	public String getHouse() {
		return this.house;
	}
	
	public void setHouse(String house) {
		this.house = house;
	}
	
	public String getCar() {
		return this.car;
	}
	
	public void setCar(String car) {
		this.car = car;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getInfo1() {
		return this.info1;
	}
	
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getInfo2() {
		return this.info2;
	}
	
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	
	public String getInfo3() {
		return this.info3;
	}
	
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getCustomerNamePx() {
		return this.customerNamePx;
	}
	
	public void setCustomerNamePx(String customerNamePx) {
		this.customerNamePx = customerNamePx;
	}
	
	public String getCitizenType() {
		return this.citizenType;
	}
	
	public void setCitizenType(String citizenType) {
		this.citizenType = citizenType;
	}
	
	public String getNation() {
		return this.nation;
	}
	
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public String getBirthDay() {
		return this.birthDay;
	}
	
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	public String getMaritalStatus() {
		return this.maritalStatus;
	}
	
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getMobileBond() {
		return this.mobileBond;
	}
	
	public void setMobileBond(String mobileBond) {
		this.mobileBond = mobileBond;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmailBond() {
		return this.emailBond;
	}
	
	public void setEmailBond(String emailBond) {
		this.emailBond = emailBond;
	}
	
	public String getFix() {
		return this.fix;
	}
	
	public void setFix(String fix) {
		this.fix = fix;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getOriginPlace() {
		return this.originPlace;
	}
	
	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}
	
	public String getRegisteredAddress() {
		return this.registeredAddress;
	}
	
	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}
	
	public String getPostcode() {
		return this.postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getJob() {
		return this.job;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public String getTechnical() {
		return this.technical;
	}
	
	public void setTechnical(String technical) {
		this.technical = technical;
	}
	
	public String getCustomerJobType() {
		return this.customerJobType;
	}
	
	public void setCustomerJobType(String customerJobType) {
		this.customerJobType = customerJobType;
	}
	
	public String getRelation() {
		return this.relation;
	}
	
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public String getSpoRealName() {
		return this.spoRealName;
	}
	
	public void setSpoRealName(String spoRealName) {
		this.spoRealName = spoRealName;
	}
	
	public List<PersonalCompanyOrder> getReqList() {
		return this.reqList;
	}
	
	public void setReqList(List<PersonalCompanyOrder> reqList) {
		this.reqList = reqList;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonalCustomerDetailOrder [reqList=");
		builder.append(reqList);
		builder.append(", customerNamePx=");
		builder.append(customerNamePx);
		builder.append(", citizenType=");
		builder.append(citizenType);
		builder.append(", nation=");
		builder.append(nation);
		builder.append(", birthDay=");
		builder.append(birthDay);
		builder.append(", maritalStatus=");
		builder.append(maritalStatus);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", mobileBond=");
		builder.append(mobileBond);
		builder.append(", email=");
		builder.append(email);
		builder.append(", emailBond=");
		builder.append(emailBond);
		builder.append(", fix=");
		builder.append(fix);
		builder.append(", address=");
		builder.append(address);
		builder.append(", originPlace=");
		builder.append(originPlace);
		builder.append(", registeredAddress=");
		builder.append(registeredAddress);
		builder.append(", postcode=");
		builder.append(postcode);
		builder.append(", company=");
		builder.append(company);
		builder.append(", job=");
		builder.append(job);
		builder.append(", technical=");
		builder.append(technical);
		builder.append(", customerJobType=");
		builder.append(customerJobType);
		builder.append(", relation=");
		builder.append(relation);
		builder.append(", spoRealName=");
		builder.append(spoRealName);
		builder.append(", spoSex=");
		builder.append(spoSex);
		builder.append(", spoCitizenType=");
		builder.append(spoCitizenType);
		builder.append(", spoNation=");
		builder.append(spoNation);
		builder.append(", spoCertType=");
		builder.append(spoCertType);
		builder.append(", spoCertNo=");
		builder.append(spoCertNo);
		builder.append(", spoMaritalStatus=");
		builder.append(spoMaritalStatus);
		builder.append(", spoEducation=");
		builder.append(spoEducation);
		builder.append(", spoBirthDay=");
		builder.append(spoBirthDay);
		builder.append(", spoMobile=");
		builder.append(spoMobile);
		builder.append(", spoEmail=");
		builder.append(spoEmail);
		builder.append(", spoFix=");
		builder.append(spoFix);
		builder.append(", spoAddress=");
		builder.append(spoAddress);
		builder.append(", spoOriginPlace=");
		builder.append(spoOriginPlace);
		builder.append(", spoRegisteredAddress=");
		builder.append(spoRegisteredAddress);
		builder.append(", spoPostcode=");
		builder.append(spoPostcode);
		builder.append(", spoCompany=");
		builder.append(spoCompany);
		builder.append(", spoJob=");
		builder.append(spoJob);
		builder.append(", spoTechnical=");
		builder.append(spoTechnical);
		builder.append(", spoCustomerType=");
		builder.append(spoCustomerType);
		builder.append(", totalAsset=");
		builder.append(totalAsset);
		builder.append(", familyAsset=");
		builder.append(familyAsset);
		builder.append(", totalLoan=");
		builder.append(totalLoan);
		builder.append(", totalOutcome=");
		builder.append(totalOutcome);
		builder.append(", bankAccount=");
		builder.append(bankAccount);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", accountHolder=");
		builder.append(accountHolder);
		builder.append(", bankAccountWages=");
		builder.append(bankAccountWages);
		builder.append(", accountNoWages=");
		builder.append(accountNoWages);
		builder.append(", accountHolderWages=");
		builder.append(accountHolderWages);
		builder.append(", spoBankAccountWages=");
		builder.append(spoBankAccountWages);
		builder.append(", spoAccountNoWages=");
		builder.append(spoAccountNoWages);
		builder.append(", spoAccountHolderWages=");
		builder.append(spoAccountHolderWages);
		builder.append(", house=");
		builder.append(house);
		builder.append(", car=");
		builder.append(car);
		builder.append(", memo=");
		builder.append(memo);
		builder.append(", info1=");
		builder.append(info1);
		builder.append(", info2=");
		builder.append(info2);
		builder.append(", info3=");
		builder.append(info3);
		builder.append(", certImg=");
		builder.append(certImg);
		builder.append(", certImgFont=");
		builder.append(certImgFont);
		builder.append(", certImgBack=");
		builder.append(certImgBack);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", countyCode=");
		builder.append(countyCode);
		builder.append(", countyName=");
		builder.append(countyName);
		builder.append("]");
		return builder.toString();
	}
	
}
