package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.UnderwritingChargeWaytEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目调查 - 承销项目情况
 * 
 * @author lirz
 * 
 * 2016-3-23 下午4:32:35
 */
public class FInvestigationUnderwritingInfo extends InvestigationBaseInfo {
	
	private static final long serialVersionUID = 3881514340638577595L;
	
	private long underwritingId; //主键
	private String projectSource; //项目来源
	private String projectGist; //项目依据
	private Money financingAmount = new Money(0, 0); //本次申请融资金额
	private Date demandDate; //发行人资金需求日期
	private Date setupDate; //立项日期
	private Date issueDate; //预计发行日期
	private Money collectScale; //募集规模
	private int timeLimit; //融资期限
	private TimeUnitEnum timeUnit; //融资单位(年,月,日)
	private double totalCost; //总成本(百分比)
	private double issueRate; //发行利率
	private String hasCredit; //是否增信
	private long exchangeId; //交易所ID
	private String exchangeName; //交易所名称
	private double chargeRate; //收费费率
	private ChargeTypeEnum chargeUnit; //收费费率单位(%/年,单)
	private double lawOfficeRate; //律所费率
	private String lawOfficeUnit; //收费费率单位(%/年,单)
	private double clubRate; //会所费率
	private String clubUnit; //会所费率单位(%/年,单)
	private double otherRate; //其他费用
	private String otherUnit; //其他费率单位(%/年,单)
	private double underwritingRate; //承销费率
	private String underwritingUnit; //承销费率单位(%/年,单)
	private UnderwritingChargeWaytEnum chargeWay; //收费方式
	private Money balance = new Money(0, 0); //发行人扣除费用后实际到账金额
	private String intro; //发行主体简要介绍
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getUnderwritingId() {
		return underwritingId;
	}
	
	public void setUnderwritingId(long underwritingId) {
		this.underwritingId = underwritingId;
	}
	
	public String getProjectSource() {
		return projectSource;
	}
	
	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
	
	public String getProjectGist() {
		return projectGist;
	}
	
	public void setProjectGist(String projectGist) {
		this.projectGist = projectGist;
	}
	
	public Money getFinancingAmount() {
		return financingAmount;
	}

	public void setFinancingAmount(Money financingAmount) {
		this.financingAmount = financingAmount;
	}

	public Date getDemandDate() {
		return demandDate;
	}
	
	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}
	
	public Date getSetupDate() {
		return setupDate;
	}
	
	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public Money getCollectScale() {
		return collectScale;
	}
	
	public void setCollectScale(Money collectScale) {
		this.collectScale = collectScale;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public double getIssueRate() {
		return issueRate;
	}
	
	public void setIssueRate(double issueRate) {
		this.issueRate = issueRate;
	}
	
	public String getHasCredit() {
		return hasCredit;
	}
	
	public void setHasCredit(String hasCredit) {
		this.hasCredit = hasCredit;
	}
	
	public long getExchangeId() {
		return exchangeId;
	}
	
	public void setExchangeId(long exchangeId) {
		this.exchangeId = exchangeId;
	}
	
	public String getExchangeName() {
		return exchangeName;
	}
	
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	
	public double getChargeRate() {
		return chargeRate;
	}
	
	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public ChargeTypeEnum getChargeUnit() {
		return chargeUnit;
	}
	
	public void setChargeUnit(ChargeTypeEnum chargeUnit) {
		this.chargeUnit = chargeUnit;
	}
	
	public double getLawOfficeRate() {
		return lawOfficeRate;
	}
	
	public void setLawOfficeRate(double lawOfficeRate) {
		this.lawOfficeRate = lawOfficeRate;
	}
	
	public double getClubRate() {
		return clubRate;
	}
	
	public void setClubRate(double clubRate) {
		this.clubRate = clubRate;
	}
	
	public double getOtherRate() {
		return otherRate;
	}
	
	public void setOtherRate(double otherRate) {
		this.otherRate = otherRate;
	}
	
	public double getUnderwritingRate() {
		return underwritingRate;
	}
	
	public void setUnderwritingRate(double underwritingRate) {
		this.underwritingRate = underwritingRate;
	}
	
	public UnderwritingChargeWaytEnum getChargeWay() {
		return chargeWay;
	}
	
	public void setChargeWay(UnderwritingChargeWaytEnum chargeWay) {
		this.chargeWay = chargeWay;
	}
	
	public Money getBalance() {
		return balance;
	}
	
	public void setBalance(Money balance) {
		this.balance = balance;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
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

	public String getLawOfficeUnit() {
		return lawOfficeUnit;
	}

	public void setLawOfficeUnit(String lawOfficeUnit) {
		this.lawOfficeUnit = lawOfficeUnit;
	}

	public String getClubUnit() {
		return clubUnit;
	}

	public void setClubUnit(String clubUnit) {
		this.clubUnit = clubUnit;
	}

	public String getOtherUnit() {
		return otherUnit;
	}

	public void setOtherUnit(String otherUnit) {
		this.otherUnit = otherUnit;
	}

	public String getUnderwritingUnit() {
		return underwritingUnit;
	}

	public void setUnderwritingUnit(String underwritingUnit) {
		this.underwritingUnit = underwritingUnit;
	}
	
}
