package com.born.fcs.fm.ws.info.receipt;

import java.util.Date;

import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单收费明细
 * @author wuzj
 */
public class FormReceiptFeeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1578867785424552067L;
	
	/** 主键 */
	private long id;
	/** 表单ID */
	private long formId;
	/** 费用类型 */
	private SubjectCostTypeEnum feeType;
	/** 收费金额 */
	private Money amount = new Money(0, 0);
	/** 收款账户 */
	private String account;
	/** 会计科目编号 */
	private String atCode;
	/** 会计科目名称 */
	private String atName;
	/** 收款时间 */
	private Date receiptDate;
	/** 备注 */
	private String remark;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public SubjectCostTypeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(SubjectCostTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAtCode() {
		return atCode;
	}
	
	public void setAtCode(String atCode) {
		this.atCode = atCode;
	}
	
	public String getAtName() {
		return atName;
	}
	
	public void setAtName(String atName) {
		this.atName = atName;
	}
	
	public Date getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
