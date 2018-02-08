package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class CostCategoryOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long categoryId;

	private String name;

	private String accountCode;

	private String accountName;

	private BooleanEnum used;

	private CostCategoryStatusEnum status;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private boolean isDel;
	
	private List<CostCategoryOrder> batchList;
	
    //========== getters and setters ==========

	public long getCategoryId() {
		return categoryId;
	}
	
	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public List<CostCategoryOrder> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<CostCategoryOrder> batchList) {
		this.batchList = batchList;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public BooleanEnum getUsed() {
		return used;
	}

	public void setUsed(BooleanEnum used) {
		this.used = used;
	}

	public CostCategoryStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CostCategoryStatusEnum status) {
		this.status = status;
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
