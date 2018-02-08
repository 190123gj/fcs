package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目立项 - 理财项目详情Order
 *
 * @author wuzj
 *
 */
public class FProjectFinancialOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 5352399794573984456L;
	/** 主键 */
	private Long id;
	/** 产品ID */
	private Long productId;
	/** 立项编号 */
	private String projectCode;
	/** 产品类型 */
	private String productType;
	/** 产品名称 */
	private String productName;
	/** 期限类型 */
	private String termType;
	/** 收益类型 */
	private String interestType;
	/** 期限 */
	private Integer timeLimit;
	/** 期限单位 */
	private String timeUnit;
	/** 发行机构 */
	private String issueInstitution;
	/** 年化收益率% */
	private Double interestRate;
	private Double rateRangeStart;
	private Double rateRangeEnd;
	/** 产品结息方式 */
	private String interestSettlementWay;
	/** 拟购买时间 */
	private Date expectBuyDate;
	/** 拟到期时间 */
	private Date expectExpireDate;
	/** 拟申购份数 */
	private Double expectBuyNum;
	/** 已申购份数 */
	private Double buyNum;
	/** 是否可提前赎回 YES/NO */
	private String canRedeem;
	/** 是否滚动 */
	private String isRoll;
	/** 产品计算收益时候一年的计算天数 */
	private Integer yearDayNum;
	/** 单价 */
	private Money price = new Money(0, 0);
	/** 风险等级 */
	private String riskLevel;
	/** 风险措施 */
	private String riskMeasure;
	/** 创建人 */
	private long createUserId;
	/** 创建人账号 */
	private String createUserAccount;
	/** 创建人名称 */
	private String createUserName;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProductId() {
		return this.productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public String getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(String interestType) {
		this.interestType = interestType;
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
	
	public Date getExpectBuyDate() {
		return this.expectBuyDate;
	}
	
	public void setExpectBuyDate(Date expectBuyDate) {
		this.expectBuyDate = expectBuyDate;
	}
	
	public Date getExpectExpireDate() {
		return this.expectExpireDate;
	}
	
	public void setExpectExpireDate(Date expectExpireDate) {
		this.expectExpireDate = expectExpireDate;
	}
	
	public Double getExpectBuyNum() {
		return this.expectBuyNum;
	}
	
	public void setExpectBuyNum(Double expectBuyNum) {
		this.expectBuyNum = expectBuyNum;
	}
	
	public Double getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(Double buyNum) {
		this.buyNum = buyNum;
	}
	
	public String getCanRedeem() {
		return this.canRedeem;
	}
	
	public void setCanRedeem(String canRedeem) {
		this.canRedeem = canRedeem;
	}
	
	public String getIsRoll() {
		return this.isRoll;
	}
	
	public void setIsRoll(String isRoll) {
		this.isRoll = isRoll;
	}
	
	public Integer getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(Integer yearDayNum) {
		this.yearDayNum = yearDayNum;
	}
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public String getRiskLevel() {
		return this.riskLevel;
	}
	
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	public String getRiskMeasure() {
		return this.riskMeasure;
	}
	
	public void setRiskMeasure(String riskMeasure) {
		this.riskMeasure = riskMeasure;
	}
	
	public long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getCreateUserAccount() {
		return this.createUserAccount;
	}
	
	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}
	
	public String getCreateUserName() {
		return this.createUserName;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
