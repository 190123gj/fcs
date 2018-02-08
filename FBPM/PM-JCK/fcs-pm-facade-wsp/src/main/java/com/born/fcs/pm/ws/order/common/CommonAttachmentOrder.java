/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.common;

import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.service.Order;

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
public class CommonAttachmentOrder extends ValidateOrderBase implements Order {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 7654210862751673L;
	
	private long attachmentId;
	
	private String bizNo;
	
	private String childId;
	
	private CommonAttachmentTypeEnum moduleType = CommonAttachmentTypeEnum.OTHER;
	
	private String fileName;
	
	private long isort;
	
	private String filePhysicalPath;
	
	private String requestPath;
	
	private long uploaderId;
	private String uploaderAccount;
	private String uploaderName;
	
	public long getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public String getBizNo() {
		return bizNo;
	}
	
	public String getChildId() {
		return this.childId;
	}
	
	public void setChildId(String childId) {
		this.childId = childId;
	}
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	public CommonAttachmentTypeEnum getModuleType() {
		return moduleType;
	}
	
	public void setModuleType(CommonAttachmentTypeEnum moduleType) {
		this.moduleType = moduleType;
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
	
	public String getFilePhysicalPath() {
		return filePhysicalPath;
	}
	
	public void setFilePhysicalPath(String filePhysicalPath) {
		this.filePhysicalPath = filePhysicalPath;
	}
	
	public String getRequestPath() {
		return requestPath;
	}
	
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	public long getUploaderId() {
		return this.uploaderId;
	}
	
	public void setUploaderId(long uploaderId) {
		this.uploaderId = uploaderId;
	}
	
	public String getUploaderAccount() {
		return this.uploaderAccount;
	}
	
	public void setUploaderAccount(String uploaderAccount) {
		this.uploaderAccount = uploaderAccount;
	}
	
	public String getUploaderName() {
		return this.uploaderName;
	}
	
	public void setUploaderName(String uploaderName) {
		this.uploaderName = uploaderName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonAttachmentOrder [attachmentId=");
		builder.append(attachmentId);
		builder.append(", bizNo=");
		builder.append(bizNo);
		builder.append(", moduleType=");
		builder.append(moduleType);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", isort=");
		builder.append(isort);
		builder.append(", filePhysicalPath=");
		builder.append(filePhysicalPath);
		builder.append(", requestPath=");
		builder.append(requestPath);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * 
	 * @see com.yjf.common.service.Order#check()
	 */
	@Override
	public void check() {
		validateHasText(bizNo, "交易流水号");
		validateNotNull(moduleType, "模块类型");
		validateHasText(fileName, "文件名称");
		validateHasZore(isort, "序号");
		validateHasText(filePhysicalPath, "文件路径");
		validateHasText(requestPath, "访问路径");
	}
}
