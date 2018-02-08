/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:34:20 创建
 */
package com.born.fcs.fm.ws.order.payment;

import com.born.fcs.fm.ws.enums.ReportCompanyEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ReportAccountDetailOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 1L;
	
	ReportCompanyEnum company;
	
	public ReportCompanyEnum getCompany() {
		return this.company;
	}
	
	public void setCompany(ReportCompanyEnum company) {
		this.company = company;
	}
	
}
