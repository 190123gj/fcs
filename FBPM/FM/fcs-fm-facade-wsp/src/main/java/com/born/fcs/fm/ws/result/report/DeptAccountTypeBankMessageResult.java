/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午2:18:15 创建
 */
package com.born.fcs.fm.ws.result.report;

import java.util.List;

import com.born.fcs.fm.ws.info.report.DeptAccountTypeBankMessageInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class DeptAccountTypeBankMessageResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private List<DeptAccountTypeBankMessageInfo> list;
	
	public List<DeptAccountTypeBankMessageInfo> getList() {
		return this.list;
	}
	
	public void setList(List<DeptAccountTypeBankMessageInfo> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeptAccountTypeBankMessageResult [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}
	
}
