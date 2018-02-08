package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.yjf.common.lang.util.money.Money;

/**
 * 开发项目完成情况检查
 * 
 * @author lirz
 *
 * 2016-6-7 下午2:02:16
 */
public class FAfterwardsCheckReportProjectInfo extends FAfterwardsCheckReportProjectSimpleInfo {

	private static final long serialVersionUID = 8811008207952069204L;
	
	private Date openingDate;
	private Date closingDate;
	private Money openingArea;
	private Money receivedAmount = new Money(0, 0);
	private Money receivingAmount = new Money(0, 0);
	private Money salingArea;
	private Money salingAmount = new Money(0, 0);
	private String projectPlan;
	private Money paidLandAmount = new Money(0, 0);
	private Money paidProjectAmount = new Money(0, 0);
	private Money planArea;
	private Money planProjectAmount = new Money(0, 0);
	private Money planInvestAmount = new Money(0, 0);
	private String projectDesc;
	private String delAble;
	private int checkStatus;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

    //========== getters and setters ==========

	public Date getOpeningDate() {
		return openingDate;
	}
	
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}
	
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Money getOpeningArea() {
		return openingArea;
	}
	
	public void setOpeningArea(Money openingArea) {
		this.openingArea = openingArea;
	}

	public Money getReceivedAmount() {
		return receivedAmount;
	}
	
	public void setReceivedAmount(Money receivedAmount) {
		if (receivedAmount == null) {
			this.receivedAmount = new Money(0, 0);
		} else {
			this.receivedAmount = receivedAmount;
		}
	}

	public Money getReceivingAmount() {
		return receivingAmount;
	}
	
	public void setReceivingAmount(Money receivingAmount) {
		if (receivingAmount == null) {
			this.receivingAmount = new Money(0, 0);
		} else {
			this.receivingAmount = receivingAmount;
		}
	}

	public Money getSalingArea() {
		return salingArea;
	}
	
	public void setSalingArea(Money salingArea) {
		this.salingArea = salingArea;
	}

	public Money getSalingAmount() {
		return salingAmount;
	}
	
	public void setSalingAmount(Money salingAmount) {
		if (salingAmount == null) {
			this.salingAmount = new Money(0, 0);
		} else {
			this.salingAmount = salingAmount;
		}
	}

	public String getProjectPlan() {
		return projectPlan;
	}
	
	public void setProjectPlan(String projectPlan) {
		this.projectPlan = projectPlan;
	}

	public Money getPaidLandAmount() {
		return paidLandAmount;
	}
	
	public void setPaidLandAmount(Money paidLandAmount) {
		if (paidLandAmount == null) {
			this.paidLandAmount = new Money(0, 0);
		} else {
			this.paidLandAmount = paidLandAmount;
		}
	}

	public Money getPaidProjectAmount() {
		return paidProjectAmount;
	}
	
	public void setPaidProjectAmount(Money paidProjectAmount) {
		if (paidProjectAmount == null) {
			this.paidProjectAmount = new Money(0, 0);
		} else {
			this.paidProjectAmount = paidProjectAmount;
		}
	}

	public Money getPlanArea() {
		return planArea;
	}
	
	public void setPlanArea(Money planArea) {
		this.planArea = planArea;
	}

	public Money getPlanProjectAmount() {
		return planProjectAmount;
	}
	
	public void setPlanProjectAmount(Money planProjectAmount) {
		if (planProjectAmount == null) {
			this.planProjectAmount = new Money(0, 0);
		} else {
			this.planProjectAmount = planProjectAmount;
		}
	}

	public Money getPlanInvestAmount() {
		return planInvestAmount;
	}
	
	public void setPlanInvestAmount(Money planInvestAmount) {
		if (planInvestAmount == null) {
			this.planInvestAmount = new Money(0, 0);
		} else {
			this.planInvestAmount = planInvestAmount;
		}
	}

	public String getProjectDesc() {
		return projectDesc;
	}
	
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
