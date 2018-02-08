package com.born.fcs.fm.ws.order.payment;

import java.util.List;

import com.born.fcs.fm.ws.enums.PeriodEnum;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

public class BudgetQueryOrder extends QueryPermissionPageBase {

	private static final long serialVersionUID = 1L;
	
	private String budgetDeptId;
	private String startTime;
	private String endTime;
	private String period;
	private String periodValue;
	private boolean isDetail;
	private List<String> budgetDeptList;
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriodValue() {
		return periodValue;
	}
	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}
	public String getBudgetDeptId() {
		return budgetDeptId;
	}
	public void setBudgetDeptId(String budgetDeptId) {
		this.budgetDeptId = budgetDeptId;
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
	public List<String> getBudgetDeptList() {
		return budgetDeptList;
	}
	public void setBudgetDeptList(List<String> budgetDeptList) {
		this.budgetDeptList = budgetDeptList;
	}
	public boolean isDetail() {
		return isDetail;
	}
	public void setDetail(boolean isDetail) {
		this.isDetail = isDetail;
	}
	
}
