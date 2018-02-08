package com.born.fcs.pm.ws.info.council;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;

/**
 * 会议纪要 - 项目评审会 - 委贷项目会议纪要
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectEntrustedInfo extends FCouncilSummaryProjectCreditConditionInfo {
	
	private static final long serialVersionUID = -397706441570375454L;
	
	/** 主键 */
	private long id;
	/** 会议纪要对应项目ID */
	private long spId;
	/** 资金渠道ID */
	private long capitalChannelId;
	/** 资金渠道编码 */
	private String capitalChannelCode;
	/** 渠道类型 */
	private String capitalChannelType;
	/** 资金渠道名称 */
	private String capitalChannelName;
	/** 二级资金渠道ID */
	private long capitalSubChannelId;
	/** 二级资金渠道编码 */
	private String capitalSubChannelCode;
	/** 渠道类型 */
	private String capitalSubChannelType;
	/** 二级资金渠道名称 */
	private String capitalSubChannelName;
	/** 多个资金渠道 */
	private List<ProjectChannelRelationInfo> capitalChannels;
	/** 利率 */
	private double interestRate;
	/** 其他费用 */
	private double otherFee;
	/** 其他费用类型 */
	private ChargeTypeEnum otherFeeType;
	/** 保证金 */
	private double deposit;
	/** 保证金类型 */
	private ChargeTypeEnum depositType;
	/** 保证金存入 帐户名 */
	private String depositAccount;
	/** 过程控制标识 */
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
	
	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
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
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
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
	
	public List<ProjectChannelRelationInfo> getCapitalChannels() {
		return this.capitalChannels;
	}
	
	public void setCapitalChannels(List<ProjectChannelRelationInfo> capitalChannels) {
		this.capitalChannels = capitalChannels;
	}
}
