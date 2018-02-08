/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目合同列表DO
 *
 * @author wuzj
 */
public class FinancialProjectContractFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -2401760813224573991L;
	
	/** 合同ID */
	private long contractId;
	/** 表单ID */
	private long formId;
	/** 项目编号 */
	private String projectCode;
	/** 合同内容 */
	private String contract;
	/** 附件 */
	private String attach;
	/** 合同状态 （待定） */
	private String contractStatus;
	/** 回传文件 */
	private String contractReturn;
	/** 备注 */
	private String remark;
	/** 新增时间 */
	private Date rawAddTime;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	//理财产品部分
	private long productId;
	
	private String productName;
	
	private String issueInstitution;
	
	private double interestRate;
	
	private Date expectBuyDate;
	
	private double expectBuyNum;
	
	private Money price = Money.zero();
	
	//查询部分
	private String sortCol;
	
	private String sortOrder;
	
	private long limitStart;
	
	private long pageSize;
	
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	private List<String> formStatusList;
	
	private long busiManagerId;
	
	private long busiManagerbId;
	
	public long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
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
	
	public String getContract() {
		return this.contract;
	}
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getContractStatus() {
		return this.contractStatus;
	}
	
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public String getContractReturn() {
		return this.contractReturn;
	}
	
	public void setContractReturn(String contractReturn) {
		this.contractReturn = contractReturn;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getFormStatusList() {
		return this.formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getIssueInstitution() {
		return this.issueInstitution;
	}
	
	public void setIssueInstitution(String issueInstitution) {
		this.issueInstitution = issueInstitution;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public Date getExpectBuyDate() {
		return this.expectBuyDate;
	}
	
	public void setExpectBuyDate(Date expectBuyDate) {
		this.expectBuyDate = expectBuyDate;
	}
	
	public double getExpectBuyNum() {
		return this.expectBuyNum;
	}
	
	public void setExpectBuyNum(double expectBuyNum) {
		this.expectBuyNum = expectBuyNum;
	}
	
	public Money getPrice() {
		return this.price;
	}
	
	public void setPrice(Money price) {
		this.price = price;
	}
	
}
