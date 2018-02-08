package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;

/**
 * 会议纪要 - 项目评审会 - 诉讼业务
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectLgLitigationInfo extends FCouncilSummaryProjectInfo {
	
	private static final long serialVersionUID = -8593026469752005570L;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayInfo> chargeWayList;
	/** 主键 */
	private long id;
	/** 会议纪要项目ID */
	private long spId;
	/** 合作机构ID */
	private long coInstitutionId;
	/** 合作机构名称 */
	private String coInstitutionName;
	/** 合作机构服务费 */
	private double coInstitutionCharge;
	/** 合作机构服务费类型 元/% */
	private ChargeTypeEnum coInstitutionChargeType;
	/** 担保费 */
	private double guaranteeFee;
	/** 担保费类型 */
	private ChargeTypeEnum guaranteeFeeType;
	/** 保证金 */
	private double deposit;
	/** 保证金类型 元/% */
	private ChargeTypeEnum depositType;
	/** 保证金存入 帐户名 */
	private String depositAccount;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 元/% */
	private ChargeTypeEnum otherFeeType;
	/** 法院 */
	private String court;
	/** 本次申请保全标的 */
	private String assureObject;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public List<FCouncilSummaryProjectChargeWayInfo> getChargeWayList() {
		return this.chargeWayList;
	}
	
	public void setChargeWayList(List<FCouncilSummaryProjectChargeWayInfo> chargeWayList) {
		this.chargeWayList = chargeWayList;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
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
	
	public double getOtherFee() {
		return this.otherFee;
	}
	
	public void setOtherFee(double otherFee) {
		this.otherFee = otherFee;
	}
	
	public ChargeTypeEnum getOtherFeeType() {
		return this.otherFeeType;
	}
	
	public void setOtherFeeType(ChargeTypeEnum otherFeeType) {
		this.otherFeeType = otherFeeType;
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
