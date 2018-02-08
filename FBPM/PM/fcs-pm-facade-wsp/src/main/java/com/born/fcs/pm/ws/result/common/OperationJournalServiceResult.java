/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.result.common;

import com.born.fcs.pm.ws.info.common.OperationJournalInfo;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * @Filename OperationJournalServiceResult.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author jiajie
 * 
 * @Email hjiajie@yiji.com
 * 
 * @History <li>Author: jiajie</li> <li>Date: 2013-1-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class OperationJournalServiceResult extends FcsBaseResult {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 778793500429870690L;
	
	private OperationJournalInfo operationJournalInfo;
	
	public OperationJournalInfo getOperationJournalInfo() {
		return operationJournalInfo;
	}
	
	public void setOperationJournalInfo(OperationJournalInfo operationJournalInfo) {
		this.operationJournalInfo = operationJournalInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperationJournalServiceResult [operationJournalInfo=");
		builder.append(operationJournalInfo);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
