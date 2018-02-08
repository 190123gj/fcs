/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:44:34 创建
 */
package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.result.base.FormBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class FProjectResult extends FormBaseResult {
	
	private static final long serialVersionUID = 3672081514919780172L;
	
	private FProjectInfo fPrijectInfo;
	
	public FProjectInfo getfPrijectInfo() {
		return this.fPrijectInfo;
	}
	
	public void setfPrijectInfo(FProjectInfo fPrijectInfo) {
		this.fPrijectInfo = fPrijectInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FProjectResult [fPrijectInfo=");
		builder.append(fPrijectInfo);
		builder.append("]");
		return builder.toString();
	}
	
}
