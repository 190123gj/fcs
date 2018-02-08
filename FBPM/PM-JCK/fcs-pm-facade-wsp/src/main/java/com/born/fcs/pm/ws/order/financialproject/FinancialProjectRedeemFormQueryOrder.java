package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 理财项目赎回申请查询Order
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectRedeemFormQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 3341703956568452529L;
	
	private long applyId;
	
	private String projectCode;
	
	private String productName;
	
	private String formStatus;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private String redeemTimeStart;
	
	private String redeemTimeEnd;
	
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
	
	public String getApplyTimeStart() {
		return this.applyTimeStart;
	}
	
	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	
	public String getApplyTimeEnd() {
		return this.applyTimeEnd;
	}
	
	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public String getRedeemTimeStart() {
		return this.redeemTimeStart;
	}
	
	public void setRedeemTimeStart(String redeemTimeStart) {
		this.redeemTimeStart = redeemTimeStart;
	}
	
	public String getRedeemTimeEnd() {
		return this.redeemTimeEnd;
	}
	
	public void setRedeemTimeEnd(String redeemTimeEnd) {
		this.redeemTimeEnd = redeemTimeEnd;
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
	
}
