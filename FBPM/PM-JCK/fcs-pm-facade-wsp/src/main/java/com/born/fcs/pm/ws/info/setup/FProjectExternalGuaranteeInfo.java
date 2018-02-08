package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 对外担保情况
 *
 *
 * @author wuzj
 *
 */
public class FProjectExternalGuaranteeInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -7068811973835842096L;
	
	private long id;
	
	private String warrantee;
	
	private String loanBank;
	
	private Money amount = new Money(0, 0);
	
	private Date startTime;
	
	private Date endTime;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
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
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
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
	
}
