package com.born.fcs.pm.ws.order.check;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryPageBase;

public class FAfterwardsCheckQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4501454134307919849L;
	
	private long id;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private Date checkDate;
	
	private int roundYear;
	
	private int roundTime;
	
	private String edition;
	
	private String isLatest;
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public Date getCheckDate() {
		return this.checkDate;
	}
	
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public int getRoundYear() {
		return roundYear;
	}

	public void setRoundYear(int roundYear) {
		this.roundYear = roundYear;
	}

	public int getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getIsLatest() {
		return this.isLatest;
	}

	public void setIsLatest(String isLatest) {
		this.isLatest = isLatest;
	}
	
}
