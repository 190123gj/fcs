/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

// auto generated imports
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目立项列表DO
 *
 * @author wuzj
 */
public class FinancialProjectSetupFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = 7150743408866870966L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private long productId;
	
	private String productType;
	
	private String productName;
	
	private String interestType;
	
	private String termType;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private String issueInstitution;
	
	private double interestRate;
	
	private double rateRangeStart;
	
	private double rateRangeEnd;
	
	private String interestSettlementWay;
	
	private Date expectBuyDate;
	
	private Date expectExpireDate;
	
	private double expectBuyNum;
	
	private double buyNum;
	
	private int buyTimes;
	
	private String canRedeem;
	
	/** 是否滚动 */
	private String isRoll;
	/** 产品计算收益时候一年的计算天数 */
	private int yearDayNum;
	
	private Money price = new Money(0, 0);
	
	private String riskLevel;
	
	private String riskMeasure;
	
	private long createUserId;
	
	private String createUserAccount;
	
	private String createUserName;
	
	private long deptId;
	
	private String deptCode;
	
	private String deptName;
	
	private String deptPath;
	
	private String deptPathName;
	
	/** 上会申请ID */
	private long councilApplyId;
	/** 上会类型 */
	private String councilType;
	/** 上会状态 */
	private String councilStatus;
	/** 选择合同项目 */
	private String chooseForContract;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private long loginUserId;
	private List<Long> deptIdList;
	private long limitStart;
	private long pageSize;
	private String sortCol;
	private String sortOrder;
	private List<String> formStatusList;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
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
	
	public String getInterestType() {
		return this.interestType;
	}
	
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	
	public String getTermType() {
		return this.termType;
	}
	
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
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
	
	public double getExpectBuyNum() {
		return this.expectBuyNum;
	}
	
	public void setExpectBuyNum(double expectBuyNum) {
		this.expectBuyNum = expectBuyNum;
	}
	
	public double getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(double buyNum) {
		this.buyNum = buyNum;
	}
	
	public int getBuyTimes() {
		return this.buyTimes;
	}
	
	public void setBuyTimes(int buyTimes) {
		this.buyTimes = buyTimes;
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
	
	public int getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(int yearDayNum) {
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
	
	public long getCouncilApplyId() {
		return this.councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public String getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilStatus() {
		return this.councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
		this.councilStatus = councilStatus;
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public String getChooseForContract() {
		return this.chooseForContract;
	}
	
	public void setChooseForContract(String chooseForContract) {
		this.chooseForContract = chooseForContract;
	}
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
