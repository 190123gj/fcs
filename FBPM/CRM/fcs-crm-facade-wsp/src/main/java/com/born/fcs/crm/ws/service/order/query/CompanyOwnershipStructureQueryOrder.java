package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 公司股权结构查询
 * */
public class CompanyOwnershipStructureQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -8424992369642297650L;
	/** 客户Id： 通用查询Id */
	private String customerId;
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
