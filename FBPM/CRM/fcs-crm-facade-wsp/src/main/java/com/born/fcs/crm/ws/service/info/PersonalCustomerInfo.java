package com.born.fcs.crm.ws.service.info;

import java.util.Date;

import com.born.fcs.crm.ws.service.order.PersonalCustomerDetailOrder;

/**
 * 个人客户查询信息
 * */
public class PersonalCustomerInfo extends PersonalCustomerDetailOrder {
	
	private static final long serialVersionUID = -3209444813827741035L;
	
	/** 更新时间 */
	private Date rawUpdateTime;
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
