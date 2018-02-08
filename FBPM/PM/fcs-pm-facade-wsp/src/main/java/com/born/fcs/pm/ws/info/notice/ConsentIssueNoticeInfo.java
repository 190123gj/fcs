package com.born.fcs.pm.ws.info.notice;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.common.ProjectInfo;

/**
 * 发行通知书info
 * @author jil
 * 
 */
public class ConsentIssueNoticeInfo extends ProjectInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4731339529474842037L;
	
	private long noticeId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private CustomerTypeEnum customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	private BooleanEnum isUploadReceipt;
	
	private String receiptAttachment;
	
	private String yourCooperationCompany;
	
	private String yourCooperationAttachment;
	
	private String myCooperationCompany;
	
	private String myCooperationAttachment;
	
	private String myCooperationContractNo;
	
	private String html;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getNoticeId() {
		return noticeId;
	}
	
	public void setNoticeId(long noticeId) {
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
	
	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
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
	
	public BooleanEnum getIsUploadReceipt() {
		return isUploadReceipt;
	}
	
	public void setIsUploadReceipt(BooleanEnum isUploadReceipt) {
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
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
