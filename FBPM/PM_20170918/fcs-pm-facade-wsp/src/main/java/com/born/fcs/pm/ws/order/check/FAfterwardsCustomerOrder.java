package com.born.fcs.pm.ws.order.check;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 保后 - 客户信息
 * 
 * @author lirz
 * 
 * 2016-11-24 下午4:18:01
 * 
 */
public class FAfterwardsCustomerOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1648735089709077807L;
	
	private long id;
	
	private long formId;
	
	private long customerId;
	
	private String customerName;
	
	private String formData;
	
	private String editHtml;
	
	private String viewHtml;
	
	private String status;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String userIp;
	
	private long deptId;
	
	private String deptName;
	
	private String sessionId;
	
	private String accessToken;
	
	private String exeResult;
	
	//========== getters and setters ==========
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public String getFormData() {
		return this.formData;
	}
	
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getEditHtml() {
		return this.editHtml;
	}
	
	public void setEditHtml(String editHtml) {
		this.editHtml = editHtml;
	}
	
	public String getViewHtml() {
		return this.viewHtml;
	}
	
	public void setViewHtml(String viewHtml) {
		this.viewHtml = viewHtml;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return this.userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserIp() {
		return this.userIp;
	}
	
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getSessionId() {
		return this.sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getExeResult() {
		return this.exeResult;
	}
	
	public void setExeResult(String exeResult) {
		this.exeResult = exeResult;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FAfterwardsCustomerInfo[")
		  .append("id=").append(id)
		  .append(",formId=").append(formId)
		  .append(",customerId=").append(customerId)
		  .append(",customerName=").append(customerName)
		  .append(",status=").append(status)
		  .append("]")
		  ;
		return sb.toString();
	}
}
