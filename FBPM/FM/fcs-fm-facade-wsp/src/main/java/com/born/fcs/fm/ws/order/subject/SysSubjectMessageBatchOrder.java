/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.ws.order.subject;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 默认科目信息维护列改
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysSubjectMessageBatchOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	List<SysSubjectMessageOrder> subjectOrders;
	
	//========== getters and setters ==========
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public List<SysSubjectMessageOrder> getSubjectOrders() {
		return this.subjectOrders;
	}
	
	public void setSubjectOrders(List<SysSubjectMessageOrder> subjectOrders) {
		this.subjectOrders = subjectOrders;
	}
}
