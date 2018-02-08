/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午1:47:57 创建
 */
package com.born.fcs.fm.ws.order.bank;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class BankMessageBatchOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 1L;
	
	private List<BankMessageOrder> orders;
	
	public List<BankMessageOrder> getOrders() {
		return this.orders;
	}
	
	public void setOrders(List<BankMessageOrder> orders) {
		this.orders = orders;
	}
	
}
