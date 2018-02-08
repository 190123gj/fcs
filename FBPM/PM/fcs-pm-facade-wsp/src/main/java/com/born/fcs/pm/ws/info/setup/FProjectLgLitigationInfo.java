package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;

public class FProjectLgLitigationInfo extends FProjectInfo {
	
	private static final long serialVersionUID = -5791242371539305749L;
	
	private long id;
	
	private double guaranteeFee;
	
	private ChargeTypeEnum guaranteeFeeType;
	
	private long coInstitutionId;
	
	private String coInstitutionName;
	
	private double coInstitutionCharge;
	
	private ChargeTypeEnum coInstitutionChargeType;
	
	private String court;
	
	private double deposit;
	
	private ChargeTypeEnum depositType;
	
	private String depositAccount;
	
	private String assureObject;
	
	private long legalManagerId;
	
	private String legalManagerAccount;
	
	private String legalManagerName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public ChargeTypeEnum getGuaranteeFeeType() {
		return this.guaranteeFeeType;
	}
	
	public void setGuaranteeFeeType(ChargeTypeEnum guaranteeFeeType) {
		this.guaranteeFeeType = guaranteeFeeType;
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
	
	public ChargeTypeEnum getCoInstitutionChargeType() {
		return this.coInstitutionChargeType;
	}
	
	public void setCoInstitutionChargeType(ChargeTypeEnum coInstitutionChargeType) {
		this.coInstitutionChargeType = coInstitutionChargeType;
	}
	
	public String getCourt() {
		return this.court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public double getDeposit() {
		return this.deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public ChargeTypeEnum getDepositType() {
		return this.depositType;
	}
	
	public void setDepositType(ChargeTypeEnum depositType) {
		this.depositType = depositType;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public String getAssureObject() {
		return this.assureObject;
	}
	
	public void setAssureObject(String assureObject) {
		this.assureObject = assureObject;
	}
	
	public long getLegalManagerId() {
		return this.legalManagerId;
	}
	
	public void setLegalManagerId(long legalManagerId) {
		this.legalManagerId = legalManagerId;
	}
	
	public String getLegalManagerAccount() {
		return this.legalManagerAccount;
	}
	
	public void setLegalManagerAccount(String legalManagerAccount) {
		this.legalManagerAccount = legalManagerAccount;
	}
	
	public String getLegalManagerName() {
		return this.legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
