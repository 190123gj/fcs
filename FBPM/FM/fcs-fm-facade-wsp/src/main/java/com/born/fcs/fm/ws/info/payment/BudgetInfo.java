package com.born.fcs.fm.ws.info.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.PeriodEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class BudgetInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long budgetId;

	private Date startTime;

	private Date endTime;
	
	private PeriodEnum period;
	
	private String periodValue;
	
	private long budgetDeptId;

	private String budgetDeptName;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private List<BudgetDetailInfo> detailList;
	
	
    //========== getters and setters ==========
	
	public List<BudgetDetailInfo> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<BudgetDetailInfo> detailList) {
		this.detailList = detailList;
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

	public long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(long budgetId) {
		this.budgetId = budgetId;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
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
