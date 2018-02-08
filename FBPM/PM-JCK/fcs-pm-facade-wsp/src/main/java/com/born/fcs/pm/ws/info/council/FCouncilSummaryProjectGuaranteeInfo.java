package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;

/**
 * 会议纪要 - 项目评审会 - 担保目批复
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectGuaranteeInfo extends FCouncilSummaryProjectCreditConditionInfo {
	
	private static final long serialVersionUID = 1350268194776541986L;
	
	/** 主键 */
	private long id;
	/** 会议纪要项目ID */
	private long spId;
	/** 资金渠道ID */
	private long capitalChannelId;
	/** 资金渠道名称 */
	private String capitalChannelName;
	/** 二级资金渠道 */
	private long capitalSubChannelId;
	/** 二级资金渠道名称 */
	private String capitalSubChannelName;
	/** 利率 */
	private double interestRate;
	/** 利率浮动(大于/小于等) */
	private CompareEnum interestRateFloat;
	/** 担保费 */
	private double guaranteeFee;
	/** 担保费类型 */
	private ChargeTypeEnum guaranteeFeeType;
	/** 总成本 */
	private double totalCost;
	/** 总成本类型 元/% */
	private ChargeTypeEnum totalCostType;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 */
	private ChargeTypeEnum otherFeeType;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
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
	
	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public CompareEnum getInterestRateFloat() {
		return this.interestRateFloat;
	}
	
	public void setInterestRateFloat(CompareEnum interestRateFloat) {
		this.interestRateFloat = interestRateFloat;
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
	
	public double getTotalCost() {
		return this.totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public ChargeTypeEnum getTotalCostType() {
		return this.totalCostType;
	}
	
	public void setTotalCostType(ChargeTypeEnum totalCostType) {
		this.totalCostType = totalCostType;
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
