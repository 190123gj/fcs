package com.born.fcs.pm.ws.order.check;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 保后 - 诉讼保函类
 * 
 * @author lirz
 * 
 * 2016-7-22 上午11:38:52
 */
public class FAfterwardsCheckLitigationOrder extends FAfterwardsCheckOrder {
	
	private static final long serialVersionUID = -6322072390495206475L;
	
	private long litigationId;
	private long coInstitutionId;
	private String coInstitutionName;
	private double coInstitutionCharge;
	private String coInstitutionChargeType;
	private double guaranteeFee;
	private String guaranteeFeeType;
	private String busiManagerName;
	private String riskManagerName;
	private String assureObject;
	private String caseStatus;
	private Date openingDate;
	private Date judgeDate;
	private String remark;
	
	public long getLitigationId() {
		return this.litigationId;
	}
	
	public void setLitigationId(long litigationId) {
		this.litigationId = litigationId;
	}
	
	public long getCoInstitutionId() {
		return this.coInstitutionId;
	}
	
	public void setCoInstitutionId(long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return this.coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
	public double getCoInstitutionCharge() {
		return this.coInstitutionCharge;
	}
	
	public void setCoInstitutionCharge(double coInstitutionCharge) {
		this.coInstitutionCharge = coInstitutionCharge;
	}
	
	public String getCoInstitutionChargeType() {
		return this.coInstitutionChargeType;
	}
	
	public void setCoInstitutionChargeType(String coInstitutionChargeType) {
		this.coInstitutionChargeType = coInstitutionChargeType;
	}
	
	public double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public String getGuaranteeFeeType() {
		return this.guaranteeFeeType;
	}
	
	public void setGuaranteeFeeType(String guaranteeFeeType) {
		this.guaranteeFeeType = guaranteeFeeType;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getAssureObject() {
		return this.assureObject;
	}
	
	public void setAssureObject(String assureObject) {
		this.assureObject = assureObject;
	}
	
	public String getCaseStatus() {
		return this.caseStatus;
	}
	
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	
	public Date getOpeningDate() {
		return this.openingDate;
	}
	
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}
	
	public Date getJudgeDate() {
		return this.judgeDate;
	}
	
	public void setJudgeDate(Date judgeDate) {
		this.judgeDate = judgeDate;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
