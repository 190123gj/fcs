package com.born.fcs.pm.ws.info.projectissueinformation;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.SellStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 承销/发债类项目发行信息
 *
 * @author Ji
 *
 */
public class ProjectIssueInformationInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5614132958013517768L;
	//========== properties ==========
	
	private long id;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private CustomerTypeEnum customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private Money amount = new Money(0, 0);
	
	private long institutionId;
	
	private String institutionName;
	
	private double issueRate;
	
	private String projectGist;
	
	private String bondCode;
	
	private String letterUrl;
	
	private String voucherUrl;
	
	private Money actualAmount = new Money(0, 0);
	
	private Date issueDate;
	
	private Date expireTime;
	
	private BooleanEnum isContinue;
	
	private SellStatusEnum status;
	
	private BooleanEnum isReinsurance;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	/**
	 * 发债类项目-分保信息info
	 */
	private List<ProjectBondReinsuranceInformationInfo> listBondReinsuranceInfo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public long getInstitutionId() {
		return institutionId;
	}
	
	public void setInstitutionId(long institutionId) {
		this.institutionId = institutionId;
	}
	
	public String getInstitutionName() {
		return institutionName;
	}
	
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	
	public double getIssueRate() {
		return issueRate;
	}
	
	public void setIssueRate(double issueRate) {
		this.issueRate = issueRate;
	}
	
	public String getProjectGist() {
		return projectGist;
	}
	
	public void setProjectGist(String projectGist) {
		this.projectGist = projectGist;
	}
	
	public String getBondCode() {
		return bondCode;
	}
	
	public void setBondCode(String bondCode) {
		this.bondCode = bondCode;
	}
	
	public String getLetterUrl() {
		return letterUrl;
	}
	
	public void setLetterUrl(String letterUrl) {
		this.letterUrl = letterUrl;
	}
	
	public String getVoucherUrl() {
		return voucherUrl;
	}
	
	public void setVoucherUrl(String voucherUrl) {
		this.voucherUrl = voucherUrl;
	}
	
	public Money getActualAmount() {
		return actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		if (actualAmount == null) {
			this.actualAmount = new Money(0, 0);
		} else {
			this.actualAmount = actualAmount;
		}
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public Date getExpireTime() {
		return expireTime;
	}
	
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public BooleanEnum getIsContinue() {
		return isContinue;
	}
	
	public void setIsContinue(BooleanEnum isContinue) {
		this.isContinue = isContinue;
	}
	
	public SellStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(SellStatusEnum status) {
		this.status = status;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public List<ProjectBondReinsuranceInformationInfo> getListBondReinsuranceInfo() {
		return listBondReinsuranceInfo;
	}
	
	public void setListBondReinsuranceInfo(	List<ProjectBondReinsuranceInformationInfo> listBondReinsuranceInfo) {
		this.listBondReinsuranceInfo = listBondReinsuranceInfo;
	}
	
	public BooleanEnum getIsReinsurance() {
		return isReinsurance;
	}
	
	public void setIsReinsurance(BooleanEnum isReinsurance) {
		this.isReinsurance = isReinsurance;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
