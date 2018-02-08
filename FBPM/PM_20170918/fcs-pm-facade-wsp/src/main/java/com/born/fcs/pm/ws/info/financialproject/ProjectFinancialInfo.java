package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FinancialProductInterestTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.InterestSettlementWayEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.DateUtil;
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
	
	/** 项目ID */
	private long projectId;
	/** 项目编号 */
	private String projectCode;
	/** 立项编号 */
	private String originalCode;
	/** 产品ID */
	private long productId;
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
	/** 收益率 */
	private double interestRate;
	/** 结息方式 */
	private InterestSettlementWayEnum interestSettlementWay;
	/** 拟购买时间 */
	private Date expectBuyDate;
	/** 拟到期时间 */
	private Date expectExpireDate;
	/** 产品单价 */
	private Money price = new Money(0, 0);
	/** 买入份额 */
	private double buyNum;
	/** 风险等级 */
	private String riskLevel;
	/** 风险措施 */
	private String riskMeasure;
	/** 创建人ID */
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
	/** 部门路径名 */
	private String deptPathName;
	/** 实际购买日期 */
	private Date actualBuyDate;
	/** 实际到期日期 */
	private Date actualExpireDate;
	/** 滚动到期时间 */
	private Date cycleExpireDate;
	/** 购买单价 */
	private Money actualPrice = new Money(0, 0);
	/** 是否可提前赎回 YES/NO */
	private BooleanEnum canRedeem;
	/** 是否滚动 */
	private BooleanEnum isRoll;
	/** 是否开放 */
	private BooleanEnum isOpen;
	/** 产品计算收益时候一年的计算天数 */
	private int yearDayNum;
	/** 买入份额 */
	private double actualBuyNum;
	/** 实际持有份额 */
	private double actualHoldNum;
	/** 不含转让回购部分的份额 */
	private double originalHoldNum;
	/** 实际本金 */
	private Money actualPrincipal = new Money(0, 0);
	/** 实际收益 */
	private Money actualInterest = new Money(0, 0);
	/** 实际利率 */
	private double actualInterestRate;
	/** 累计结息金额 */
	private Money settlementAmount = new Money(0, 0);
	/** 已转让份额 */
	private double transferedNum;
	/** 已回购份额 */
	private double buyBackNum;
	/** 已赎回份额 */
	private double redeemedNum;
	/** 提前完成时间 */
	private Date preFinishTime;
	/** 状态 */
	private FinancialProjectStatusEnum status;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	/**
	 * 是否过期(主要用来判断是否可转让、赎回、计算持有期收益)
	 * @return
	 */
	public boolean isExipre() {
		boolean isExpire = false;
		if (status == FinancialProjectStatusEnum.EXPIRE) {
			isExpire = true;
		} else
		//不开放不滚动
		if (isRoll == BooleanEnum.NO && isOpen == BooleanEnum.NO
			&& cycleExpireDate.after(new Date())) {
			isExpire = true;
		} else if (preFinishTime != null && preFinishTime.after(new Date())) {
			isExpire = true;
		}
		return isExpire;
	}
	
	/**
	 * 是否过期(主要用来判断是否可转让、赎回、计算持有期收益)
	 * @return
	 */
	public boolean isExipre(Date now) {
		boolean isExpire = false;
		if (status == FinancialProjectStatusEnum.EXPIRE) {
			isExpire = true;
		} else
		//不开放不滚动
		if (isRoll == BooleanEnum.NO && isOpen == BooleanEnum.NO && now.after(cycleExpireDate)) {
			isExpire = true;
		} else if (preFinishTime != null && now.after(preFinishTime)) {
			isExpire = true;
		}
		return isExpire;
	}
	
	/**
	 * 获取到期时间
	 * @return
	 */
	public String getExpireDate() {
		if (isOpen == BooleanEnum.IS) {
			return "-";
		} else {
			return DateUtil.dtSimpleFormat(cycleExpireDate);
		}
	}
	
	/**
	 * 第一轮是否到期(主要用于是否可到期信息维护)
	 * @return
	 */
	public boolean isAfterExipreDate() {
		return new Date().after(actualExpireDate);
	}
	
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
	
	public String getOriginalCode() {
		return this.originalCode;
	}
	
	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
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
	
	public double getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(double buyNum) {
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
	
	public BooleanEnum getIsOpen() {
		return this.isOpen;
	}
	
	public void setIsOpen(BooleanEnum isOpen) {
		this.isOpen = isOpen;
	}
	
	public int getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(int yearDayNum) {
		this.yearDayNum = yearDayNum;
	}
	
	public double getActualBuyNum() {
		return this.actualBuyNum;
	}
	
	public void setActualBuyNum(double actualBuyNum) {
		this.actualBuyNum = actualBuyNum;
	}
	
	public double getActualHoldNum() {
		return this.actualHoldNum;
	}
	
	public void setActualHoldNum(double actualHoldNum) {
		this.actualHoldNum = actualHoldNum;
	}
	
	public double getOriginalHoldNum() {
		return this.originalHoldNum;
	}
	
	public void setOriginalHoldNum(double originalHoldNum) {
		this.originalHoldNum = originalHoldNum;
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
	
	public double getTransferedNum() {
		return this.transferedNum;
	}
	
	public void setTransferedNum(double transferedNum) {
		this.transferedNum = transferedNum;
	}
	
	public double getBuyBackNum() {
		return this.buyBackNum;
	}
	
	public void setBuyBackNum(double buyBackNum) {
		this.buyBackNum = buyBackNum;
	}
	
	public double getRedeemedNum() {
		return this.redeemedNum;
	}
	
	public void setRedeemedNum(double redeemedNum) {
		this.redeemedNum = redeemedNum;
	}
	
	public Date getPreFinishTime() {
		return this.preFinishTime;
	}
	
	public void setPreFinishTime(Date preFinishTime) {
		this.preFinishTime = preFinishTime;
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
	
	public Date getCycleExpireDate() {
		return this.cycleExpireDate;
	}
	
	public void setCycleExpireDate(Date cycleExpireDate) {
		this.cycleExpireDate = cycleExpireDate;
	}
	
	public Money getSettlementAmount() {
		return this.settlementAmount;
	}
	
	public void setSettlementAmount(Money settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
}
