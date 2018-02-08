package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目计提台帐
 *
 *
 * @author wuzj
 *
 */
public class FinancialProjectWithdrawingInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7761996376389210131L;
	
	private long id;
	
	private String projectCode;
	
	private String projectName;
	
	private long productId;
	
	private String productName;
	
	private double interestRate;
	
	private Date buyDate;
	
	private Money totalInterest = new Money(0, 0);
	
	private String withdrawDate;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private ProjectFinancialInfo project;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
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
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public Date getBuyDate() {
		return this.buyDate;
	}
	
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	public Money getTotalInterest() {
		return this.totalInterest;
	}
	
	public void setTotalInterest(Money totalInterest) {
		this.totalInterest = totalInterest;
	}
	
	public String getWithdrawDate() {
		return this.withdrawDate;
	}
	
	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
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
	
	public ProjectFinancialInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectFinancialInfo project) {
		this.project = project;
	}
	
}
