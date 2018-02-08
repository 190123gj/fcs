/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:43:54 创建
 */
package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class CouncilDelOrder extends ProcessOrder {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CouncilDelOrder [councilIds=");
		builder.append(councilIds);
		builder.append("]");
		return builder.toString();
	}
	
	private static final long serialVersionUID = 1L;
	
	private List<Long> councilIds;
	
	public List<Long> getCouncilIds() {
		return this.councilIds;
	}
	
	public void setCouncilIds(List<Long> councilIds) {
		this.councilIds = councilIds;
	}
	
}
