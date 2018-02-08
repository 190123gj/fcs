package com.born.fcs.fm.ws.order.payment;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

public class ExpenseCxDetailOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long detailId;

	private long expenseApplicationId;

	private long fromApplicationId;

	private long fromDetailId;

	private long categoryId;

	private String category;

	private String accountCode;

	private String accountName;

	private String bank;

	private String bankAccount;

	private Money bxAmount = new Money(0, 0);

	private Money fyAmount = new Money(0, 0);

	private Money fpAmount = new Money(0, 0);

	private Money xjAmount = new Money(0, 0);

	private String fromType;

	private String def1;

	private String def2;

	private String def3;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}

	public long getExpenseApplicationId() {
		return expenseApplicationId;
	}
	
	public void setExpenseApplicationId(long expenseApplicationId) {
		this.expenseApplicationId = expenseApplicationId;
	}

	public long getFromApplicationId() {
		return fromApplicationId;
	}
	
	public void setFromApplicationId(long fromApplicationId) {
		this.fromApplicationId = fromApplicationId;
	}

	public long getFromDetailId() {
		return fromDetailId;
	}
	
	public void setFromDetailId(long fromDetailId) {
		this.fromDetailId = fromDetailId;
	}

	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getAccountCode() {
		return accountCode;
	}
	
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Money getBxAmount() {
		return bxAmount;
	}
	
	public void setBxAmount(Money bxAmount) {
		if (bxAmount == null) {
			this.bxAmount = new Money(0, 0);
		} else {
			this.bxAmount = bxAmount;
		}
	}

	public Money getFyAmount() {
		return fyAmount;
	}
	
	public void setFyAmount(Money fyAmount) {
		if (fyAmount == null) {
			this.fyAmount = new Money(0, 0);
		} else {
			this.fyAmount = fyAmount;
		}
	}

	public Money getFpAmount() {
		return fpAmount;
	}
	
	public void setFpAmount(Money fpAmount) {
		if (fpAmount == null) {
			this.fpAmount = new Money(0, 0);
		} else {
			this.fpAmount = fpAmount;
		}
	}

	public Money getXjAmount() {
		return xjAmount;
	}
	
	public void setXjAmount(Money xjAmount) {
		if (xjAmount == null) {
			this.xjAmount = new Money(0, 0);
		} else {
			this.xjAmount = xjAmount;
		}
	}

	public String getFromType() {
		return fromType;
	}
	
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getDef1() {
		return def1;
	}
	
	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef2() {
		return def2;
	}
	
	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getDef3() {
		return def3;
	}
	
	public void setDef3(String def3) {
		this.def3 = def3;
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
