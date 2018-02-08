package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.finvestigation.base.FInvestigationBaseOrder;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目调查 - 诉讼担保项目调查报告
 * 
 * @author lirz
 *
 * 2016-3-23 下午4:13:14
 */
public class FInvestigationLitigationOrder extends FInvestigationBaseOrder {
	
	private static final long serialVersionUID = 3865875681944282095L;
	
	private long litigationId; //主键ID
	private Double guaranteeFee;
	private String guaranteeType;
	private Long coInstitutionId; //合作机构ID
	private String coInstitutionName; //合作机构名称
	private Double informationFee; //法律咨询费
	private String informationFeeType; //法律咨询费类型
	private String court; //法院
	private Double deposit; //保证金
	private String depositType; //保证金类型
	private String contactPerson; //项目联系人
	private String contactNo; //联系电话
	private String investigatePersion; //调查人员
	private Date acceptDate; //受理时间
	private String guaranteeAmountStr; //本次申请保全金额
	private String depositAccount; //保证金存入账户名
	private String caseIntroduce; //案情介绍
	private String content; //拟保全标的或内容
	private String auditOpinion; //风险审核意见
	private String synthesizeOpinion; //项目综合意见
	
	//========== getters and setters ==========
	
	public long getLitigationId() {
		return litigationId;
	}
	
	public void setLitigationId(long litigationId) {
		this.litigationId = litigationId;
	}
	
	public Double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public void setGuaranteeFee(Double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public String getGuaranteeType() {
		return this.guaranteeType;
	}
	
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	
	public Double getInformationFee() {
		return informationFee;
	}
	
	public void setInformationFee(Double informationFee) {
		this.informationFee = informationFee;
	}
	
	public String getInformationFeeType() {
		return this.informationFeeType;
	}
	
	public void setInformationFeeType(String informationFeeType) {
		this.informationFeeType = informationFeeType;
	}
	
	public String getCourt() {
		return court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public Double getDeposit() {
		return deposit;
	}
	
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	
	public String getDepositType() {
		return depositType;
	}
	
	public void setDepositType(String depositType) {
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
	
	public void setAcceptDateStr(String acceptDateStr) {
		this.acceptDate = DateUtil.strToDtSimpleFormat(acceptDateStr);
	}
	
	public Money getGuaranteeAmount() {
		if (isNull(this.guaranteeAmountStr)) {
			return new Money(0L);
		} else {
			return Money.amout(this.guaranteeAmountStr);
		}
	}

	public String getGuaranteeAmountStr() {
		return this.guaranteeAmountStr;
	}

	public void setGuaranteeAmountStr(String guaranteeAmountStr) {
		this.guaranteeAmountStr = guaranteeAmountStr;
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
	
	public Long getCoInstitutionId() {
		return coInstitutionId;
	}
	
	public void setCoInstitutionId(Long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
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
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
