/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 尽调
 *
 * @author lirz
 * 
 * 2016-10-18 上午10:21:38
 * 
 */
public class FInvestigationAddOrder extends ProjectFormOrderBase{

	private static final long serialVersionUID = 7220493073345430249L;

	private long investigateId;

	private long newFormId;

	private String review;

	private String councilBack;

	private String busiType;

	private String busiTypeName;

	private Money amount = new Money(0, 0);

	private String declares;

	private String investigatePlace;

	private Date investigateDate;

	private String investigatePersion;

	private String investigatePersionId;

	private String receptionPersion;

	private String receptionDuty;

	private String councilType;

	private long councilApplyId;

	private String councilStatus;

	public long getInvestigateId() {
		return investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
	}

	public long getNewFormId() {
		return newFormId;
	}
	
	public void setNewFormId(long newFormId) {
		this.newFormId = newFormId;
	}

	public String getReview() {
		return review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}

	public String getCouncilBack() {
		return councilBack;
	}
	
	public void setCouncilBack(String councilBack) {
		this.councilBack = councilBack;
	}

	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
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

	public String getDeclares() {
		return declares;
	}
	
	public void setDeclares(String declares) {
		this.declares = declares;
	}

	public String getInvestigatePlace() {
		return investigatePlace;
	}
	
	public void setInvestigatePlace(String investigatePlace) {
		this.investigatePlace = investigatePlace;
	}

	public Date getInvestigateDate() {
		return investigateDate;
	}
	
	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}

	public String getInvestigatePersion() {
		return investigatePersion;
	}
	
	public void setInvestigatePersion(String investigatePersion) {
		this.investigatePersion = investigatePersion;
	}

	public String getInvestigatePersionId() {
		return investigatePersionId;
	}
	
	public void setInvestigatePersionId(String investigatePersionId) {
		this.investigatePersionId = investigatePersionId;
	}

	public String getReceptionPersion() {
		return receptionPersion;
	}
	
	public void setReceptionPersion(String receptionPersion) {
		this.receptionPersion = receptionPersion;
	}

	public String getReceptionDuty() {
		return receptionDuty;
	}
	
	public void setReceptionDuty(String receptionDuty) {
		this.receptionDuty = receptionDuty;
	}

	public String getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}

	public long getCouncilApplyId() {
		return councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}

	public String getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
		this.councilStatus = councilStatus;
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
