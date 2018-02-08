package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductSellStatusEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.yjf.common.lang.util.money.Money;

public class FinancialProductInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6342338110984192197L;
	
	private long productId;
	
	private FinancialProductTypeEnum productType;
	
	private FinancialProductTermTypeEnum termType;
	
	private FinancialProductInterestTypeEnum interestType;
	
	private String productName;
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
	private String issueInstitution;
	
	private double interestRate;
	
	private InterestSettlementWayEnum interestSettlementWay;
	
	private Money price = new Money(0, 0);
	
	private FinancialProductSellStatusEnum sellStatus;
	
	private String riskLevel;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public FinancialProductTypeEnum getProductType() {
		return this.productType;
	}
	
	public void setProductType(FinancialProductTypeEnum productType) {
		this.productType = productType;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public FinancialProductTermTypeEnum getTermType() {
		return this.termType;
	}
	
	public void setTermType(FinancialProductTermTypeEnum termType) {
		this.termType = termType;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public InterestSettlementWayEnum getInterestSettlementWay() {
		return this.interestSettlementWay;
	}
	
	public void setInterestSettlementWay(InterestSettlementWayEnum interestSettlementWay) {
		this.interestSettlementWay = interestSettlementWay;
	}
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public FinancialProductSellStatusEnum getSellStatus() {
		return this.sellStatus;
	}
	
	public void setSellStatus(FinancialProductSellStatusEnum sellStatus) {
		this.sellStatus = sellStatus;
	}
	
	public String getRiskLevel() {
		return this.riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
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
	
	public FinancialProductInterestTypeEnum getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(FinancialProductInterestTypeEnum interestType) {
		this.interestType = interestType;
	}
	
}
