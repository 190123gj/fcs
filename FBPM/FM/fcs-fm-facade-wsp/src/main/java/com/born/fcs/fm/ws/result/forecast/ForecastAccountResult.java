/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:08:07 创建
 */
package com.born.fcs.fm.ws.result.forecast;

import com.born.fcs.fm.ws.info.forecast.ForecastAccountInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ForecastAccountResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	private ForecastAccountInfo forecastAccountInfo;
	
	public ForecastAccountInfo getForecastAccountInfo() {
		return this.forecastAccountInfo;
	}
	
	public void setForecastAccountInfo(ForecastAccountInfo forecastAccountInfo) {
		this.forecastAccountInfo = forecastAccountInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ForecastAccountResult [forecastAccountInfo=");
		builder.append(forecastAccountInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
