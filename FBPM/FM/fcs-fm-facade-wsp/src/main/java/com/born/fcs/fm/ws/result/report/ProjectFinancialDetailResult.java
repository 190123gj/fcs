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

import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectFinancialDetailResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private List<ProjectFinancialInfo> list;
	
	public List<ProjectFinancialInfo> getList() {
		return list;
	}
	
	public void setList(List<ProjectFinancialInfo> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectFinancialDetailResult [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}
	
}
