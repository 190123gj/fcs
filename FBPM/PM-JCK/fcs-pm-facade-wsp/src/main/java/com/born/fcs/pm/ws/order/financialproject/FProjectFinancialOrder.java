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
	
	private Long id;
	
	private Long productId;
	
	private String productType;
	
	private String productName;
	
	private String interestType;
	
	private String termType;
	
	private Integer timeLimit;
	
	private String timeUnit;
	
	private String issueInstitution;
	
	private Double interestRate;
	
	private String interestSettlementWay;
	
	private Date expectBuyDate;
	
	private Date expectExpireDate;
	
	private Money price = new Money(0, 0);
	
	private Long buyNum;
	
	private String riskLevel;
	
	private String riskMeasure;
	
	private String attachName;
	
	private String attachUrl;
	
	private Long createUserId;
	
	private String createUserAccount;
	
	private String createUserName;
	
	private Long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	@Override
	public void check() {
		validateNotNull(productId, "理财产品");
		validateHasZore(productId, "理财产品");
	}
	
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
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
	public Long getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
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
	
	public String getAttachName() {
		return this.attachName;
	}
	
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	
	public String getAttachUrl() {
		return this.attachUrl;
	}
	
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
	
	public Long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(Long createUserId) {
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
	
	public Long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptPath() {
		return this.deptPath;
	}
	
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}
	
	public String getDeptPathName() {
		return this.deptPathName;
	}
	
	public void setDeptPathName(String deptPathName) {
		this.deptPathName = deptPathName;
	}
	
	public String getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	
}
