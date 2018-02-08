package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

public class BankCategoryQueryCondition extends QueryConditionBaseDO {

	private static final long serialVersionUID = 1L;

	private String area;

	private List<String> statusList;
	
	private List<String> excStatusList;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}

	public List<String> getExcStatusList() {
		return excStatusList;
	}

	public void setExcStatusList(List<String> excStatusList) {
		this.excStatusList = excStatusList;
	}
}
