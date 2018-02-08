/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:07:36 创建
 */
package com.born.fcs.fm.ws.info.forecast;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class AccountAmountDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private Date time;
	
	private Money startAmount = new Money(0, 0);
	
	private Money forecastInAmount = new Money(0, 0);
	
	private Money forecastOutAmount = new Money(0, 0);
	
	private Money forecastLastAmount = new Money(0, 0);
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	////// 用于 首页展示
	private String dateStr;
	
	public long getId() {
		return this.id;
	}
	
	public String getDateStr() {
		return this.dateStr;
	}
	
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getTime() {
		return this.time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Money getStartAmount() {
		return this.startAmount;
	}
	
	public void setStartAmount(Money startAmount) {
		this.startAmount = startAmount;
	}
	
	public Money getForecastInAmount() {
		return this.forecastInAmount;
	}
	
	public void setForecastInAmount(Money forecastInAmount) {
		this.forecastInAmount = forecastInAmount;
	}
	
	public Money getForecastOutAmount() {
		return this.forecastOutAmount;
	}
	
	public void setForecastOutAmount(Money forecastOutAmount) {
		this.forecastOutAmount = forecastOutAmount;
	}
	
	public Money getForecastLastAmount() {
		return this.forecastLastAmount;
	}
	
	public void setForecastLastAmount(Money forecastLastAmount) {
		this.forecastLastAmount = forecastLastAmount;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
