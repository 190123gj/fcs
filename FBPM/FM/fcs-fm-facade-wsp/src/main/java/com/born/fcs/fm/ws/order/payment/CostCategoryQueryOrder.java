package com.born.fcs.fm.ws.order.payment;

import java.util.List;

import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.pm.ws.base.QueryProjectBase;

public class CostCategoryQueryOrder extends QueryProjectBase {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private List<CostCategoryStatusEnum> statusList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CostCategoryStatusEnum> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CostCategoryStatusEnum> statusList) {
		this.statusList = statusList;
	}
}
