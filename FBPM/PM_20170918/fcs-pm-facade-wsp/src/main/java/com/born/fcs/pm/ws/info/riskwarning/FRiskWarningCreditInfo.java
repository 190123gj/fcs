package com.born.fcs.pm.ws.info.riskwarning;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 风险预警处理 - 客户 授信业务基本情况
 * 
 * @author lirz
 * 
 * 2016-4-18 上午9:50:39
 */
public class FRiskWarningCreditInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1576367795495671995L;
	
	private long id;
	private long warningId;
	private String projectCode;
	private String deptName;
	private Date issueDate;
	private Date expireDate;
	private Money loanAmount = new Money(0, 0);
	private Money debitInterest = new Money(0, 0);
	private BooleanEnum hasRepayPlan;
	private int sortOrder;
	private JSONObject jsonObject = null;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getWarningId() {
		return warningId;
	}
	
	public void setWarningId(long warningId) {
		this.warningId = warningId;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public Money getLoanAmount() {
		return loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		if (loanAmount == null) {
			this.loanAmount = new Money(0, 0);
		} else {
			this.loanAmount = loanAmount;
		}
	}
	
	public Money getDebitInterest() {
		return debitInterest;
	}
	
	public void setDebitInterest(Money debitInterest) {
		if (debitInterest == null) {
			this.debitInterest = new Money(0, 0);
		} else {
			this.debitInterest = debitInterest;
		}
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public BooleanEnum getHasRepayPlan() {
		return this.hasRepayPlan;
	}
	
	public void setHasRepayPlan(BooleanEnum hasRepayPlan) {
		this.hasRepayPlan = hasRepayPlan;
	}
	
	public JSONObject getJsonObject() {
		if (jsonObject == null)
			jsonObject = new JSONObject();
		return this.jsonObject;
	}
	
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
}
