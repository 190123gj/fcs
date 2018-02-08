package com.born.fcs.pm.ws.order.notice;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 发行通知书Order
 * @author jil
 * 
 */
public class ConsentIssueNoticeOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6096930232223149112L;
	
	private Long noticeId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	private String isUploadReceipt;
	
	private String receiptAttachment;
	
	private String yourCooperationCompany;
	
	private String yourCooperationAttachment;
	
	private String myCooperationCompany;
	
	private String myCooperationAttachment;
	
	private String myCooperationContractNo;
	
	private String html;
	
	private Date rawAddTime;
	
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
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public String getIsUploadReceipt() {
		return isUploadReceipt;
	}
	
	public void setIsUploadReceipt(String isUploadReceipt) {
		this.isUploadReceipt = isUploadReceipt;
	}
	
	public String getReceiptAttachment() {
		return receiptAttachment;
	}
	
	public void setReceiptAttachment(String receiptAttachment) {
		this.receiptAttachment = receiptAttachment;
	}
	
	public String getYourCooperationCompany() {
		return yourCooperationCompany;
	}
	
	public void setYourCooperationCompany(String yourCooperationCompany) {
		this.yourCooperationCompany = yourCooperationCompany;
	}
	
	public String getYourCooperationAttachment() {
		return yourCooperationAttachment;
	}
	
	public void setYourCooperationAttachment(String yourCooperationAttachment) {
		this.yourCooperationAttachment = yourCooperationAttachment;
	}
	
	public String getMyCooperationCompany() {
		return myCooperationCompany;
	}
	
	public void setMyCooperationCompany(String myCooperationCompany) {
		this.myCooperationCompany = myCooperationCompany;
	}
	
	public String getMyCooperationAttachment() {
		return myCooperationAttachment;
	}
	
	public void setMyCooperationAttachment(String myCooperationAttachment) {
		this.myCooperationAttachment = myCooperationAttachment;
	}
	
	public String getMyCooperationContractNo() {
		return myCooperationContractNo;
	}
	
	public void setMyCooperationContractNo(String myCooperationContractNo) {
		this.myCooperationContractNo = myCooperationContractNo;
	}
	
	public String getHtml() {
		return html;
	}
	
	public void setHtml(String html) {
		this.html = html;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
}
