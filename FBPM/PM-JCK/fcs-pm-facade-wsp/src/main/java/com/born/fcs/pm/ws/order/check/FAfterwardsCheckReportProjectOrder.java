package com.born.fcs.pm.ws.order.check;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 开发项目完成情况检查
 * 
 * @author lirz
 * 
 * 2016-6-7 下午2:02:16
 */
public class FAfterwardsCheckReportProjectOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1270615499815478304L;
	
	private long id;
	private long formId;
	private String projectName;
	private String projectType;
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
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectType() {
		return this.projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public Date getOpeningDate() {
		return this.openingDate;
	}
	
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}
	
	public Date getClosingDate() {
		return this.closingDate;
	}
	
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
	
	public Money getOpeningArea() {
		return this.openingArea;
	}
	
	public void setOpeningArea(Money openingArea) {
		this.openingArea = openingArea;
	}
	
	public Money getReceivedAmount() {
		return this.receivedAmount;
	}
	
	public void setReceivedAmount(Money receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	
	public Money getReceivingAmount() {
		return this.receivingAmount;
	}
	
	public void setReceivingAmount(Money receivingAmount) {
		this.receivingAmount = receivingAmount;
	}
	
	public Money getSalingArea() {
		return this.salingArea;
	}
	
	public void setSalingArea(Money salingArea) {
		this.salingArea = salingArea;
	}
	
	public Money getSalingAmount() {
		return this.salingAmount;
	}
	
	public void setSalingAmount(Money salingAmount) {
		this.salingAmount = salingAmount;
	}
	
	public String getProjectPlan() {
		return this.projectPlan;
	}
	
	public void setProjectPlan(String projectPlan) {
		this.projectPlan = projectPlan;
	}
	
	public Money getPaidLandAmount() {
		return this.paidLandAmount;
	}
	
	public void setPaidLandAmount(Money paidLandAmount) {
		this.paidLandAmount = paidLandAmount;
	}
	
	public Money getPaidProjectAmount() {
		return this.paidProjectAmount;
	}
	
	public void setPaidProjectAmount(Money paidProjectAmount) {
		this.paidProjectAmount = paidProjectAmount;
	}
	
	public Money getPlanArea() {
		return this.planArea;
	}
	
	public void setPlanArea(Money planArea) {
		this.planArea = planArea;
	}
	
	public Money getPlanProjectAmount() {
		return this.planProjectAmount;
	}
	
	public void setPlanProjectAmount(Money planProjectAmount) {
		this.planProjectAmount = planProjectAmount;
	}
	
	public Money getPlanInvestAmount() {
		return this.planInvestAmount;
	}
	
	public void setPlanInvestAmount(Money planInvestAmount) {
		this.planInvestAmount = planInvestAmount;
	}
	
	public String getProjectDesc() {
		return this.projectDesc;
	}
	
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	
	public String getDelAble() {
		return this.delAble;
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
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
