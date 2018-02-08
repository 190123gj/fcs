package com.born.fcs.pm.dataobject;

import java.util.Date;

/**
 * 
 * 解保申请列表
 * 
 * @author lirz
 * 
 * 2016-5-17 下午5:14:29
 */
public class ReleaseApplyDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -401561377909669346L;
	
	private long releaseId; //主键
	private String projectCode;
	private String projectName;
	private long customerId;
	private String customerName;
	private String projectStatus;
	private String phases;
	private long busiManagerId;
	private String busiManagerAccount;
	private String busiManagerName;
	private String busiType;
	private String busiTypeName;
	private Date rightVoucherExtenDate;
	
	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
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
	
	public String getProjectStatus() {
		return projectStatus;
	}
	
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
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

	public Date getRightVoucherExtenDate() {
		return rightVoucherExtenDate;
	}

	public void setRightVoucherExtenDate(Date rightVoucherExtenDate) {
		this.rightVoucherExtenDate = rightVoucherExtenDate;
	}
	
}
