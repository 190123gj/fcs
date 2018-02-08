package com.born.fcs.pm.ws.info.common;

import com.yjf.common.lang.util.money.Money;

/**
 * 表单项目基本信息
 * 
 * @author lirz
 * 
 * 2016-3-10 下午4:04:12
 */
public class SimpleFormProjectInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1112364658856067739L;
	
	/** 表单ID */
	protected long formId;
	/** 项目编号 */
	protected String projectCode;
	/** 项目名称 */
	protected String projectName;
	/** 客户ID */
	protected long customerId;
	/** 客户名称 */
	protected String customerName;
	/** 金额 */
	protected Money amount = new Money(0, 0);
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	
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
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public String getAmountCNString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toCNString();
	}
	
	public String getAmountStandardString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toStandardString();
	}
	
	public String getAmountTenThousandString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return getMoneyByw2(money);
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
}
