/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.common;

import java.util.List;

import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * @Filename EstateTradeAttachmentInfo.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-4-10</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public class CommonAttachmentQueryOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private String bizNo;
	
	private String childId;
	
	private List<CommonAttachmentTypeEnum> moduleTypeList = null;
	
	private String fileName;
	private long isort;
	
	public String getBizNo() {
		return bizNo;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public String getChildId() {
		return this.childId;
	}
	
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public List<CommonAttachmentTypeEnum> getModuleTypeList() {
		return moduleTypeList;
	}
	
	public void setModuleTypeList(List<CommonAttachmentTypeEnum> moduleTypeList) {
		this.moduleTypeList = moduleTypeList;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getIsort() {
		return isort;
	}
	
	public void setIsort(long isort) {
		this.isort = isort;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonAttachmentQueryOrder [bizNo=");
		builder.append(bizNo);
		builder.append(", moduleTypeList=");
		builder.append(moduleTypeList);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public void check() {
	}
	
}
