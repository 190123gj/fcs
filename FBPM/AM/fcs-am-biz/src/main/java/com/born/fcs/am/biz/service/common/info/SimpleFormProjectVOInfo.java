package com.born.fcs.am.biz.service.common.info;

import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 对应SimpleFormProjectDO.java
 * 
 * @author lirz
 * 
 *         2016-5-19 下午2:24:57
 */
public class SimpleFormProjectVOInfo extends FormVOInfo {

	private static final long serialVersionUID = -790880715478507719L;

	private String projectCode;

	private String projectName;

	private long customerId;

	private String customerName;

	private CustomerTypeEnum customerType;

	private String busiType;

	private String busiTypeName;

	private Money amount = new Money(0, 0);

	private int timeLimit;

	private TimeUnitEnum timeUnit;

	private ProjectStatusEnum projectStatus;

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

	public CustomerTypeEnum getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}

	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public TimeUnitEnum getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}

	public ProjectStatusEnum getProjectStatus() {
		return this.projectStatus;
	}

	public void setProjectStatus(ProjectStatusEnum projectStatus) {
		this.projectStatus = projectStatus;
	}
}
