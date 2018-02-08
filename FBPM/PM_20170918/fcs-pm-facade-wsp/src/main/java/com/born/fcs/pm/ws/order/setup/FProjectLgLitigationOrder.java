package com.born.fcs.pm.ws.order.setup;

/**
 * 项目立项 - 诉讼保函详情Order
 *
 * @author wuzj
 *
 */
public class FProjectLgLitigationOrder extends FProjectOrder {
	
	private static final long serialVersionUID = 8852342689716626575L;
	
	private Long id;
	
	private Double guaranteeFee;
	
	private String guaranteeFeeType;
	
	private Long coInstitutionId;
	
	private String coInstitutionName;
	
	private Double coInstitutionCharge;
	
	private String coInstitutionChargeType;
	
	private String court;
	
	private Double deposit;
	
	private String depositType;
	
	private String depositAccount;
	
	private String assureObject;
	
	private Long legalManagerId;
	
	private String legalManagerAccount;
	
	private String legalManagerName;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getGuaranteeFee() {
		return this.guaranteeFee;
	}
	
	public void setGuaranteeFee(Double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}
	
	public String getGuaranteeFeeType() {
		return this.guaranteeFeeType;
	}
	
	public void setGuaranteeFeeType(String guaranteeFeeType) {
		this.guaranteeFeeType = guaranteeFeeType;
	}
	
	public Long getCoInstitutionId() {
		return this.coInstitutionId;
	}
	
	public void setCoInstitutionId(Long coInstitutionId) {
		this.coInstitutionId = coInstitutionId;
	}
	
	public String getCoInstitutionName() {
		return this.coInstitutionName;
	}
	
	public void setCoInstitutionName(String coInstitutionName) {
		this.coInstitutionName = coInstitutionName;
	}
	
	public Double getCoInstitutionCharge() {
		return this.coInstitutionCharge;
	}
	
	public void setCoInstitutionCharge(Double coInstitutionCharge) {
		this.coInstitutionCharge = coInstitutionCharge;
	}
	
	public String getCoInstitutionChargeType() {
		return this.coInstitutionChargeType;
	}
	
	public void setCoInstitutionChargeType(String coInstitutionChargeType) {
		this.coInstitutionChargeType = coInstitutionChargeType;
	}
	
	public String getCourt() {
		return this.court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public Double getDeposit() {
		return this.deposit;
	}
	
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	
	public String getDepositType() {
		return this.depositType;
	}
	
	public void setDepositType(String depositType) {
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
	
	public Long getLegalManagerId() {
		return this.legalManagerId;
	}
	
	public void setLegalManagerId(Long legalManagerId) {
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
	
}
