/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:51:05 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysForecastParamBatchOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 1L;
	
	private List<SysForecastParamOrder> paramOrders;
	
	public List<SysForecastParamOrder> getParamOrders() {
		return this.paramOrders;
	}
	
	public void setParamOrders(List<SysForecastParamOrder> paramOrders) {
		this.paramOrders = paramOrders;
	}
	
}
