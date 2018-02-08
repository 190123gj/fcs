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
	
	/** 产品ID */
	private long productId;
	/** 产品类型 */
	private FinancialProductTypeEnum productType;
	/** 期限类型 */
	private FinancialProductTermTypeEnum termType;
	/** 收益类型 */
	private FinancialProductInterestTypeEnum interestType;
	/** 产品名称 */
	private String productName;
	/** 产品期限 */
	private int timeLimit;
	/** 产品期限单位 */
	private TimeUnitEnum timeUnit;
	/** 发行机构 */
	private String issueInstitution;
	/** 年华率 */
	private double interestRate;
	private double rateRangeStart;
	private double rateRangeEnd;
	/** 结息方式 */
	private InterestSettlementWayEnum interestSettlementWay;
	/** 单价 */
	private Money price = new Money(0, 0);
	/** 出售状态 */
	private FinancialProductSellStatusEnum sellStatus;
	/** 风险等级 */
	private String riskLevel;
	/** 备注 */
	private String remark;
	/** 产品计算收益时候一年的计算天数 */
	private int yearDayNum;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
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
	
	public double getRateRangeStart() {
		return this.rateRangeStart;
	}
	
	public void setRateRangeStart(double rateRangeStart) {
		this.rateRangeStart = rateRangeStart;
	}
	
	public double getRateRangeEnd() {
		return this.rateRangeEnd;
	}
	
	public void setRateRangeEnd(double rateRangeEnd) {
		this.rateRangeEnd = rateRangeEnd;
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
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(int yearDayNum) {
		this.yearDayNum = yearDayNum;
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
