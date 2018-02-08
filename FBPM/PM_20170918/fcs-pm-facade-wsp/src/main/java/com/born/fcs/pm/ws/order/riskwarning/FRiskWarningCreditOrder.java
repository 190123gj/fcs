package com.born.fcs.pm.ws.order.riskwarning;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 风险预警处理 - 客户 授信业务基本情况
 * 
 * @author lirz
 * 
 * 2016-4-18 上午9:50:39
 */
public class FRiskWarningCreditOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4264604494964782766L;
	
	private long id;
	private long warningId;
	private String projectCode;
	private String deptName;
	private Date issueDate;
	private Date expireDate;
	private String loanAmountStr;
	private String debitInterestStr;
	private BooleanEnum hasRepayPlan;
	private JSONObject jsonObject = null;
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(issueDate) && isNull(expireDate) && isNull(loanAmountStr)
				&& isNull(debitInterestStr) && isNull(projectCode) && isNull(deptName);
	}
	
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
	
	public void setIssueDateStr(String issueDateStr) {
		this.issueDate = DateUtil.strToDtSimpleFormat(issueDateStr);
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public void setExpireDateStr(String expireDateStr) {
		this.expireDate = DateUtil.strToDtSimpleFormat(expireDateStr);
	}
	
	public Money getLoanAmount() {
		if (null == this.loanAmountStr) {
			return new Money(0L);
		}
		return Money.amout(this.loanAmountStr);
	}
	
	public String getLoanAmountStr() {
		return loanAmountStr;
	}
	
	public void setLoanAmountStr(String loanAmountStr) {
		this.loanAmountStr = loanAmountStr;
	}
	
	public Money getDebitInterest() {
		if (null == this.debitInterestStr) {
			return new Money(0L);
		}
		return Money.amout(this.debitInterestStr);
	}
	
	public String getDebitInterestStr() {
		return debitInterestStr;
	}
	
	public void setDebitInterestStr(String debitInterestStr) {
		this.debitInterestStr = debitInterestStr;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
		if (this.jsonObject == null)
			this.jsonObject = new JSONObject();
		return this.jsonObject;
	}
	
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
