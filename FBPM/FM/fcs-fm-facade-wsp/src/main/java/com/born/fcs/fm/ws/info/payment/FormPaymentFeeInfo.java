package com.born.fcs.fm.ws.info.payment;

import java.util.Date;

import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单收费明细
 * @author wuzj
 */
public class FormPaymentFeeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1578867785424552067L;
	
	/** 主键 */
	private long id;
	/** 表单ID */
	private long formId;
	private Long formPaymentId;
	/** 费用类型 */
	private SubjectCostTypeEnum feeType;
	/** 付费金额 */
	private Money amount = new Money(0, 0);
	/** 付款账户 */
	private String paymentAccount;
	/** 收款账户 */
	private String receiptAccount;
	/** 收款人名 */
	private String receiptName;
	
	/** 开户银行 */
	private String bankName;
	/** 会计科目编号 */
	private String atCode;
	/** 会计科目名称 */
	private String atName;
	/** 付款时间 */
	private Date paymentDate;
	/** 备注 */
	private String remark;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	/** 该银行帐号可用余额 **/
	private Money canUseAmount;
	
	public long getId() {
		return id;
	}
	
	public Money getCanUseAmount() {
		return this.canUseAmount;
	}
	
	public void setCanUseAmount(Money canUseAmount) {
		this.canUseAmount = canUseAmount;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public Long getFormPaymentId() {
		return this.formPaymentId;
	}
	
	public String getReceiptName() {
		return this.receiptName;
	}
	
	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public void setFormPaymentId(Long formPaymentId) {
		this.formPaymentId = formPaymentId;
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
	
	public String getPaymentAccount() {
		return this.paymentAccount;
	}
	
	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}
	
	public String getReceiptAccount() {
		return this.receiptAccount;
	}
	
	public void setReceiptAccount(String receiptAccount) {
		this.receiptAccount = receiptAccount;
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
	
	public Date getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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
