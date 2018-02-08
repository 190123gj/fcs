package com.born.fcs.fm.ws.order.receipt;

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
public class FormReceiptFeeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4570073688121112165L;
	
	/** 主键 */
	private Long id;
	/** 表单ID */
	private Long formId;
	/** 费用类型 */
	private SubjectCostTypeEnum feeType;
	/** 收费金额 */
	private Money amount = new Money(0, 0);
	/** 收款时间 */
	private Date receiptDate;
	/** 收款账户 */
	private String account;
	/** 会计科目编号 */
	private String atCode;
	/** 会计科目名称 */
	private String atName;
	/** 备注 */
	private String remark;
	
	public void setAmountStr(String amountStr) {
		if (StringUtil.isNotBlank(amountStr)) {
			this.amount = Money.amout(amountStr);
		}
	}
	
	public void setReceiptDateStr(String receiptDateStr) {
		try {
			if (StringUtil.isNotBlank(receiptDateStr)) {
				this.receiptDate = DateUtil.getFormat("yyyy-MM-dd").parse(receiptDateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isNull() {
		return feeType == null && isNull(amount) && isNull(account) && isNull(atCode);
	}
	
	@Override
	public void check() {
		validateNotNull(feeType, "收款种类");
		validateNotNull(amount, "收款金额");
		validateNotNull(account, "收款账户");
		validateNotNull(atCode, "会计科目");
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFormId() {
		return formId;
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
}
