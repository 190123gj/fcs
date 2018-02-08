package com.born.fcs.crm.ws.service.info;

import java.util.Date;

import com.born.fcs.crm.ws.service.order.CompanyCustomerDetailOrder;

/**
 * 公司客户详情
 * **/
public class CompanyCustomerInfo extends CompanyCustomerDetailOrder {
	
	private static final long serialVersionUID = -6123039384231479318L;
	
	/** 更新时间 */
	private Date rawUpdateTime;
	/** 去年评级 */
	private String lastLevel;
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getLastLevel() {
		return this.lastLevel;
	}
	
	public void setLastLevel(String lastLevel) {
		this.lastLevel = lastLevel;
	}
}
