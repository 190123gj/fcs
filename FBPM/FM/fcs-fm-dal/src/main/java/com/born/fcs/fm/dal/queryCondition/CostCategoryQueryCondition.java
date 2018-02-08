package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

public class CostCategoryQueryCondition extends QueryConditionBaseDO{

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> statusList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
}
