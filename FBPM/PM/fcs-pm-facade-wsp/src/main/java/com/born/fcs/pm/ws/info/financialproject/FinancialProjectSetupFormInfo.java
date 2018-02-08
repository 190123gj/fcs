package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目立项列表INFO
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectSetupFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 6910361401999733452L;
	
	/** 主键 */
	private long id;
	/** 产品ID */
	private long productId;
	/** 立项编号 */
	private String projectCode;
	/** 产品类型 */
	private FinancialProductTypeEnum productType;
	/** 产品名称 */
	private String productName;
	/** 期限类型 */
	private FinancialProductTermTypeEnum termType;
	/** 收益类型 */
	private FinancialProductInterestTypeEnum interestType;
	/** 期限 */
	private int timeLimit;
	/** 期限单位 */
	private TimeUnitEnum timeUnit;
	/** 发行机构 */
	private String issueInstitution;
	/** 年化收益率% */
	private double interestRate;
	private double rateRangeStart;
	private double rateRangeEnd;
	/** 产品结息方式 */
	private InterestSettlementWayEnum interestSettlementWay;
	/** 拟购买时间 */
	private Date expectBuyDate;
	/** 拟到期时间 */
	private Date expectExpireDate;
	/** 拟申购份数 */
	private double expectBuyNum;
	/** 已申购份数 */
	private double buyNum;
	/** 购买次数 */
	private int buyTimes;
	/** 是否可提前赎回 YES/NO */
	private BooleanEnum canRedeem;
	/** 是否滚动 */
	private BooleanEnum isRoll;
	/** 产品计算收益时候一年的计算天数 */
	private int yearDayNum;
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
	/** 部门ID */
	private long deptId;
	/** 部门编号 */
	private String deptCode;
	/** 部门名称 */
	private String deptName;
	/** 部门路径 */
	private String deptPath;
	/** 部门路径名称 */
	private String deptPathName;
	/** 上会申请ID */
	private long councilApplyId;
	/** 上会类型 */
	private ProjectCouncilEnum councilType;
	/** 上会状态 */
	private ProjectCouncilStatusEnum councilStatus;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	
	public BooleanEnum getCanRedeem() {
		return this.canRedeem;
	}
	
	public void setCanRedeem(BooleanEnum canRedeem) {
		this.canRedeem = canRedeem;
	}
	
	public BooleanEnum getIsRoll() {
		return this.isRoll;
	}
	
	public void setIsRoll(BooleanEnum isRoll) {
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
	
	public ProjectCouncilEnum getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(ProjectCouncilEnum councilType) {
		this.councilType = councilType;
	}
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return this.councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
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
}
