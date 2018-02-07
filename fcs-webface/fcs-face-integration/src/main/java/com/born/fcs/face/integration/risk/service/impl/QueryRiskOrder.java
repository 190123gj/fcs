package com.born.fcs.face.integration.risk.service.impl;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class QueryRiskOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -5505322903865890923L;
	/** 当前登陆人 */
	private String operator;
	/** 客户名 */
	private String customName;
	/** 客户证件号 */
	private String licenseNo;
	
	@Override
	public void check() {
		validateHasText(operator, "操作人");
		validateHasText(customName, "客户名");
		validateHasText(licenseNo, "证件号");
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public String getLicenseNo() {
		return this.licenseNo;
	}
	
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
}
