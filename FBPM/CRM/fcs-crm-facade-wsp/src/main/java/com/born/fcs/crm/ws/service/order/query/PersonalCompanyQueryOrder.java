package com.born.fcs.crm.ws.service.order.query;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 个人客户：公司查询 Order
 * **/
public class PersonalCompanyQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7693545569011957341L;
	/** 客户Id： 通用查询Id */
	private String customerId;
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
