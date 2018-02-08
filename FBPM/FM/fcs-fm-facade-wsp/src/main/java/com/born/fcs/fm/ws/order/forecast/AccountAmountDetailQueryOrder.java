/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:08:55 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class AccountAmountDetailQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 1L;
	
	/*** 预计发生时间开始 */
	private Date forecastTimeStart;
	
	/*** 预计发生时间结束 */
	private Date forecastTimeEnd;
	
	public Date getForecastTimeStart() {
		return this.forecastTimeStart;
	}
	
	public void setForecastTimeStart(Date forecastTimeStart) {
		this.forecastTimeStart = forecastTimeStart;
	}
	
	public Date getForecastTimeEnd() {
		return this.forecastTimeEnd;
	}
	
	public void setForecastTimeEnd(Date forecastTimeEnd) {
		this.forecastTimeEnd = forecastTimeEnd;
	}
	
}
