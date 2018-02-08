package com.born.fcs.pm.ws.order.council;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

public class FCouncilApplyCreditOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -8295897319114602970L;
	
	private long id; //主键ID
	private long handleId; //上会申报ID
	private String projectCode;
	private String projectName;
	private String deptName;
	private Money creditAmount = new Money(0, 0);//授信金额
	private String guaranteeRate;//担保费率
	private Money bulgariaBalance = new Money(0, 0);// 当前在保余额
	private String fundingSources;//资金渠道
	private Date issueDate; //授信开始时间
	private Date expireDate; //授信到期时间
	private Money loanAmount = new Money(0, 0); //应还款额
	private Money debitInterest = new Money(0, 0); //欠息/费金额
	private JSONObject jsonData;
	private int sortOrder;
	
	public boolean isNull() {
		return null == issueDate && null == expireDate && null == loanAmount
				&& null == debitInterest;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getHandleId() {
		return handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
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
		return loanAmount;
	}
	
	public void setLoanAmount(Money loanAmount) {
		if (loanAmount == null) {
			this.loanAmount = new Money(0, 0);
		} else {
			this.loanAmount = loanAmount;
		}
	}
	
	public void setLoanAmountStr(String loanAmountStr) {
		if (!isNull(loanAmountStr)) {
			this.loanAmount = Money.amout(loanAmountStr);
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
	
	public void setDebitInterestStr(String debitInterestStr) {
		if (!isNull(debitInterestStr)) {
			this.debitInterest = Money.amout(debitInterestStr);
		}
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
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Money getCreditAmount() {
		return this.creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	public String getGuaranteeRate() {
		return this.guaranteeRate;
	}
	
	public void setGuaranteeRate(String guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}
	
	public Money getBulgariaBalance() {
		return this.bulgariaBalance;
	}
	
	public void setBulgariaBalance(Money bulgariaBalance) {
		if (bulgariaBalance == null) {
			this.bulgariaBalance = new Money(0, 0);
		} else {
			this.bulgariaBalance = bulgariaBalance;
		}
	}
	
	public void setBulgariaBalanceStr(String bulgariaBalanceStr) {
		if (!isNull(bulgariaBalanceStr)) {
			this.bulgariaBalance = Money.amout(bulgariaBalanceStr);
		}
	}
	
	public String getFundingSources() {
		return this.fundingSources;
	}
	
	public void setFundingSources(String fundingSources) {
		this.fundingSources = fundingSources;
	}
	
	public JSONObject getJsonData() {
		if (this.jsonData == null)
			this.jsonData = new JSONObject();
		return this.jsonData;
	}
	
	public void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
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
