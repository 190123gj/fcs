package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目调查 - 诉讼担保项目调查报告
 * 
 * @author lirz
 * 
 * 2016-3-23 下午4:13:14
 */
public class FInvestigationLitigationInfo extends FInvestigationInfo {
	
	private static final long serialVersionUID = 2664134586943457035L;
	
	private long litigationId; //主键ID
	private double guaranteeFee;
	private ChargeTypeEnum guaranteeType;
	private long coInstitutionId; //合作机构ID
	private String coInstitutionName; //合作机构名称
	private double informationFee; //法律咨询费率
	private ChargeTypeEnum informationFeeType; //法律咨询费类型
	private String court; //法院
	private double deposit; //保证金
	private ChargeTypeEnum depositType; //保证金类型
	private String contactPerson; //项目联系人
	private String contactNo; //联系电话
	private String investigatePersion; //调查人员
	private Date acceptDate; //受理时间
	private Money guaranteeAmount = new Money(0, 0); //本次申请保全金额
	private String depositAccount; //保证金存入账户名
	private String caseIntroduce; //案情介绍
	private String content; //拟保全标的或内容
	private String auditOpinion; //风险审核意见
	private String synthesizeOpinion; //项目综合意见
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getLitigationId() {
		return litigationId;
	}
	
	public void setLitigationId(long litigationId) {
		this.litigationId = litigationId;
	}
	
	public double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public ChargeTypeEnum getGuaranteeType() {
		return this.guaranteeType;
	}
	
	public void setGuaranteeType(ChargeTypeEnum guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	
	public double getInformationFee() {
		return informationFee;
	}
	
	public void setInformationFee(double informationFee) {
		this.informationFee = informationFee;
	}
	
	public ChargeTypeEnum getInformationFeeType() {
		return this.informationFeeType;
	}
	
	public void setInformationFeeType(ChargeTypeEnum informationFeeType) {
		this.informationFeeType = informationFeeType;
	}
	
	public String getCourt() {
		return court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public double getDeposit() {
		return deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public ChargeTypeEnum getDepositType() {
		return depositType;
	}
	
	public void setDepositType(ChargeTypeEnum depositType) {
		this.depositType = depositType;
	}
	
	public String getContactPerson() {
		return contactPerson;
	}
	
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
	public String getContactNo() {
		return contactNo;
	}
	
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getInvestigatePersion() {
		return investigatePersion;
	}
	
	public void setInvestigatePersion(String investigatePersion) {
		this.investigatePersion = investigatePersion;
	}
	
	public Date getAcceptDate() {
		return acceptDate;
	}
	
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	public Money getGuaranteeAmount() {
		return this.guaranteeAmount;
	}

	public void setGuaranteeAmount(Money guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public String getDepositAccount() {
		return this.depositAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	public String getCaseIntroduce() {
		return caseIntroduce;
	}
	
	public void setCaseIntroduce(String caseIntroduce) {
		this.caseIntroduce = caseIntroduce;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAuditOpinion() {
		return auditOpinion;
	}
	
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	public String getSynthesizeOpinion() {
		return synthesizeOpinion;
	}
	
	public void setSynthesizeOpinion(String synthesizeOpinion) {
		this.synthesizeOpinion = synthesizeOpinion;
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
	
	public long getCoInstitutionId() {
		return coInstitutionId;
	}
	
	public void setCoInstitutionId(long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
}
