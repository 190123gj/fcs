package com.born.fcs.pm.dataobject;

import java.util.Date;

import com.born.fcs.pm.dal.dataobject.ProjectFinancialTradeTansferDO;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 理财转让待计提DO
 * 
 */
public class ProjectGenWithdrawFinancialTransferDO extends ProjectFinancialTradeTansferDO {
	
	private static final long serialVersionUID = -533664697688244639L;
	
	private long productId;
	
	private String productName;
	
	private String projectCode;
	
	private Date preFinishTime;
	
	private Date actualExpireDate;
	
	private Date cycleExpireDate;
	
	private Money actualPrice = Money.zero();
	
	private String isOpen;
	
	private String isRoll;
	
	private int yearDayNum;
	
	public long getProductId() {
		return this.productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Date getActualExpireDate() {
		return this.actualExpireDate;
	}
	
	public void setActualExpireDate(Date actualExpireDate) {
		this.actualExpireDate = actualExpireDate;
	}
	
	public Money getActualPrice() {
		return this.actualPrice;
	}
	
	public void setActualPrice(Money actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	public int getYearDayNum() {
		return this.yearDayNum;
	}
	
	public void setYearDayNum(int yearDayNum) {
		this.yearDayNum = yearDayNum;
	}
	
	public Date getCycleExpireDate() {
		return this.cycleExpireDate;
	}
	
	public void setCycleExpireDate(Date cycleExpireDate) {
		this.cycleExpireDate = cycleExpireDate;
	}
	
	public String getIsOpen() {
		return this.isOpen;
	}
	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	public String getIsRoll() {
		return this.isRoll;
	}
	
	public void setIsRoll(String isRoll) {
		this.isRoll = isRoll;
	}
	
	public Date getPreFinishTime() {
		return this.preFinishTime;
	}
	
	public void setPreFinishTime(Date preFinishTime) {
		this.preFinishTime = preFinishTime;
	}
	
}
