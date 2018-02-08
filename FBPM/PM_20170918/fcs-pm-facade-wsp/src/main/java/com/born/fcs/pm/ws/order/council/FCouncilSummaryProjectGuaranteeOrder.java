package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;

/**
 * 会议纪要 - 项目评审会 - 担保项目详情
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectGuaranteeOrder extends
													FCouncilSummaryProjectCreditConditionOrder {
	
	private static final long serialVersionUID = -8423050786660326695L;
	/** 主键 */
	private Long id;
	/** 资金渠道ID */
	private Long capitalChannelId;
	/** 资金渠道编码 */
	private String capitalChannelCode;
	/** 渠道类型 */
	private String capitalChannelType;
	/** 资金渠道名称 */
	private String capitalChannelName;
	/** 二级资金渠道ID */
	private Long capitalSubChannelId;
	/** 二级资金渠道编码 */
	private String capitalSubChannelCode;
	/** 渠道类型 */
	private String capitalSubChannelType;
	/** 二级资金渠道名称 */
	private String capitalSubChannelName;
	/** 多个资金渠道 */
	private List<ProjectChannelRelationOrder> capitalChannelOrder;
	/** 利率 */
	private Double interestRate;
	/** 利率浮动(大于/小于等) */
	private String interestRateFloat;
	/** 担保费 */
	private Double guaranteeFee;
	/** 担保费类型 */
	private String guaranteeFeeType;
	/** 总成本 */
	private Double totalCost;
	/** 总成本类型 元/% */
	private String totalCostType;
	/** 其他费用 */
	private Double otherFee;
	/** 其他费用类型 */
	private String otherFeeType;
	/** 保证金 */
	private Double deposit;
	/** 保证金类型 元/% */
	private String depositType;
	/** 保证金存入 帐户名 */
	private String depositAccount;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(Long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelCode() {
		return this.capitalChannelCode;
	}
	
	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public Long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(Long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelCode() {
		return this.capitalSubChannelCode;
	}
	
	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public List<ProjectChannelRelationOrder> getCapitalChannelOrder() {
		return this.capitalChannelOrder;
	}
	
	public void setCapitalChannelOrder(List<ProjectChannelRelationOrder> capitalChannelOrder) {
		this.capitalChannelOrder = capitalChannelOrder;
	}
	
	public Double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getInterestRateFloat() {
		return this.interestRateFloat;
	}
	
	public void setInterestRateFloat(String interestRateFloat) {
		this.interestRateFloat = interestRateFloat;
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
	
	public Double getTotalCost() {
		return this.totalCost;
	}
	
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	public String getTotalCostType() {
		return this.totalCostType;
	}
	
	public void setTotalCostType(String totalCostType) {
		this.totalCostType = totalCostType;
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
	
	public String getCapitalChannelType() {
		return this.capitalChannelType;
	}
	
	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}
	
	public String getCapitalSubChannelType() {
		return this.capitalSubChannelType;
	}
	
	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}
	
}
