package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.ChargeBasisEnum;

public class FChargeNotificationQueryOrder extends QueryProjectBase {
	
	private static final long serialVersionUID = -4182907878975682935L;
	
	private long notificationId;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String formStatus;
	
	private Date timeBegin;
	
	private Date timeEnd;
	//是否查询收费明细
	private boolean queryFeeList;

	private String sortOrder;

	private String sortCol;

	private ChargeBasisEnum chargeBasis;

	public ChargeBasisEnum getChargeBasis() {
		return chargeBasis;
	}

	public void setChargeBasis(ChargeBasisEnum chargeBasis) {
		this.chargeBasis = chargeBasis;
	}

	@Override
	public String getSortOrder() {
		return sortOrder;
	}

	@Override
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String getSortCol() {
		return sortCol;
	}

	@Override
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Date getTimeBegin() {
		return this.timeBegin;
	}
	
	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}
	
	public Date getTimeEnd() {
		return this.timeEnd;
	}
	
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getFormStatus() {
		return formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public boolean isQueryFeeList() {
		return this.queryFeeList;
	}
	
	public void setQueryFeeList(boolean queryFeeList) {
		this.queryFeeList = queryFeeList;
	}
}
