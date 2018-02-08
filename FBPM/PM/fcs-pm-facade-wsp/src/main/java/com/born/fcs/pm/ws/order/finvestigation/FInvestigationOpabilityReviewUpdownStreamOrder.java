/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:55:45
 */
public class FInvestigationOpabilityReviewUpdownStreamOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 5807907802024115678L;
	
	private long id;
	private long opReviewId;
	private String upOrDown; //上游或下游
	private String name; //对方名称
	private String clearingForm; //结算方式
	private String paymentTerms; //帐期
	private String endingBalanceStr; //期末余额
	private String remark; //备注（产品种类、合作年限等）
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(name)
				&& isNull(clearingForm)
				&& isNull(paymentTerms)
				&& isNull(getEndingBalance())
				&& isNull(remark);
	}
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getOpReviewId() {
		return opReviewId;
	}
	
	public void setOpReviewId(long opReviewId) {
		this.opReviewId = opReviewId;
	}
	
	public String getUpOrDown() {
		return this.upOrDown;
	}
	
	public void setUpOrDown(String upOrDown) {
		this.upOrDown = upOrDown;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClearingForm() {
		return clearingForm;
	}
	
	public void setClearingForm(String clearingForm) {
		this.clearingForm = clearingForm;
	}
	
	public String getPaymentTerms() {
		return paymentTerms;
	}
	
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	public Money getEndingBalance() {
		if (isNull(this.endingBalanceStr)) {
			return new Money(0L);
		}
		return Money.amout(this.endingBalanceStr);
	}
	
	public String getEndingBalanceStr() {
		return endingBalanceStr;
	}
	
	public void setEndingBalanceStr(String endingBalanceStr) {
		this.endingBalanceStr = endingBalanceStr;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getSortOrder() {
		return sortOrder;
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
