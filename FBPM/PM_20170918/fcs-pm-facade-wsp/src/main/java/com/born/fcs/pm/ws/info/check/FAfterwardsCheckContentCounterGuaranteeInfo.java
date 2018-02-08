package com.born.fcs.pm.ws.info.check;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class FAfterwardsCheckContentCounterGuaranteeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4335566759904146578L;
	
	private long cgId;
	
	private String type;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String other;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	List<FAfterwardsCheckContentCounterGuaranteeItemInfo> items;
	
	public long getCgId() {
		return this.cgId;
	}
	
	public void setCgId(long cgId) {
		this.cgId = cgId;
	}
	
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
	
	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<FAfterwardsCheckContentCounterGuaranteeItemInfo> getItems() {
		return this.items;
	}
	
	public void setItems(List<FAfterwardsCheckContentCounterGuaranteeItemInfo> items) {
		this.items = items;
	}
	
}
