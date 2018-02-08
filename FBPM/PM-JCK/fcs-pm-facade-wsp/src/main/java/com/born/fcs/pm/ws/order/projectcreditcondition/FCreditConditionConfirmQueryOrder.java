package com.born.fcs.pm.ws.order.projectcreditcondition;

import java.util.Date;

import com.born.fcs.pm.ws.base.QueryProjectBase;

/**
 * 授信条件落实情况查询Order
 * @author Ji
 */
public class FCreditConditionConfirmQueryOrder extends QueryProjectBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8286957808388865048L;
	
	private Long confirmId;
	
	private String projectCode;
	
	private String projectName;
	
	private String contractNo;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private String institutionName;
	private String isMargin;
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	/**
	 * 客户经理
	 */
	private long busiManagerId;
	
	private String busiManagerName;
	
	private Date submitTimeStart;
	
	private Date submitTimeEnd;
	
	public Long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(Long confirmId) {
		this.confirmId = confirmId;
	}
	
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
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(String customerType) {
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
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public long getBusiManagerId() {
		return busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerName() {
		return busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public Date getSubmitTimeStart() {
		return submitTimeStart;
	}
	
	public void setSubmitTimeStart(Date submitTimeStart) {
		this.submitTimeStart = submitTimeStart;
	}
	
	public Date getSubmitTimeEnd() {
		return submitTimeEnd;
	}
	
	public void setSubmitTimeEnd(Date submitTimeEnd) {
		this.submitTimeEnd = submitTimeEnd;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getIsMargin() {
		return isMargin;
	}
	
	public void setIsMargin(String isMargin) {
		this.isMargin = isMargin;
	}
	
}
