package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.born.fcs.fm.ws.enums.PeriodEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class BudgetOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long budgetId;

	private String startTime;

	private String endTime;
	
	private PeriodEnum period;
	private String periodValue;
	private long budgetDeptId;

	private String budgetDeptName;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private List<BudgetDetailOrder> detailList;
	
	@Override
	public void check() {
//		validateNotNull(startTime, "预算开始时间");
//		validateNotNull(endTime, "预算结束时间");
		validateNotNull(period, "预算周期");
		validateNotNull(periodValue, "预算周期");
		Assert.isTrue(budgetDeptId > 0, "部门不能为空");
//		Assert.isTrue(startTime.compareTo(endTime)<=0, "预算开始时间不能大于预算结束时间");
	}
	
    //========== getters and setters ==========
	
	public long getBudgetId() {
		return budgetId;
	}

	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}

	public PeriodEnum getPeriod() {
		return period;
	}

	public void setPeriod(PeriodEnum period) {
		this.period = period;
	}

	public List<BudgetDetailOrder> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<BudgetDetailOrder> detailList) {
		this.detailList = detailList;
	}

	public void setBudgetId(long budgetId) {
		this.budgetId = budgetId;
	}

	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getBudgetDeptId() {
		return budgetDeptId;
	}
	
	public void setBudgetDeptId(long budgetDeptId) {
		this.budgetDeptId = budgetDeptId;
	}

	public String getBudgetDeptName() {
		return budgetDeptName;
	}
	
	public void setBudgetDeptName(String budgetDeptName) {
		this.budgetDeptName = budgetDeptName;
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
