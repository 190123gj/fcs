package com.born.fcs.fm.ws.order.payment;

import java.util.Date;

import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单收费明细Order
 * @author wuzj
 */
public class FormPaymentFeeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4570073688121112165L;
	
	/** 主键 */
	private Long id;
	/** 表单ID */
	private Long formId;
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
	
	public void setAmountStr(String amountStr) {
		if (StringUtil.isNotBlank(amountStr)) {
			this.amount = Money.amout(amountStr);
		}
	}
	
	public void setPaymentDateStr(String paymentDateStr) {
		try {
			if (StringUtil.isNotBlank(paymentDateStr)) {
				this.paymentDate = DateUtil.getFormat("yyyy-MM-dd").parse(paymentDateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNull() {
		return feeType == null && isNull(amount) && isNull(paymentAccount)
				&& isNull(receiptAccount) && isNull(atCode);
	}
	
	@Override
	public void check() {
		validateNotNull(feeType, "付款种类");
		validateNotNull(amount, "付款金额");
		validateNotNull(paymentAccount, "付款账户");
		validateNotNull(receiptAccount, "收款账户");
		validateNotNull(atCode, "会计科目");
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Long getFormId() {
		return formId;
	}
	
	public Long getFormPaymentId() {
		return this.formPaymentId;
	}
	
	public void setFormPaymentId(Long formPaymentId) {
		this.formPaymentId = formPaymentId;
	}
	
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	public SubjectCostTypeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(SubjectCostTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getAmount() {
		return this.amount;
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
}
