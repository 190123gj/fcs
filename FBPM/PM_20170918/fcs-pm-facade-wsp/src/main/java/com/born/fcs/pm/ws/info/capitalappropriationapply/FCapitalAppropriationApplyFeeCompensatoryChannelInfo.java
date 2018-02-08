package com.born.fcs.pm.ws.info.capitalappropriationapply;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请-资金划付信息
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyFeeCompensatoryChannelInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 222033115787441373L;
	//主键
	private long id;
	//表单id
	private long formId;
	//明细id
	private long feeId;
	//资金渠道id
	private long capitalChannelId;
	//资金渠道编码
	private String capitalChannelCode;
	//渠道类型
	private String capitalChannelType;
	//资金渠道名称
	private String capitalChannelName;
	//二级资金渠道
	private long capitalSubChannelId;
	//二级资金渠道编码
	private String capitalSubChannelCode;
	//渠道类型
	private String capitalSubChannelType;
	//二级资金渠道名称
	private String capitalSubChannelName;
	//实际保证金
	private Money actualDepositAmount = new Money(0, 0);
	//流动资金贷款
	private Money liquidityLoanAmount = new Money(0, 0);
	//固定资产融资
	private Money fixedAssetsFinancingAmount = new Money(0, 0);
	//承兑汇票担保
	private Money acceptanceBillAmount = new Money(0, 0);
	//信用证担保
	private Money creditLetterAmount = new Money(0, 0);
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public long getFeeId() {
		return feeId;
	}
	
	public void setFeeId(long feeId) {
		this.feeId = feeId;
	}
	
	public long getCapitalChannelId() {
		return capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelCode() {
		return capitalChannelCode;
	}
	
	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}
	
	public String getCapitalChannelType() {
		return capitalChannelType;
	}
	
	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}
	
	public String getCapitalChannelName() {
		return capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public long getCapitalSubChannelId() {
		return capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelCode() {
		return capitalSubChannelCode;
	}
	
	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}
	
	public String getCapitalSubChannelType() {
		return capitalSubChannelType;
	}
	
	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}
	
	public String getCapitalSubChannelName() {
		return capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public Money getActualDepositAmount() {
		return actualDepositAmount;
	}
	
	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
	}
	
	public Money getLiquidityLoanAmount() {
		return liquidityLoanAmount;
	}
	
	public void setLiquidityLoanAmount(Money liquidityLoanAmount) {
		this.liquidityLoanAmount = liquidityLoanAmount;
	}
	
	public Money getFixedAssetsFinancingAmount() {
		return fixedAssetsFinancingAmount;
	}
	
	public void setFixedAssetsFinancingAmount(Money fixedAssetsFinancingAmount) {
		this.fixedAssetsFinancingAmount = fixedAssetsFinancingAmount;
	}
	
	public Money getAcceptanceBillAmount() {
		return acceptanceBillAmount;
	}
	
	public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
		this.acceptanceBillAmount = acceptanceBillAmount;
	}
	
	public Money getCreditLetterAmount() {
		return creditLetterAmount;
	}
	
	public void setCreditLetterAmount(Money creditLetterAmount) {
		this.creditLetterAmount = creditLetterAmount;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
