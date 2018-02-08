package com.born.fcs.fm.ws.order.payment;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

public class ExpenseApplicationDetailOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long detailId;

	private long expenseApplicationId;

	private String expenseType;

	private Money amount = new Money(0, 0);

	private Money taxAmount = new Money(0, 0);

	private Money fpAmount = new Money(0, 0);

	private Money xjAmount = new Money(0, 0);
	
	private long deptId;
	
	private String deptName;
	
	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	@Override
	public void check() {
//		validateNotNull(expenseType, "申请明细-费用类型");
//		validateNotNull(deptName, "申请明细-部门");
//		validateNotNull(amount, "申请明细-报销金额");
//		Assert.isTrue(amount.compareTo(Money.zero()) > 0, "申请明细-报销金额大于0");
	}
	
	public Money getFpAmount() {
		return fpAmount;
	}

	public void setFpAmount(Money fpAmount) {
		this.fpAmount = fpAmount;
	}

	public Money getXjAmount() {
		return xjAmount;
	}

	public void setXjAmount(Money xjAmount) {
		this.xjAmount = xjAmount;
	}

	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public long getExpenseApplicationId() {
		return expenseApplicationId;
	}
	
	public void setExpenseApplicationId(long expenseApplicationId) {
		this.expenseApplicationId = expenseApplicationId;
	}

	public String getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}

	public Money getTaxAmount() {
		return taxAmount;
	}
	
	public void setTaxAmount(Money taxAmount) {
		if (taxAmount == null) {
			this.taxAmount = new Money(0, 0);
		} else {
			this.taxAmount = taxAmount;
		}
	}

	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
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
