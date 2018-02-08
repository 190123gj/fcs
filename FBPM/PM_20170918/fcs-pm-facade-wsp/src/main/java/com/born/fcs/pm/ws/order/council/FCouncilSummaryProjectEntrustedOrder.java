package com.born.fcs.pm.ws.order.council;

/**
 * 会议纪要 - 项目评审会 - 委贷项目
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectEntrustedOrder extends
													FCouncilSummaryProjectCreditConditionOrder {
	
	private static final long serialVersionUID = 2922104977215699844L;
	/** 主键 */
	private long id;
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
	/** 利率 */
	private Double interestRate;
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
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
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
	
	public Double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
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
