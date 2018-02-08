package com.born.fcs.pm.ws.order.setup;

import java.util.Date;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目立项 - 对外担保情况Order
 *
 * @author wuzj
 *
 */
public class FProjectExternalGuaranteeOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = -765801459757563838L;
	
	private Long id;
	
	private String warrantee;
	
	private String loanBank;
	
	private Money amount = Money.zero();
	
	private String amountStr;
	
	private Date startTime;
	
	private String startTimeStr;
	
	private Date endTime;
	
	private String endTimeStr;
	
	private String remark;
	
	public boolean isNull() {
		return isNull(warrantee) && isNull(loanBank) && isNull(amount) && isNull(startTimeStr)
				&& isNull(endTimeStr) && isNull(remark);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFormId() {
		return this.formId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public String getWarrantee() {
		return this.warrantee;
	}
	
	public void setWarrantee(String warrantee) {
		this.warrantee = warrantee;
	}
	
	public String getLoanBank() {
		return this.loanBank;
	}
	
	public void setLoanBank(String loanBank) {
		this.loanBank = loanBank;
	}
	
	public Money getAmount() {
		if (StringUtil.isNotBlank(amountStr))
			return Money.amout(amountStr);
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getAmountStr() {
		return this.amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTimeStr() {
		return this.startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTimeStr() {
		return this.endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
