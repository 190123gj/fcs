/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.notice;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 发行通知书Order
 *
 * @author jil
 *
 */
public class ConsentIssueNoticeQueryOrder extends QueryProjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1784310605719376434L;
	
	private Long noticeId;
	
	private String projectCode;
	
	private String projectName;
	
	private String customerName;
	
	private String isUploadReceipt;
	
	public Long getNoticeId() {
		return noticeId;
	}
	
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getIsUploadReceipt() {
		return isUploadReceipt;
	}
	
	public void setIsUploadReceipt(String isUploadReceipt) {
		this.isUploadReceipt = isUploadReceipt;
	}
	
}
