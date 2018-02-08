/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.basicmaintain;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财产品Order
 *
 * @author wuzj
 *
 */
public class FinancialProductOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -8900772014850296389L;
	
	/** 产品ID */
	private Long productId;
	/** 产品类型 */
	private String productType;
	/** 期限类型 */
	private String termType;
	/** 收益类型 */
	private String interestType;
	/** 产品名称 */
	private String productName;
	/** 产品期限 */
	private Integer timeLimit;
	/** 产品期限单位 */
	private String timeUnit;
	/** 发行机构 */
	private String issueInstitution;
	/** 年华率 */
	private Double interestRate;
	private Double rateRangeStart;
	private Double rateRangeEnd;
	/** 结息方式 */
	private String interestSettlementWay;
	/** 单价 */
	private Money price = new Money(0, 0);
	/** 出售状态 */
	private String sellStatus;
	/** 风险等级 */
	private String riskLevel;
	/** 备注 */
	private String remark;
	/** 产品计算收益时候一年的计算天数 */
	private Integer yearDayNum;
	
	public Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Integer getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public Double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	public Double getRateRangeStart() {
		return this.rateRangeStart;
	}
	
	public void setRateRangeStart(Double rateRangeStart) {
		this.rateRangeStart = rateRangeStart;
	}
	
	public Double getRateRangeEnd() {
		return this.rateRangeEnd;
	}
	
	public void setRateRangeEnd(Double rateRangeEnd) {
		this.rateRangeEnd = rateRangeEnd;
	}
	
	public String getInterestSettlementWay() {
		return this.interestSettlementWay;
	}
	
	public void setInterestSettlementWay(String interestSettlementWay) {
		this.interestSettlementWay = interestSettlementWay;
	}
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public String getSellStatus() {
		return this.sellStatus;
	}
	
	public void setSellStatus(String sellStatus) {
		this.sellStatus = sellStatus;
	}
	
	public String getRiskLevel() {
		return this.riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(Integer yearDayNum) {
		this.yearDayNum = yearDayNum;
	}
	
}
