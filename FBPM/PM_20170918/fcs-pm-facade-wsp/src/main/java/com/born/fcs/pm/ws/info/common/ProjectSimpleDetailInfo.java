package com.born.fcs.pm.ws.info.common;

import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;

/**
 * 简单项目详细信息
 *
 * @author wuzj
 */
public class ProjectSimpleDetailInfo extends ProjectInfo {
	
	private static final long serialVersionUID = -8936125193316856139L;
	
	//机构信息
	private long institutionId;
	
	//（担保/委贷：资金渠道、承销：交易所、诉讼担保/发债：合作机构）
	private String institutionTypeName;
	
	private String institutionName;
	
	//多个资金渠道的情况下
	private String institutionIds;
	private String institutionNames;
	
	//授信费（利）率
	private double chargeFee;
	
	//担保/发债 /诉讼担保: 担保费、委贷：利率、承销：承销费、
	private String chargeName;
	
	//收费类型
	private ChargeTypeEnum chargeType;
	
	//收费阶段（先收/后扣）
	private ChargePhaseEnum chargePhase;
	
	//利率
	private double interestRate;
	//利率浮动
	private CompareEnum interrestFloat;
	
	public long getInstitutionId() {
		return this.institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}
	
	public String getInstitutionTypeName() {
		return this.institutionTypeName;
	}
	
	public void setInstitutionTypeName(String institutionTypeName) {
		this.institutionTypeName = institutionTypeName;
	}
	
	public String getInstitutionName() {
		return this.institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public double getChargeFee() {
		return this.chargeFee;
	}
	
	public void setChargeFee(double chargeFee) {
		this.chargeFee = chargeFee;
	}
	
	public String getChargeName() {
		return this.chargeName;
	}
	
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	
	public ChargeTypeEnum getChargeType() {
		return this.chargeType;
	}
	
	public void setChargeType(ChargeTypeEnum chargeType) {
		this.chargeType = chargeType;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public CompareEnum getInterrestFloat() {
		return this.interrestFloat;
	}
	
	public void setInterrestFloat(CompareEnum interrestFloat) {
		this.interrestFloat = interrestFloat;
	}
	
	public ChargePhaseEnum getChargePhase() {
		return this.chargePhase;
	}
	
	public void setChargePhase(ChargePhaseEnum chargePhase) {
		this.chargePhase = chargePhase;
	}
	
	public String getInstitutionIds() {
		return this.institutionIds;
	}
	
	public void setInstitutionIds(String institutionIds) {
		this.institutionIds = institutionIds;
	}
	
	public String getInstitutionNames() {
		return this.institutionNames;
	}
	
	public void setInstitutionNames(String institutionNames) {
		this.institutionNames = institutionNames;
	}
	
}
