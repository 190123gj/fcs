package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class BankCategoryOrder extends ValidateOrderBase {
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long categoryId;

	private String area;

	private String bankCategory;

	private String bankName;

	private String status;

	private Date rawAddTime;

	private Date rawUpdateTime;

	private boolean isDel;
	
	private List<BankCategoryOrder> batchList;
	
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

	public List<BankCategoryOrder> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<BankCategoryOrder> batchList) {
		this.batchList = batchList;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}

	public String getBankCategory() {
		return bankCategory;
	}
	
	public void setBankCategory(String bankCategory) {
		this.bankCategory = bankCategory;
	}

	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
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
