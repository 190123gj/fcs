package com.born.fcs.pm.ws.info.capitalappropriationapply;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请-资金调动信息
 *
 * @author wuzj
 *
 */
public class FCapitalAppropriationApplyTransferInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 5438720974728229348L;
	
	private long id;
	
	private long formId;
	/** 转入银行 */
	private String inAccount;
	/** 转出银行 */
	private String outAccount;
	/** 转出金额 */
	private Money outAmount = new Money(0, 0);
	/** 备注 */
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getInAccount() {
		return this.inAccount;
	}
	
	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}
	
	public String getOutAccount() {
		return this.outAccount;
	}
	
	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	
	public Money getOutAmount() {
		return this.outAmount;
	}
	
	public void setOutAmount(Money outAmount) {
		this.outAmount = outAmount;
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
