package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 理财项目转让申请查询Order
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectTransferFormQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = -310749886596506210L;
	
	private long applyId;
	
	private String projectCode;
	
	private String productName;
	
	private String formStatus;
	
	private List<String> formStatusList;
	
	private Date transferTimeStart;
	
	private Date transferTimeEnd;
	
	private long createUserId;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
}
