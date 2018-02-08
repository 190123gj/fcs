/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:59:50
 */
public class FInvestigationOpabilityReviewProductStructureInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 5824371622649856977L;
	
	private long id;
	private long opReviewId;
	private String name; //主要产品
	private String reportPeriod1; //报告期1
	private String reportPeriod2; //报告期2
	private String reportPeriod3; //报告期3
	private Money income1 = new Money(0, 0); //收入1
	private Money income2 = new Money(0, 0); //收入2
	private Money income3 = new Money(0, 0); //收入3
	private double incomeRatio1; //占比1
	private double incomeRatio2; //占比2
	private double incomeRatio3; //占比3
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getOpReviewId() {
		return opReviewId;
	}
	
	public void setOpReviewId(long opReviewId) {
		this.opReviewId = opReviewId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getReportPeriod1() {
		return reportPeriod1;
	}
	
	public void setReportPeriod1(String reportPeriod1) {
		this.reportPeriod1 = reportPeriod1;
	}

	public String getReportPeriod2() {
		return reportPeriod2;
	}
	
	public void setReportPeriod2(String reportPeriod2) {
		this.reportPeriod2 = reportPeriod2;
	}

	public String getReportPeriod3() {
		return reportPeriod3;
	}
	
	public void setReportPeriod3(String reportPeriod3) {
		this.reportPeriod3 = reportPeriod3;
	}

	public Money getIncome1() {
		return income1;
	}
	
	public void setIncome1(Money income1) {
		if (income1 == null) {
			this.income1 = new Money(0, 0);
		} else {
			this.income1 = income1;
		}
	}

	public Money getIncome2() {
		return income2;
	}
	
	public void setIncome2(Money income2) {
		if (income2 == null) {
			this.income2 = new Money(0, 0);
		} else {
			this.income2 = income2;
		}
	}

	public Money getIncome3() {
		return income3;
	}
	
	public void setIncome3(Money income3) {
		if (income3 == null) {
			this.income3 = new Money(0, 0);
		} else {
			this.income3 = income3;
		}
	}

	public double getIncomeRatio1() {
		return incomeRatio1;
	}
	
	public void setIncomeRatio1(double incomeRatio1) {
		this.incomeRatio1 = incomeRatio1;
	}

	public double getIncomeRatio2() {
		return incomeRatio2;
	}
	
	public void setIncomeRatio2(double incomeRatio2) {
		this.incomeRatio2 = incomeRatio2;
	}

	public double getIncomeRatio3() {
		return incomeRatio3;
	}
	
	public void setIncomeRatio3(double incomeRatio3) {
		this.incomeRatio3 = incomeRatio3;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

}
