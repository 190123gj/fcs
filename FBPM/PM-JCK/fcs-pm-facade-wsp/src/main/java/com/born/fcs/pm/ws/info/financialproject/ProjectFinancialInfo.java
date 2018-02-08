package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目信息
 *
 *
 * @author wuzj
 *
 */
public class ProjectFinancialInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5459321241227995755L;
	
	private long projectId;
	
	private String projectCode;
	
	private String projectName;
	
	private long productId;
	
	private FinancialProductTypeEnum productType;
	
	private String productName;
	
	private FinancialProductTermTypeEnum termType;
	
	private FinancialProductInterestTypeEnum interestType;
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
	private String issueInstitution;
	
	private double interestRate;
	
	private InterestSettlementWayEnum interestSettlementWay;
	
	private Date expectBuyDate;
	
	private Date expectExpireDate;
	
	private Money price = new Money(0, 0);
	
	private long buyNum;
	
	private String riskLevel;
	
	private String riskMeasure;
	
	private String attachName;
	
	private String attachUrl;
	
	private long createUserId;
	
	private String createUserAccount;
	
	private String createUserName;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	private Date actualBuyDate;
	
	private Date actualExpireDate;
	
	private Money actualPrice = new Money(0, 0);
	
	private long actualBuyNum;
	
	private long actualHoldNum;
	
	private long originalHoldNum;
	
	private Money actualPrincipal = new Money(0, 0);
	
	private Money actualInterest = new Money(0, 0);
	
	private double actualInterestRate;
	
	private long transferedNum;
	
	private long buyBackNum;
	
	private long redeemedNum;
	
	private FinancialProjectStatusEnum status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
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
	
	public FinancialProductInterestTypeEnum getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(FinancialProductInterestTypeEnum interestType) {
		this.interestType = interestType;
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
	
	public long getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(long buyNum) {
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
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
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
	
	public Date getActualBuyDate() {
		return this.actualBuyDate;
	}
	
	public void setActualBuyDate(Date actualBuyDate) {
		this.actualBuyDate = actualBuyDate;
	}
	
	public Date getActualExpireDate() {
		return this.actualExpireDate;
	}
	
	public void setActualExpireDate(Date actualExpireDate) {
		this.actualExpireDate = actualExpireDate;
	}
	
	public Money getActualPrice() {
		return this.actualPrice;
	}
	
	public void setActualPrice(Money actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	public long getActualBuyNum() {
		return this.actualBuyNum;
	}
	
	public void setActualBuyNum(long actualBuyNum) {
		this.actualBuyNum = actualBuyNum;
	}
	
	public long getActualHoldNum() {
		return this.actualHoldNum;
	}
	
	public void setActualHoldNum(long actualHoldNum) {
		this.actualHoldNum = actualHoldNum;
	}
	
	public Money getActualPrincipal() {
		return this.actualPrincipal;
	}
	
	public void setActualPrincipal(Money actualPrincipal) {
		this.actualPrincipal = actualPrincipal;
	}
	
	public Money getActualInterest() {
		return this.actualInterest;
	}
	
	public void setActualInterest(Money actualInterest) {
		this.actualInterest = actualInterest;
	}
	
	public double getActualInterestRate() {
		return this.actualInterestRate;
	}
	
	public void setActualInterestRate(double actualInterestRate) {
		this.actualInterestRate = actualInterestRate;
	}
	
	public long getTransferedNum() {
		return this.transferedNum;
	}
	
	public void setTransferedNum(long transferedNum) {
		this.transferedNum = transferedNum;
	}
	
	public long getBuyBackNum() {
		return this.buyBackNum;
	}
	
	public void setBuyBackNum(long buyBackNum) {
		this.buyBackNum = buyBackNum;
	}
	
	public long getRedeemedNum() {
		return this.redeemedNum;
	}
	
	public void setRedeemedNum(long redeemedNum) {
		this.redeemedNum = redeemedNum;
	}
	
	public FinancialProjectStatusEnum getStatus() {
		return this.status;
	}
	
	public void setStatus(FinancialProjectStatusEnum status) {
		this.status = status;
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
	
	public long getOriginalHoldNum() {
		return this.originalHoldNum;
	}
	
	public void setOriginalHoldNum(long originalHoldNum) {
		this.originalHoldNum = originalHoldNum;
	}
}
