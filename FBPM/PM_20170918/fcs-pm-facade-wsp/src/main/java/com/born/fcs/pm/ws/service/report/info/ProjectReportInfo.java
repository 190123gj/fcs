package com.born.fcs.pm.ws.service.report.info;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目相关报表
 * @author wuzj
 */
public class ProjectReportInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6853182918547659562L;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/** 客户经理ID */
	private long busiManagerId;
	/** 客户经理账号 */
	private String busiManagerAccount;
	/** 客户经理名称 */
	private String busiManagerName;
	/** 授信开始时间 */
	private Date startTime;
	/** 授信结束时间 */
	private Date endTime;
	/** 在保余额 */
	private Money balance = new Money(0, 0);
	/** 代偿本金 */
	private Money compPrincipalAmount = new Money(0, 0);
	
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
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Money getBalance() {
		return this.balance;
	}
	
	public void setBalance(Money balance) {
		this.balance = balance;
	}
	
	public Money getCompPrincipalAmount() {
		return this.compPrincipalAmount;
	}
	
	public void setCompPrincipalAmount(Money compPrincipalAmount) {
		this.compPrincipalAmount = compPrincipalAmount;
	}
	
}
