package com.born.fcs.pm.ws.order.financialproject;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

/**
 * 理财项目计提台帐查询
 *
 * @author wuzj
 */
public class FinancialProjectWithdrawingQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 8654104652676761730L;
	
	private String projectCode;
	
	private String withdrawDate;
	
	private long createUserId;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getWithdrawDate() {
		return this.withdrawDate;
	}
	
	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
	}
	
	public long getCreateUserId() {
		return this.createUserId;
	}
	
	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}
	
}
