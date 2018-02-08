/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:51:25 创建
 */
package com.born.fcs.fm.ws.result.forecast;

import com.born.fcs.fm.ws.info.forecast.SysForecastParamAllInfo;
import com.born.fcs.fm.ws.info.forecast.SysForecastParamInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysForecastParamResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private SysForecastParamInfo paramInfo;
	
	private SysForecastParamAllInfo paramAllInfo;
	
	public SysForecastParamInfo getParamInfo() {
		return this.paramInfo;
	}
	
	public void setParamInfo(SysForecastParamInfo paramInfo) {
		this.paramInfo = paramInfo;
	}
	
	public SysForecastParamAllInfo getParamAllInfo() {
		return this.paramAllInfo;
	}
	
	public void setParamAllInfo(SysForecastParamAllInfo paramAllInfo) {
		this.paramAllInfo = paramAllInfo;
	}
	
}
