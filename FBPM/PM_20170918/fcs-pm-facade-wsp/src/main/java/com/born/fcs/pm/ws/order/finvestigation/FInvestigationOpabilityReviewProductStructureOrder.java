/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:59:50
 */
public class FInvestigationOpabilityReviewProductStructureOrder extends ValidateOrderBase{

	private static final long serialVersionUID = -7218473709854129506L;

	private long id;
	private long opReviewId;
	private String name; //主要产品
	private String reportPeriod1; //报告期1
	private String reportPeriod2; //报告期2
	private String reportPeriod3; //报告期3
	private String income1Str; //收入1
	private String income2Str; //收入2
	private String income3Str; //收入3
	private Double incomeRatio1; //占比1
	private Double incomeRatio2; //占比2
	private Double incomeRatio3; //占比3
	private int sortOrder;

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
		if(isNull(this.income1Str)) {
			return new Money(0L);
		}
		return Money.amout(this.income1Str);
	}
	
	public String getIncome1Str() {
		return income1Str;
	}
	
	public void setIncome1Str(String income1Str) {
		this.income1Str = income1Str;
	}

	public Money getIncome2() {
		if(isNull(this.income2Str)) {
			return new Money(0L);
		}
		return Money.amout(this.income2Str);
	}
	
	public String getIncome2Str() {
		return income2Str;
	}
	
	public void setIncome2Str(String income2Str) {
		this.income2Str = income2Str;
	}

	public Money getIncome3() {
		if(isNull(this.income3Str)) {
			return new Money(0L);
		}
		return Money.amout(this.income3Str);
	}
	
	public String getIncome3Str() {
		return income3Str;
	}
	
	public void setIncome3Str(String income3Str) {
		this.income3Str = income3Str;
	}

	public Double getIncomeRatio1() {
		return incomeRatio1;
	}
	
	public void setIncomeRatio1(Double incomeRatio1) {
		this.incomeRatio1 = incomeRatio1;
	}

	public Double getIncomeRatio2() {
		return incomeRatio2;
	}
	
	public void setIncomeRatio2(Double incomeRatio2) {
		this.incomeRatio2 = incomeRatio2;
	}

	public Double getIncomeRatio3() {
		return incomeRatio3;
	}
	
	public void setIncomeRatio3(Double incomeRatio3) {
		this.incomeRatio3 = incomeRatio3;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
