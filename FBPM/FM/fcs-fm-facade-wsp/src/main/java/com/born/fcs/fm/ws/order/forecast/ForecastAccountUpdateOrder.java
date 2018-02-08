/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午4:44:10 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 预测表
 * @author hjiajie
 * 
 */
public class ForecastAccountUpdateOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 3717301922146316004L;

	/** 主键 */
	private long id;
	
	/*** 预计发生时间 */
	private Date forecastStartTime;
	
	/*** 金额 */
	private Money amount = new Money(0, 0);
	
	@Override
	public void check() {
		validateGreaterThan(id, "ID");
		validateNotNull(forecastStartTime, "预计发生时间");
		validateNotNull(amount, "金额");
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public Date getForecastStartTime() {
		return forecastStartTime;
	}
	
	public void setForecastStartTime(Date forecastStartTime) {
		this.forecastStartTime = forecastStartTime;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
}
