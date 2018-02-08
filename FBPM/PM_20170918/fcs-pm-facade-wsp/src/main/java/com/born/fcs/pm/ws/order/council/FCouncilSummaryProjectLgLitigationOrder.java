package com.born.fcs.pm.ws.order.council;

import java.util.List;

/**
 * 会议纪要 - 项目评审会 - 诉讼业务项目详情
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectLgLitigationOrder extends FCouncilSummaryProjectOrder {
	
	private static final long serialVersionUID = 7238682950921371057L;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder;
	/** 主键 */
	private Long id;
	/** 合作机构ID */
	private Long coInstitutionId;
	/** 合作机构名称 */
	private String coInstitutionName;
	/** 合作机构服务费 */
	private Double coInstitutionCharge;
	/** 合作机构服务费类型 元/% */
	private String coInstitutionChargeType;
	/** 担保费 */
	private Double guaranteeFee;
	/** 担保费类型 */
	private String guaranteeFeeType;
	/** 保证金 */
	private Double deposit;
	/** 保证金类型 元/% */
	private String depositType;
	/** 保证金存入 帐户名 */
	private String depositAccount;
	/** 其他费用 */
	private Double otherFee;
	/** 其他费用类型 元/% */
	private String otherFeeType;
	/** 法院 */
	private String court;
	/** 本次申请保全标的 */
	private String assureObject;
	
	public List<FCouncilSummaryProjectChargeWayOrder> getChargeWayOrder() {
		return this.chargeWayOrder;
	}
	
	public void setChargeWayOrder(List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder) {
		this.chargeWayOrder = chargeWayOrder;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Double getOtherFee() {
		return this.otherFee;
	}
	
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	
	public String getOtherFeeType() {
		return this.otherFeeType;
	}
	
	public void setOtherFeeType(String otherFeeType) {
		this.otherFeeType = otherFeeType;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public String getCourt() {
		return this.court;
	}
	
	public void setCourt(String court) {
		this.court = court;
	}
	
	public String getAssureObject() {
		return this.assureObject;
	}
	
	public void setAssureObject(String assureObject) {
		this.assureObject = assureObject;
	}
	
}
